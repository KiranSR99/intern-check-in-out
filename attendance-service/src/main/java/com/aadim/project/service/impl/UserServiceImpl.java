package com.aadim.project.service.impl;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.Admin;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Login;
import com.aadim.project.entity.Supervisor;
import com.aadim.project.repository.AdminRepository;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.SupervisorRepository;
import com.aadim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final InternRepository internRepository;
    private final SupervisorRepository supervisorRepository;
    @Override
    @Transactional
    public UserResponse saveUser(UserRequest request) {
        Login login = new Login();
        login.setEmail(request.getEmail());
        login.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        if(Objects.equals(request.getRole(), "ADMIN")) {
            Admin admin = new Admin();
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
        } else if (Objects.equals(request.getRole(), "SUPERVISIOR")) {
            Supervisor supervisor = new Supervisor();
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
        } else if (Objects.equals(request.getRole(), "INTERN")) {
            Intern intern = new Intern();
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
        }
        return new UserResponse();
    }

}
