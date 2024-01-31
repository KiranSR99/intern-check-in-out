package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<GlobalApiResponse<>> saveUser(@RequestBody UserRequest request){
        return successResponse(userService.saveUser(request), "User saved successfully");
    }

    @PostMapping("/saveIntern")
    public String saveIntern(@RequestBody String name){
        log.warn("name: {}", name + " " + "is saved");
        return "success";
    }
}
