package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.WishListDTO;
import com.app.ecommerce_backend.model.WishList;

import java.util.stream.Collectors;

public class WishListMapper {

    private WishListMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static WishListDTO toDTO(WishList wishList) {
        return WishListDTO.builder()
                .id(wishList.getId())
                .userEmail(wishList.getUser().getEmail())
                .items(wishList.getItems().stream()
                        .map(WishListItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .createdAt(wishList.getCreatedAt())
                .updatedAt(wishList.getUpdatedAt())
                .build();
    }
}
