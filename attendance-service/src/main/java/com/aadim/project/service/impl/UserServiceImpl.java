package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.InternInfoResponse;
import com.aadim.project.dto.response.SupervisorInfoResponse;
import com.aadim.project.dto.response.SupervisorResponse;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.*;
import com.aadim.project.repository.*;
import com.aadim.project.service.MailService;
import com.aadim.project.service.UserService;
import com.aadim.project.validator.EmailValidator;
import com.aadim.project.validator.PasswordValidator;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final InternRepository internRepository;
    private final SupervisorRepository supervisorRepository;
    private final UserRepository userRepository;

    @Autowired
    private MailService mailService;

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String SUPERVISOR_ROLE = "SUPERVISOR";
    private static final String INTERN_ROLE = "INTERN";

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest request) throws MessagingException {
        if (!EmailValidator.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
//        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhone())) {
//            throw  new IllegalArgumentException("Invalid phone number");
//        }
        if (!PasswordValidator.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }
        log.info("Saving user");
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);

        if (request.getRole().equals(Role.ADMIN)) {
            log.info("Role is Admin");
            Admin admin = new Admin();
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            admin.setUser(user);
            Admin admin1 = adminRepository.save(admin);

            mailService.userCreationMail(request.getEmail(), "Your Admin Account has been Created!", request);

            log.info("Admin Saved");
            return new UserResponse(admin1, user);
        } else if (request.getRole().equals(Role.SUPERVISOR)) {
            log.info("Role is Supervisor");
            Supervisor supervisor = new Supervisor();
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
            supervisor.setUser(user);
            Supervisor supervisor1 = supervisorRepository.save(supervisor);

            mailService.userCreationMail(request.getEmail(), "Your Supervisor Account has been Created!", request);

            log.info("Supervisor Saved");
            return new UserResponse(supervisor1, user);
        } else if (request.getRole().equals(Role.INTERN)) {
            log.info("Role is Intern");
            Intern intern = new Intern();
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
            intern.setFieldType(request.getFieldType());
            intern.setUser(user);
            Supervisor primarySupervisor = supervisorRepository.findSupervisorById(request.getPrimarySupervisor());
            intern.setPrimarySupervisor(primarySupervisor);
            Supervisor secondarySupervisor = supervisorRepository.findSupervisorById(request.getSecondarySupervisor());
            intern.setSecondarySupervisor(secondarySupervisor);
            Intern intern1 = internRepository.save(intern);

            mailService.userCreationMail(request.getEmail(), "Your Intern Account has been Created!", request);

            log.info("Intern Saved");
            return new UserResponse(intern1, user);
        } else {
            throw new IllegalArgumentException("Role not found");
        }
    }

    @Override
    public List<UserResponse> getAllUser() {
        log.info("Fetching All Users");
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findActiveUsers();

        for (User user : users) {
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
                    userResponse.setPrimarySupervisor((mapSupervisorInfo(intern.getPrimarySupervisor())));
                    userResponse.setSecondarySupervisor((mapSupervisorInfo(intern.getPrimarySupervisor())));
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
        return userResponses;
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
        User user = userRepository.getReferenceById(id);
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User not available");
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setUserId(user.getId());
        if (user.getRole().equals(Role.ADMIN)) {
            Admin admin = adminRepository.findAdminByUserId(id);
            userResponse.setPhone(admin.getPhone());
            userResponse.setFullName(admin.getFullName());
        } else if (user.getRole().equals(Role.SUPERVISOR)) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(id);
            userResponse.setFullName(supervisor.getFullName());
            userResponse.setPhone(supervisor.getPhone());
        } else if (user.getRole().equals(Role.INTERN)) {
            Intern intern = internRepository.findInternByUserId(id);
            userResponse.setInternId(intern.getId());
            userResponse.setPrimarySupervisor((mapSupervisorInfo(intern.getPrimarySupervisor())));
            userResponse.setSecondarySupervisor((mapSupervisorInfo(intern.getSecondarySupervisor())));
            userResponse.setPhone(intern.getPhone());
            userResponse.setFullName(intern.getFullName());
            userResponse.setFieldType(intern.getFieldType());
        } else {
            log.warn("Role not found");
            throw new RuntimeException("Role Not found");
        }
        log.info("User with id : " + id + "fetched successfully.");
        return userResponse;
    }


    @Override
    public List<UserResponse> getAllUsersByRole(Role role) {
        log.info("Fetching all users of " + role);
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findActiveUsersByRole(role);
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            if (role.equals(Role.INTERN)) {
                Intern intern = internRepository.findInternByUserId(user.getId());
                if (intern != null) {
                    userResponse.setInternId(intern.getId());
                    userResponse.setPrimarySupervisor((mapSupervisorInfo(intern.getPrimarySupervisor())));
                    userResponse.setSecondarySupervisor((mapSupervisorInfo(intern.getSecondarySupervisor())));
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
        log.info("Fetched all users of ");
        return userResponses;
    }


    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository.getReferenceById(request.getId());
        if (!user.isActive()) {
            throw new RuntimeException("User not available");
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
            log.warn("User with id " + id + "doesnt exist.");
            return "User doesnt exist.";
        }
        User user = userRepository.getReferenceById(id);
        if (!user.isActive()) {
            log.warn("User with id " + id + "isn't active.");
            return "User is not active.";
        }
        user.setActive(false);
        Role role = user.getRole();
        if (role.toString().equals(ADMIN_ROLE)) {
            Admin admin = adminRepository.findAdminByUserId(user.getId());
            admin.setIsActive(false);
            adminRepository.save(admin);
        } else if (role.toString().equals(SUPERVISOR_ROLE)) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
            supervisor.setIsActive(false);
            supervisorRepository.save(supervisor);
        } else if (Objects.equals(role.toString(), INTERN_ROLE)) {
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
        User user = userRepository.getReferenceById(request.getUserId());
        if (new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        } else {
            log.warn("Old password didnt match");
            throw new RuntimeException("Old password doesn't match.");
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


//    @Override
//    public List<SupervisorResponse> getAllInternsBySupervisor() {
//        log.info("Fetching All Interns by Supervisor");
//        List<SupervisorResponse> supervisorResponses = new ArrayList<>();
//        List<Supervisor> supervisorList = supervisorRepository.findActiveSupervisors();
//
//        for (Supervisor supervisor : supervisorList) {
//            SupervisorResponse supervisorResponse = new SupervisorResponse();
//            supervisorResponse.setSupervisorInfoResponse(mapSupervisorInfo(supervisor));
//
//            List<Intern> internList = internRepository.findActiveInternsBySupervisor(supervisor);
//            List<InternInfoResponse> internInfoResponses = new ArrayList<>();
//
//            for (Intern intern : internList) {
//                InternInfoResponse internInfoResponse = new InternInfoResponse();
//                internInfoResponse.setUserId(intern.getUser().getId());
//                internInfoResponse.setInternId(intern.getId());
//                internInfoResponse.setEmail(intern.getUser().getEmail());
//                internInfoResponse.setPhone(intern.getPhone());
//                internInfoResponse.setRole(intern.getUser().getRole());
//                internInfoResponse.setFieldType(intern.getFieldType());
//                internInfoResponses.add(internInfoResponse);
//            }
//
//            supervisorResponse.setInternInfoResponses(internInfoResponses);
//            supervisorResponses.add(supervisorResponse);
//        }
//
//        log.info("Fetched All Interns by Supervisor");
//        return supervisorResponses;
//    }


    @Override
    public List<SupervisorInfoResponse> getAllInternsOfSupervisor() {
        log.info("Fetching All Interns by Supervisor");
        List<SupervisorInfoResponse> supervisorResponses = new ArrayList<>();
        List<Supervisor> supervisorList = supervisorRepository.findActiveSupervisors();

        for (Supervisor supervisor : supervisorList) {
            SupervisorInfoResponse supervisorResponse = mapSupervisorInfo(supervisor); // Corrected line

            List<Intern> internList = internRepository.findActiveInternsBySupervisor(supervisor);
            List<InternInfoResponse> internInfoResponses = new ArrayList<>();

            for (Intern intern : internList) {
                InternInfoResponse internInfoResponse = new InternInfoResponse();
                internInfoResponse.setUserId(intern.getUser().getId());
                internInfoResponse.setFullName(intern.getFullName());
                internInfoResponse.setInternId(intern.getId());
                internInfoResponse.setEmail(intern.getUser().getEmail());
                internInfoResponse.setPhone(intern.getPhone());
                internInfoResponse.setRole(intern.getUser().getRole());
                internInfoResponse.setFieldType(intern.getFieldType());
                internInfoResponses.add(internInfoResponse);
            }

            supervisorResponse.setInternInfoResponses(internInfoResponses);
            supervisorResponses.add(supervisorResponse);
        }

        log.info("Fetched All Interns by Supervisor");
        return supervisorResponses;
    }


}
