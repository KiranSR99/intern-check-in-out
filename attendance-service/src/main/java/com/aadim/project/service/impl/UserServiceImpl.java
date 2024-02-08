package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.InternInfoResponse;
import com.aadim.project.dto.response.SupervisorInfoResponse;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.*;
import com.aadim.project.repository.*;
import com.aadim.project.service.MailService;
import com.aadim.project.service.UserService;
import com.aadim.project.validator.EmailValidator;
import com.aadim.project.validator.PasswordValidator;
import com.aadim.project.validator.PhoneNumberValidator;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final InternRepository internRepository;
    private final SupervisorRepository supervisorRepository;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest request) throws MessagingException {
        validateUserRequest(request);
        log.info("Saving user");
        User user = createUser(request);
        return switch (request.getRole()) {
            case ADMIN -> saveAdmin(request, user);
            case SUPERVISOR -> saveSupervisor(request, user);
            case INTERN -> saveIntern(request, user);
        };
    }

    private void validateUserRequest(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityViolationException("Email already taken");
        }
        if (!EmailValidator.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!PasswordValidator.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Password strength not matched. Required: at least 1 Uppercase, 1" +
                    " lowercase, 1 special character and 1 number. Total length: 8 required. ");
        }
        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhone())) {
            throw new IllegalArgumentException("Invalid Phone number.");
        }
    }

    private User createUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    private UserResponse saveAdmin(UserRequest request, User user) throws MessagingException {
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setPhone(request.getPhone());
        admin.setUser(user);
        Admin savedAdmin = adminRepository.save(admin);
        mailService.userCreationMail(request.getEmail(), "Your Admin Account has been Created!", request);
        return new UserResponse(savedAdmin, user);
    }

    private UserResponse saveSupervisor(UserRequest request, User user) throws MessagingException {
        Supervisor supervisor = new Supervisor();
        supervisor.setFullName(request.getFullName());
        supervisor.setPhone(request.getPhone());
        supervisor.setUser(user);
        Supervisor savedSupervisor = supervisorRepository.save(supervisor);
        mailService.userCreationMail(request.getEmail(), "Your Supervisor Account has been Created!", request);
        return new UserResponse(savedSupervisor, user);
    }

    private UserResponse saveIntern(UserRequest request, User user) throws MessagingException {
        Intern intern = new Intern();
        intern.setFullName(request.getFullName());
        intern.setPhone(request.getPhone());
        intern.setFieldType(request.getFieldType());
        intern.setUser(user);
        intern.setPrimarySupervisor(supervisorRepository.findSupervisorById(request.getPrimarySupervisor()));
        intern.setSecondarySupervisor(supervisorRepository.findSupervisorById(request.getSecondarySupervisor()));
        Intern savedIntern = internRepository.save(intern);
        mailService.userCreationMail(request.getEmail(), "Your Intern Account has been Created!", request);
        return new UserResponse(savedIntern, user);
    }


    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {
        log.info("Fetching All Users");

        // Fetching users with pagination
        Page<User> usersPage = userRepository.findActiveUsers(pageable);

        // Mapping User entities to UserResponse DTOs
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : usersPage.getContent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            userResponse.setUserId(user.getId());

            if (user.getRole().equals(Role.INTERN)) {
                Intern intern = internRepository.findInternByUserId(user.getId());
                if (intern != null) {
                    userResponse.setInternId(intern.getId());
                    userResponse.setFullName(intern.getFullName());
                    userResponse.setPhone(intern.getPhone());
                    userResponse.setPrimarySupervisor(mapSupervisorInfo(intern.getPrimarySupervisor()));
                    userResponse.setSecondarySupervisor(mapSupervisorInfo(intern.getPrimarySupervisor()));
                    userResponse.setFieldType(intern.getFieldType());
                }
            } else if (user.getRole().equals(Role.SUPERVISOR)) {
                Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
                if (supervisor != null) {
                    userResponse.setFullName(supervisor.getFullName());
                    userResponse.setPhone(supervisor.getPhone());
                }
            } else if (user.getRole().equals(Role.ADMIN)) {
                Admin admin = adminRepository.findAdminByUserId(user.getId());
                if (admin != null) {
                    userResponse.setFullName(admin.getFullName());
                    userResponse.setPhone(admin.getPhone());
                }
            }

            userResponses.add(userResponse);
        }

        log.info("All users fetched successfully");
        return new PageImpl<>(userResponses, pageable, usersPage.getTotalElements());
    }



    private SupervisorInfoResponse mapSupervisorInfo(Supervisor supervisor) {
        if (supervisor != null) {
            SupervisorInfoResponse supervisorInfo = new SupervisorInfoResponse();
            supervisorInfo.setUserId(supervisor.getUser().getId());
            supervisorInfo.setSupervisorId(supervisor.getId());
            supervisorInfo.setFullName(supervisor.getFullName());
            supervisorInfo.setEmail(supervisor.getUser().getEmail());
            supervisorInfo.setPhone(supervisor.getPhone());
            supervisorInfo.setRole(supervisor.getUser().getRole());
            return supervisorInfo;
        }
        return null;
    }

    private InternInfoResponse mapInternInfo(Intern intern) {
        if (intern != null) {
            InternInfoResponse internInfo = new InternInfoResponse();
            internInfo.setUserId(intern.getUser().getId());
            internInfo.setInternId(intern.getId());
            internInfo.setFullName(intern.getFullName());
            internInfo.setEmail(intern.getUser().getEmail());
            internInfo.setPhone(intern.getPhone());
            internInfo.setRole(intern.getUser().getRole());
            return internInfo;
        }
        return null;
    }


    @Override
    public UserResponse getUserById(Integer id) {
        log.info("Fetching user with id: " + id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("User is not active");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setUserId(user.getId());

        switch (user.getRole()) {
            case ADMIN:
                Admin admin = adminRepository.findAdminByUserId(id);
                if (admin == null) {
                    throw new EntityNotFoundException("Admin details not found");
                }
                userResponse.setFullName(admin.getFullName());
                userResponse.setPhone(admin.getPhone());
                break;
            case SUPERVISOR:
                Supervisor supervisor = supervisorRepository.findSupervisorByUserId(id);
                if (supervisor == null) {
                    throw new EntityNotFoundException("Supervisor details not found");
                }
                userResponse.setFullName(supervisor.getFullName());
                userResponse.setPhone(supervisor.getPhone());
                break;
            case INTERN:
                Intern intern = internRepository.findInternByUserId(id);
                if (intern == null) {
                    throw new EntityNotFoundException("Intern details not found");
                }
                userResponse.setInternId(intern.getId());
                userResponse.setPrimarySupervisor(mapSupervisorInfo(intern.getPrimarySupervisor()));
                userResponse.setSecondarySupervisor(mapSupervisorInfo(intern.getSecondarySupervisor()));
                userResponse.setPhone(intern.getPhone());
                userResponse.setFullName(intern.getFullName());
                userResponse.setFieldType(intern.getFieldType());
                break;
            default:
                throw new IllegalArgumentException("Invalid user role");
        }

        return userResponse;
    }


    @Override
    public Page<UserResponse> getAllUsersByRole(Role role, Pageable pageable) {
        log.info("Fetching all users of " + role);

        Page<User> userPage = userRepository.findActiveUsersByRole(role, pageable);
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : userPage.getContent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());

            if (role.equals(Role.INTERN)) {
                Intern intern = internRepository.findInternByUserId(user.getId());
                if (intern != null) {
                    userResponse.setInternId(intern.getId());
                    userResponse.setPrimarySupervisor(mapSupervisorInfo(intern.getPrimarySupervisor()));
                    userResponse.setSecondarySupervisor(mapSupervisorInfo(intern.getSecondarySupervisor()));
                    userResponse.setFullName(intern.getFullName());
                    userResponse.setPhone(intern.getPhone());
                    userResponse.setFieldType(intern.getFieldType());
                }
            } else if (role.equals(Role.SUPERVISOR)) {
                Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
                if (supervisor != null) {
                    userResponse.setUserId(supervisor.getId());
                    userResponse.setFullName(supervisor.getFullName());
                    userResponse.setPhone(supervisor.getPhone());
                }
            } else if (role.equals(Role.ADMIN)) {
                Admin admin = adminRepository.findAdminByUserId(user.getId());
                if (admin != null) {
                    userResponse.setFullName(admin.getFullName());
                    userResponse.setPhone(admin.getPhone());
                }
            }

            userResponses.add(userResponse);
        }

        log.info("Fetched all users of " + role);
        return new PageImpl<>(userResponses, pageable, userPage.getTotalElements());
    }



    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository.getReferenceById(request.getId());
        if (!user.isActive()) {
            throw new EntityNotFoundException("User not available");
        }
        Role role = user.getRole();
        if (role.equals(Role.ADMIN)) {
            Admin admin = adminRepository.findAdminByUserId(request.getId());
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            adminRepository.save(admin);
            return new UserResponse(admin, user);
        } else if (role.equals(Role.SUPERVISOR)) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(request.getId());
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
            supervisorRepository.save(supervisor);
            return new UserResponse(supervisor, user);
        } else if (role.equals(Role.INTERN)) {
            Intern intern = internRepository.findInternByUserId(request.getId());
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
            intern.setFieldType(request.getFieldType());
            Supervisor primarySupervisor = supervisorRepository.findSupervisorByUserId(request.getPrimarySupervisor());
            Supervisor secondarySupervisor = supervisorRepository.findSupervisorByUserId(request.getPrimarySupervisor());
            if (primarySupervisor == null) {
                throw new EntityNotFoundException("Primary Supervisor doesn't exist.");
            }
            if (secondarySupervisor == null) {
                throw new EntityNotFoundException("Secondary Supervisor doesn't exist.");
            }
            intern.setPrimarySupervisor(primarySupervisor);
            intern.setPrimarySupervisor(secondarySupervisor);
            internRepository.save(intern);
            return new UserResponse(intern, user);
        }
        log.warn("Cannot update user with id : " + request.getId());
        return null;
    }


    @Override
    public String deleteUser(Integer id) {
        log.info("Deleting user with id :" + id);
        if (!userRepository.existsById(id)) {
            log.warn("User with id {} doesn't exist.", id);
            return "User doesnt exist.";
        }
        User user = userRepository.getReferenceById(id);
        if (!user.isActive()) {
            log.warn("User with id {} isn't active.", id);
            return "User is not active.";
        }
        user.setActive(false);
        Role role = user.getRole();
        if (role.equals(Role.ADMIN)) {
            Admin admin = adminRepository.findAdminByUserId(user.getId());
            admin.setIsActive(false);
            adminRepository.save(admin);
        } else if (role.equals(Role.SUPERVISOR)) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
            supervisor.setIsActive(false);
            supervisorRepository.save(supervisor);
        } else if (role.equals(Role.INTERN)) {
            Intern intern = internRepository.findInternByUserId(user.getId());
            intern.setIsActive(false);
            internRepository.save(intern);
        }
        log.info("User with id " + id + " deleted successfully.");
        return "User with id " + id + " deleted successfully.";
    }


    @Transactional
    public String changePassword(PasswordRequest request) {
        log.info("Changing Password");
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        } else {
            log.warn("Old password didnt match");
            throw new IllegalArgumentException("Old password doesn't match.");
        }
        log.info("Password changed.");
        return "success";
    }

    @Transactional
    public String changePasswordByEmail(ForgotPasswordRequest request) {
        log.info("Changing Password by Email.");
        if (userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.getUserByEmail(request.getEmail());
            if (user.isActive()) {
                user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
                log.info("Password updated successfully");
                return "Password updated successfully";
            } else return "User is inActive. Couldn't change password.";
        }
        log.warn("User with email " + request.getEmail() + " doesn't exist.");
        return "User with email " + request.getEmail() + " doesn't exist.";
    }




    @Override
    public Page<SupervisorInfoResponse> getAllInternsOfSupervisor(Pageable pageable) {
        log.info("Fetching All Interns by Supervisor");

        Page<Supervisor> supervisorPage = supervisorRepository.findActiveSupervisors(pageable);
        List<SupervisorInfoResponse> supervisorResponses = new ArrayList<>();

        for (Supervisor supervisor : supervisorPage.getContent()) {
            SupervisorInfoResponse supervisorResponse = mapSupervisorInfo(supervisor);

            List<Intern> internList = internRepository.findActiveInternsBySupervisor(supervisor);
            List<InternInfoResponse> internInfoResponses = new ArrayList<>();

            for (Intern intern : internList) {
                InternInfoResponse internInfoResponse = mapInternInfo(intern);
                internInfoResponses.add(internInfoResponse);
            }

            supervisorResponse.setInternInfoResponses(internInfoResponses);
            supervisorResponses.add(supervisorResponse);
        }

        log.info("Fetched All Interns by Supervisor");
        return new PageImpl<>(supervisorResponses, pageable, supervisorPage.getTotalElements()) ;
    }


}
