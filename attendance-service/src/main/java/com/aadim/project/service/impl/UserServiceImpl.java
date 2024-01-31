package com.aadim.project.service.impl;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.Admin;
import com.aadim.project.entity.Intern;
import com.aadim.project.entity.Login;
import com.aadim.project.entity.Supervisor;
import com.aadim.project.repository.AdminRepository;
import com.aadim.project.repository.InternRepository;
import com.aadim.project.repository.LoginRepository;
import com.aadim.project.repository.SupervisorRepository;
import com.aadim.project.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final InternRepository internRepository;
    private final SupervisorRepository supervisorRepository;
    private final LoginRepository loginRepository;

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest request) {
        Login login = new Login();
        login.setEmail(request.getEmail());
        login.setPassword(request.getPassword());
        login.setRole(request.getRole());
        loginRepository.save(login);

        if(Objects.equals(request.getRole(), "ADMIN")) {
            Admin admin = new Admin();
            admin.setFullName(request.getFullName());
            admin.setPhone(request.getPhone());
            Admin admin1 = adminRepository.save(admin);
            return new UserResponse(admin1, login);
        } else if (Objects.equals(request.getRole(), "SUPERVISIOR")) {
            Supervisor supervisor = new Supervisor();
            supervisor.setFullName(request.getFullName());
            supervisor.setPhone(request.getPhone());
            Supervisor supervisor1 = supervisorRepository.save(supervisor);
            return new UserResponse(supervisor1, login);
        } else if (Objects.equals(request.getRole(), "INTERN")) {
            Intern intern = new Intern();
            intern.setFullName(request.getFullName());
            intern.setPhone(request.getPhone());
            Intern intern1= internRepository.save(intern);
            return new UserResponse(intern1, login);
        }
        return new UserResponse();
    }

}
