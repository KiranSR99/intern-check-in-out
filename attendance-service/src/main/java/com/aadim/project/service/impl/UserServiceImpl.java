package com.aadim.project.service.impl;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.Admin;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Login;
import com.aadim.project.entity.Supervisor;
import com.aadim.project.entity.User;
import com.aadim.project.repository.AdminRepository;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.SupervisorRepository;
import com.aadim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Override
@Transactional
public UserResponse saveUser(UserRequest request) {
    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(request.getPassword());
    user.setRole(request.getRole());
    userRepository.save(user);

    if(Objects.equals(request.getRole(), "ADMIN")) {
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setPhone(request.getPhone());
        Admin admin1 = adminRepository.save(admin);
        return new UserResponse(admin1, user);
    } else if (Objects.equals(request.getRole(), "SUPERVISIOR")) {
        Supervisor supervisor = new Supervisor();
        supervisor.setFullName(request.getFullName());
        supervisor.setPhone(request.getPhone());
        Supervisor supervisor1 = supervisorRepository.save(supervisor);
        return new UserResponse(supervisor1, user);
    } else if (Objects.equals(request.getRole(), "INTERN")) {
        Intern intern = new Intern();
        intern.setFullName(request.getFullName());
        intern.setPhone(request.getPhone());
        Intern intern1= internRepository.save(intern);
        return new UserResponse(intern1, user);
    }
    return new UserResponse();
}

}
