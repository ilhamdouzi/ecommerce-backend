package com.app.ecommerce_backend.dto;

import lombok.Builder;

@Builder
public record WishListItemDTO(Long id,
                              Long productId,
                              String productName) {
}
