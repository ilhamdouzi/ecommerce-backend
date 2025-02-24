package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.AuthRequest;
import com.app.ecommerce_backend.dto.AuthResponse;
import com.app.ecommerce_backend.dto.RegisterRequest;
import com.app.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse tokens = authService.authenticate(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String msg = authService.register(request);
        return ResponseEntity.ok(msg);
    }
}
