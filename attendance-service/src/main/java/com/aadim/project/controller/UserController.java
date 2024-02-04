package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.entity.Role;
import com.aadim.project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;

// to-read    mapStruct

    @PostMapping("/saveUser")
    public ResponseEntity<GlobalApiResponse> saveUser(@RequestBody UserRequest request){
        return successResponse(userService.saveUser(request), "User saved successfully");
    }



    @GetMapping("/getAll")
    public ResponseEntity<GlobalApiResponse> getAllUser(){
        return successResponse(userService.getAllUser(), "User fetched successfully");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<GlobalApiResponse> getUserById (@PathVariable Integer id) {
        return successResponse(userService.getUserById(id), "User with id " + id + "fetched successfully." );
    }

    @GetMapping("/getAllInterns")
    public ResponseEntity<GlobalApiResponse> getAllInterns() {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("INTERN")));
    }

    @GetMapping("/getAllAdmins")
    public ResponseEntity<GlobalApiResponse> getAllAdmins() {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("ADMIN")));
    }

    @GetMapping("/getAllSupervisors")
    public ResponseEntity<GlobalApiResponse> getAllSupervisors() {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("SUPERVISOR")));
    }

    @PutMapping("/update")
    public ResponseEntity<GlobalApiResponse> updateUser (@RequestBody UserUpdateRequest request) {
        return successResponse(userService.updateUser(request) , "User Updated Successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteUser (@PathVariable Integer id) {
        return successResponse(userService.deleteUser(id));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<GlobalApiResponse> changePassword (@RequestBody PasswordRequest request) {
        return successResponse(userService.changePassword(request), "Password Reset Successful");
    }
}
