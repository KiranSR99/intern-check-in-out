package com.aadim.project.service;

import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.response.UserResponse;

import java.util.List;

public interface UserService {


    UserResponse saveUser(UserRequest request);

    List<UserResponse> getAllUser();
}
