package com.taxbuddy.taxbuddy_user_service.service;


import com.taxbuddy.taxbuddy_user_service.dto.AuthResponse;
import com.taxbuddy.taxbuddy_user_service.dto.LoginRequest;
import com.taxbuddy.taxbuddy_user_service.dto.RegisterRequest;
import com.taxbuddy.taxbuddy_user_service.entity.Role;
import com.taxbuddy.taxbuddy_user_service.entity.User;
import com.taxbuddy.taxbuddy_user_service.repository.UserRepository;
import com.taxbuddy.taxbuddy_user_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        log.info("Registering new user with email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            log.warn("Registration failed - email already exists: {}",
                    request.email());
            return AuthResponse.error("Email already registered");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        log.info("User registered successfully: {}", request.email());

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.email());

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.success(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }

    public AuthResponse login(LoginRequest request) {

        log.info("Login attempt for email: {}", request.email());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (Exception e) {
            log.warn("Login failed for email: {}", request.email());
            return AuthResponse.error("Invalid email or password");
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.email());

        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        log.info("Login successful for email: {}", request.email());

        return AuthResponse.success(
                token,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        );
    }
}
