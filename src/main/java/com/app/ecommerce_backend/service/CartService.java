package com.app.ecommerce_backend.service;

import com.app.ecommerce_backend.dto.CartDTO;
import com.app.ecommerce_backend.dto.CartItemDTO;

public interface CartService {

    CartDTO getCartByUser(Long userId);

    CartItemDTO addProductToCart(Long userId, Long productId, int quantity);

    CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity);

    void removeProductFromCart(Long cartItemId);
}
