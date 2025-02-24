package com.app.ecommerce_backend.dto;

import lombok.Builder;

@Builder
public record CartItemDTO(Long id,
                          Long productId,
                          String productName,
                          Integer quantity) {
}
