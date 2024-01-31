package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.auth.LoginRequest;
import com.aadim.project.dto.auth.LoginResponse;
import com.aadim.project.entity.User;
import com.aadim.project.repository.UserRepository;
import com.aadim.project.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<GlobalApiResponse> authenticateAndGetToken(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginResponse response = new LoginResponse();
            String token = jwtService.generateToken(request.getEmail());
            response.setToken(token);
            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();
                Integer userId = userRepository.findUserIdByEmail(user.getUsername());
                response.setUserId(userId);
                response.setRole(user.getRole().toString());
            }
            return successResponse(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

}
