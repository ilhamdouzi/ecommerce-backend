package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.CartDTO;
import com.app.ecommerce_backend.model.Cart;

import java.util.stream.Collectors;

public class CartMapper {

    private CartMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .userEmail(cart.getUser().getEmail())
                .items(cart.getItems().stream()
                        .map(CartItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
}
