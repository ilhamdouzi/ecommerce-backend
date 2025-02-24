package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.CartItemDTO;
import com.app.ecommerce_backend.model.CartItem;

public class CartItemMapper {

    private CartItemMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CartItemDTO toDTO(CartItem cartItem) {
        return CartItemDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
