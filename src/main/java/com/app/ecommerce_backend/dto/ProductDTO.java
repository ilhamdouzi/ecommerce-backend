package com.app.ecommerce_backend.dto;

import com.app.ecommerce_backend.model.enums.InventoryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ProductDTO(
        Long id,
        @NotNull
        String code,
        @NotNull
        String name,
        String description,
        String image,
        String category,
        @NotNull
        BigDecimal price,
        @NotNull
        Integer quantity,
        String internalReference,
        Integer shellId,
        @NotNull
        InventoryStatus inventoryStatus,
        BigDecimal rating,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
