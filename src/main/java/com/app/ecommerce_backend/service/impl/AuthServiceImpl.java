package com.app.ecommerce_backend.service.impl;

import com.app.ecommerce_backend.dto.AuthRequest;
import com.app.ecommerce_backend.dto.AuthResponse;
import com.app.ecommerce_backend.dto.RegisterRequest;
import com.app.ecommerce_backend.exception.InvalidRequestException;
import com.app.ecommerce_backend.model.User;
import com.app.ecommerce_backend.repository.UserRepository;
import com.app.ecommerce_backend.security.JwtService;
import com.app.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        log.debug("Starting authentication process for email: {}", request.email());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.email());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        log.info("Authentication successful for email: {}", request.email());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String register(RegisterRequest request) {
        log.debug("Attempting to register user with email: {}", request.email());
        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.warn("Registration failed: email {} already taken", request.email());
            throw new InvalidRequestException("Email already taken");
        }

        User user = User.builder()
                .userName(request.username())
                .firstName(request.firstname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("USER")
                .build();

        userRepository.save(user);
        log.info("User registered successfully: {}", request.email());
        return "User registered successfully!";
    }
}
