package com.app.ecommerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(
        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Firstname must not be blank")
        String firstname,

        @NotBlank(message = "Password must not be blank")
        String password,

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        String email) {
}
