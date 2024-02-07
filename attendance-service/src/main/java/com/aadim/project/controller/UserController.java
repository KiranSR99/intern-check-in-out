package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.request.PasswordRequest;
import com.aadim.project.dto.request.UserRequest;
import com.aadim.project.dto.request.UserUpdateRequest;
import com.aadim.project.entity.Role;
import com.aadim.project.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveUser")
    public ResponseEntity<GlobalApiResponse> saveUser(@RequestBody UserRequest request) throws MessagingException {
        return successResponse(userService.saveUser(request), "User saved successfully");
    }




    @GetMapping("/getAll")
    public ResponseEntity<GlobalApiResponse> getAllUser(@PageableDefault Pageable pageable){
        return successResponse(userService.getAllUser(pageable), "User fetched successfully");
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<GlobalApiResponse> getUserById (@PathVariable Integer id) {
        return successResponse(userService.getUserById(id), "User with id " + id + "fetched successfully." );
    }

    @GetMapping("/getAllInterns")
    public ResponseEntity<GlobalApiResponse> getAllInterns(@PageableDefault Pageable pageable) {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("INTERN"), pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllAdmins")
    public ResponseEntity<GlobalApiResponse> getAllAdmins(@PageableDefault Pageable pageable) {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("ADMIN"), pageable));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping("/getAllSupervisors")
    public ResponseEntity<GlobalApiResponse> getAllSupervisors(@PageableDefault Pageable pageable) {
        return successResponse(userService.getAllUsersByRole(Role.valueOf("SUPERVISOR"), pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<GlobalApiResponse> updateUser (@RequestBody UserUpdateRequest request) {
        return successResponse(userService.updateUser(request) , "User Updated Successfully.");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalApiResponse> deleteUser (@PathVariable Integer id) {
        return successResponse(userService.deleteUser(id));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<GlobalApiResponse> changePassword (@RequestBody PasswordRequest request) {
        return successResponse(userService.changePassword(request), "Password Reset Successful");
    }

    @GetMapping("/getInternsOfSupervisor")
    public ResponseEntity<GlobalApiResponse> getInternsOfSupervisor(@PageableDefault Pageable pageable) {
        return successResponse(userService.getAllInternsOfSupervisor(pageable));
    }
}
