package com.aadim.project.service.impl;

import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.*;
import com.aadim.project.repository.*;
import com.aadim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);

        if(request.getRole().toString().equals("ADMIN") ) {
            Admin admin = new Admin();
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            admin.setUser(user);
            Admin admin1 = adminRepository.save(admin);
            return new UserResponse(admin1, user);
        } else if (Objects.equals(request.getRole().toString(), "SUPERVISOR")) {
            Supervisor supervisor = new Supervisor();
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
            supervisor.setUser(user);
            Supervisor supervisor1 = supervisorRepository.save(supervisor);
            return new UserResponse(supervisor1, user);
        } else if (Objects.equals(request.getRole().toString(), "INTERN")) {
            Intern intern = new Intern();
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
            intern.setFieldType(request.getFieldType());
            intern.setUser(user);
            Supervisor primarySupervisor = supervisorRepository.findSupervisorById(request.getPrimarySupervisor());
            intern.setPrimarySupervisor(primarySupervisor);
            Supervisor secondarySupervisor = supervisorRepository.findSupervisorById(request.getSecondarySupervisor());
            intern.setSecondarySupervisor(secondarySupervisor);
            Intern intern1= internRepository.save(intern);
            return new UserResponse(intern1, user);
        }
        return new UserResponse();
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findActiveUsers();

        for( User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            userResponse.setUserId(user.getId());
            Intern intern = internRepository.findInternByUserId(user.getId());
            if(intern != null) {
                userResponse.setFullName(intern.getFullName());
                userResponse.setPhone(intern.getPhone());
                userResponse.setFieldType(intern.getFieldType());
            }
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
            if(supervisor != null) {
                userResponse.setFullName(supervisor.getFullName());
                userResponse.setPhone(supervisor.getPhone());
            }
            Admin admin = adminRepository.findAdminByUserId(user.getId());
            if(admin != null) {
                userResponse.setFullName(admin.getFullName());
                userResponse.setPhone(admin.getPhone());
            }
            userResponses.add(userResponse);
        }

        return userResponses;
    }

    @Override
    public UserResponse getUserById(Integer id) {
        User user = userRepository.getReferenceById(id);
        if(!user.isActive()) {
            throw new RuntimeException("User not available");
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setUserId(user.getId());
        if(user.getRole().toString().equals("ADMIN")) {
            Admin admin = adminRepository.findAdminByUserId(id);
            userResponse.setPhone(admin.getPhone());
            userResponse.setFullName(admin.getFullName());
        } else if (user.getRole().toString().equals("SUPERVISOR")) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(id);
            userResponse.setFullName(supervisor.getFullName());
            userResponse.setPhone(supervisor.getPhone());
        } else if (user.getRole().toString().equals("INTERN")) {
            Intern intern = internRepository.findInternByUserId(id);
            userResponse.setPhone(intern.getPhone());
            userResponse.setFullName(intern.getFullName());
            userResponse.setFieldType(intern.getFieldType());
        }

        return userResponse;
    }


//    To-Do  next
    @Override
    public List<UserResponse> getAllUsersByRole(Role role) {
        log.warn(String.valueOf(role));
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findActiveUsersByRole(role);
        for (User user: users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setRole(user.getRole());
            if (role.toString().equals("INTERN")) {
                Intern intern = internRepository.findInternByUserId(user.getId());
                if (intern != null) {
                    userResponse.setFullName(intern.getFullName());
                    userResponse.setPhone(intern.getPhone());
                    userResponse.setFieldType(intern.getFieldType());
                }
            } else if (role.toString().equals("SUPERVISOR")) {
                Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
                if (supervisor != null) {
                    userResponse.setFullName(supervisor.getFullName());
                    userResponse.setPhone(supervisor.getPhone());
                }
            } else if (role.toString().equals("ADMIN")) {
                Admin admin = adminRepository.findAdminByUserId(user.getId());
                if (admin != null) {
                    userResponse.setFullName(admin.getFullName());
                    userResponse.setPhone(admin.getPhone());
                }
//            else {
//                throw new RuntimeException("Role " + role + "not found.");
//                }
            }
            userResponses.add(userResponse);
        }
        return userResponses;
    }




    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository.getReferenceById(request.getId());
        if(!user.isActive()) {
            throw new RuntimeException("User not available");
        }
        Role role = user.getRole();
        if(role.toString().equals("ADMIN") ) {
            Admin admin = adminRepository.findAdminByUserId(request.getId());
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            adminRepository.save(admin);
            return new UserResponse(admin, user);
        } else if (role.toString().equals("SUPERVISOR")) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(request.getId());
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
            supervisorRepository.save(supervisor);
            return new UserResponse(supervisor, user);
        } else if(role.toString().equals("INTERN")) {
            Intern intern = internRepository.findInternByUserId(request.getId());
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
            intern.setFieldType(request.getFieldType());
            internRepository.save(intern);
            return new UserResponse(intern, user);
        }
        return null;
    }


    @Override
    public String deleteUser(Integer id) {
        if(!userRepository.existsById(id)) {
            return "User doesnt exist.";
        }
        User user = userRepository.getReferenceById(id);
        if(!user.isActive()) {
            return "User is not active.";
        }
        user.setActive(false);
        Role role = user.getRole();
        if(role.toString().equals("ADMIN")) {
            Admin admin = adminRepository.findAdminByUserId(user.getId());
            admin.setIsActive(false);
            adminRepository.save(admin);
        } else if (role.toString().equals("SUPERVISOR")) {
            Supervisor supervisor = supervisorRepository.findSupervisorByUserId(user.getId());
            supervisor.setIsActive(false);
            supervisorRepository.save(supervisor);
        } else if (role.toString().equals("INTERN")) {
            Intern intern = internRepository.findInternByUserId(user.getId());
            intern.setIsActive(false);
            internRepository.save(intern);
        }
        return "User with id " + id + " deleted successfully.";
    }


    @Transactional
    public String changePassword(PasswordRequest request)
    {

        User user = userRepository.getReferenceById(request.getUserId());
        if(new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword()) ) {
            user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        } else {
            throw new RuntimeException("Old password doesn't match.");
        }
        return "success";
    }

    @Transactional
    public String changePasswordByEmail (ForgotPasswordRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            User user = userRepository.getUserByEmail(request.getEmail());
            if (user.isActive()) {
                user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
                return "Password updated successfully";
            }
            else return "User is inActive. Couldn't change password.";
        }
        return "User with email " + request.getEmail() + " doesn't exist.";
    }
}
