package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.AuthRequest;
import com.app.ecommerce_backend.dto.AuthResponse;
import com.app.ecommerce_backend.dto.RegisterRequest;
import com.app.ecommerce_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication API", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User Login",
            description = "Authenticates a user with provided credentials and returns JWT tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials provided")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        log.info("Received login request for email: {}", request.email());
        AuthResponse tokens = authService.authenticate(request);
        log.info("User authenticated successfully for email: {}", request.email());
        return ResponseEntity.ok(tokens);
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user account using the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration details provided")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        log.info("Received registration request for username: {}", request.username());
        String msg = authService.register(request);
        log.info("User registered successfully: {}", request.username());
        return ResponseEntity.ok(msg);
    }
}
