package com.app.ecommerce_backend.service;

import com.app.ecommerce_backend.dto.WishListDTO;
import com.app.ecommerce_backend.dto.WishListItemDTO;

public interface WishListService {

    WishListDTO getWishListByUser(Long userId);

    WishListItemDTO addProductToWishList(Long userId, Long productId);

    void removeProductFromWishList(Long wishListItemId);
}
