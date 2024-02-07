package com.aadim.project.controller;

import com.aadim.project.controller.Base.BaseController;
import com.aadim.project.dto.GlobalApiResponse;
import com.aadim.project.dto.auth.LoginRequest;
import com.aadim.project.dto.auth.LoginResponse;
import com.aadim.project.entity.Token;
import com.aadim.project.entity.TokenType;
import com.aadim.project.entity.User;
import com.aadim.project.repository.TokenRepository;
import com.aadim.project.repository.UserRepository;
import com.aadim.project.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class AuthController extends BaseController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<GlobalApiResponse> authenticateAndGetToken(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        log.info("Trying to authenticate user");
        if (authentication.isAuthenticated()) {
            log.info("User authenticated(User is a valid).");
            LoginResponse response = new LoginResponse();
            String token = jwtService.generateToken(request.getEmail());
            response.setToken(token);
            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();
                Integer userId = userRepository.findUserIdByEmail(user.getUsername());
                response.setUserId(userId);
                response.setRole(user.getRole().toString());
                revokeAllUserToken(user);
                saveUserToken(user, token);
            }
            return successResponse(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    private void revokeAllUserToken(User user){
        log.info("Expiring and revoking old tokens.");
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
        log.info("Old token expired and revoked.");
    }

    private void saveUserToken(User user, String token) {
        log.info("Saving the user token in Token table.");
        var tkn = Token.builder()
                .user(user)
                .token(token)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(tkn);
        log.info("Token saved in Token table.");
    }


}
