package com.app.ecommerce_backend.mapper;

import com.app.ecommerce_backend.dto.WishListItemDTO;
import com.app.ecommerce_backend.model.WishListItem;

public class WishListItemMapper {

    private WishListItemMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static WishListItemDTO toDTO(WishListItem wishListItem) {
        return WishListItemDTO.builder()
                .id(wishListItem.getId())
                .productId(wishListItem.getProduct().getId())
                .productName(wishListItem.getProduct().getName())
                .build();
    }
}
