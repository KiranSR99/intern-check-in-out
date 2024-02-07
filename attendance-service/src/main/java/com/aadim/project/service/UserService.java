package com.aadim.project.service;

import com.aadim.project.dto.request.ForgotPasswordRequest;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.SupervisorInfoResponse;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.Role;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {


    UserResponse saveUser(UserRequest request) throws MessagingException;


    Page<UserResponse> getAllUser(Pageable pageable);

    UserResponse getUserById(Integer id);

    Page<UserResponse> getAllUsersByRole(Role role, Pageable pageable);

    UserResponse updateUser (UserUpdateRequest request);

    String deleteUser(Integer id);

    String changePassword(PasswordRequest request);

    String changePasswordByEmail (ForgotPasswordRequest request);


    Page<SupervisorInfoResponse> getAllInternsOfSupervisor(Pageable pageable);
}
