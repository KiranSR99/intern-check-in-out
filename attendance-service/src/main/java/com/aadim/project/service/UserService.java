package com.aadim.project.service;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.dto.response.UserResponse;
import com.aadim.project.entity.Role;

import java.util.List;

public interface UserService {


    UserResponse saveUser(UserRequest request);

    List<UserResponse> getAllUser();


    UserResponse getUserById(Integer id);

    List<UserResponse> getAllUsersByRole(Role role);

    UserResponse updateUser (UserUpdateRequest request);

    String deleteUser(Integer id);
}
