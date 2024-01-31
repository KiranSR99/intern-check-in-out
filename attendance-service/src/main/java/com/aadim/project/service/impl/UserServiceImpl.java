package com.aadim.project.service.impl;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.*;
import com.aadim.project.repository.*;
import com.aadim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
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
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        userRepository.save(user);

        if(request.getRole().toString().equals("ADMIN") ) {
            Admin admin = new Admin();
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            admin.setUser(user);
            Admin admin1 = adminRepository.save(admin);
            return new UserResponse(admin1, user);
        } else if (Objects.equals(request.getRole().toString(), "SUPERVISIOR")) {
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
            Intern intern1= internRepository.save(intern);
            return new UserResponse(intern1, user);
        }
        return new UserResponse();
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll();


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


    public UserResponse updateUser (UserUpdateRequest request) {
        User user = userRepository.getReferenceById(request.getId());
        return null;
    }

}
