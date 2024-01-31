package com.aadim.project.config;

import com.aadim.project.entity.Role;
import com.aadim.project.entity.User;
import com.aadim.project.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InitDBConfig {
    private final UserRepository userRepository;

    @PostConstruct
    public void insert(){
        if(userRepository.findAll().isEmpty()){
            log.info("Initial Admin creation request received");
            User dbUser = User.builder()
                    .email("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .role(Role.valueOf("ADMIN"))
                    .build();
            userRepository.save(dbUser);
            log.info("Super Admin created");
        }
        else log.info("Super Admin already exists");
    }
}
