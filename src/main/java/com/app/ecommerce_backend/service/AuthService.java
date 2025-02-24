package com.app.ecommerce_backend.service;

import com.app.ecommerce_backend.dto.AuthRequest;
import com.app.ecommerce_backend.dto.AuthResponse;
import com.app.ecommerce_backend.dto.RegisterRequest;

public interface AuthService {

    public AuthResponse authenticate(AuthRequest request);

    public String register(RegisterRequest request);
}
