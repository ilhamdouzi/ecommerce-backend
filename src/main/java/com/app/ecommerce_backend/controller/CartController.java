package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.CartDTO;
import com.app.ecommerce_backend.dto.CartItemDTO;
import com.app.ecommerce_backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cart API", description = "Endpoints for managing the shopping cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Get User Cart", description = "Retrieves the shopping cart for the specified user by ID.")
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId) {
        log.info("Retrieving cart for user with id: {}", userId);
        CartDTO cart = cartService.getCartByUser(userId);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Add Product to Cart", description = "Adds a product to the user's cart with the specified quantity.")
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartItemDTO> addProductToCart(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        log.info("Adding product {} with quantity {} to cart for user with id: {}", productId, quantity, userId);
        CartItemDTO cartItem = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @Operation(summary = "Update Cart Item Quantity", description = "Updates the quantity of a specific cart item.")
    @PatchMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long cartItemId, @RequestParam int quantity) {
        log.info("Updating quantity for cart item {} to {}", cartItemId, quantity);
        CartItemDTO cartItem = cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @Operation(summary = "Remove Product from Cart", description = "Removes a product from the user's cart.")
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartItemId) {
        log.info("Removing cart item with id {} ", cartItemId);
        cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
