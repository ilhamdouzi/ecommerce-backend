package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.AuthRequest;
import com.app.ecommerce_backend.dto.AuthResponse;
import com.app.ecommerce_backend.dto.RegisterRequest;
import com.app.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("Received login request for email: {}", request.email());
        AuthResponse tokens = authService.authenticate(request);
        log.info("User authenticated successfully for email: {}", request.email());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        log.info("Received registration request for username: {}", request.username());
        String msg = authService.register(request);
        log.info("User registered successfully: {}", request.username());
        return ResponseEntity.ok(msg);
    }
}
