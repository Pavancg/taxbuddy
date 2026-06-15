package com.taxbuddy.taxbuddy_user_service.controller;


import com.taxbuddy.taxbuddy_user_service.dto.AuthResponse;
import com.taxbuddy.taxbuddy_user_service.dto.LoginRequest;
import com.taxbuddy.taxbuddy_user_service.dto.RegisterRequest;
import com.taxbuddy.taxbuddy_user_service.service.AuthService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/users/auth")

@RequiredArgsConstructor

@Slf4j

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")

    public ResponseEntity<AuthResponse> register(

            @Valid @RequestBody RegisterRequest request) {

        log.info("Register request received for: {}", request.email());

        AuthResponse response = authService.register(request);

        if (response.token() == null) {

            return ResponseEntity

                    .status(HttpStatus.CONFLICT)

                    .body(response);

        }

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(response);

    }

    @PostMapping("/login")

    public ResponseEntity<AuthResponse> login(

            @Valid @RequestBody LoginRequest request) {

        log.info("Login request received for: {}", request.email());

        AuthResponse response = authService.login(request);

        if (response.token() == null) {

            return ResponseEntity

                    .status(HttpStatus.UNAUTHORIZED)

                    .body(response);

        }

        return ResponseEntity

                .status(HttpStatus.OK)

                .body(response);

    }

}
