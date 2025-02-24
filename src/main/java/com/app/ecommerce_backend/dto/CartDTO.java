package com.app.ecommerce_backend.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CartDTO(Long id,
                      String userEmail,
                      List<CartItemDTO> items,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
}
