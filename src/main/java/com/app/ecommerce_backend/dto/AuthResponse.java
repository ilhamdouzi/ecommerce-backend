package com.app.ecommerce_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthResponse(@JsonProperty("access_token")
                           String accessToken,
                           @JsonProperty("refresh_token")
                           String refreshToken) {
}
