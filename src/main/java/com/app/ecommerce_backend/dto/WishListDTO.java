package com.app.ecommerce_backend.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record WishListDTO(
        Long id,
        String userEmail,
        List<WishListItemDTO> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
