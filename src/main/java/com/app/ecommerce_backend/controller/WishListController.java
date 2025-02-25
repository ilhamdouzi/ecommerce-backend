package com.app.ecommerce_backend.controller;

import com.app.ecommerce_backend.dto.WishListDTO;
import com.app.ecommerce_backend.dto.WishListItemDTO;
import com.app.ecommerce_backend.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "WishList API", description = "Endpoints for managing the wishlist")
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "Get User WishList", description = "Retrieves the wishlist for the specified user by ID.")
    @GetMapping("/{userId}")
    public ResponseEntity<WishListDTO> getWishList(@PathVariable Long userId) {
        log.info("Retrieving wishlist for user with id: {}", userId);
        WishListDTO wishList = wishListService.getWishListByUser(userId);
        return ResponseEntity.ok(wishList);
    }

    @Operation(summary = "Add Product to WishList", description = "Adds a product to the user's wishlist.")
    @PostMapping("/{userId}/add")
    public ResponseEntity<WishListItemDTO> addProductToWishList(@PathVariable Long userId,
                                                                @RequestParam Long productId) {
        log.info("Adding product {} to wishlist for user with id: {}", productId, userId);
        WishListItemDTO wishListItem = wishListService.addProductToWishList(userId, productId);
        return ResponseEntity.ok(wishListItem);
    }

    @Operation(summary = "Remove Product from WishList", description = "Removes a product from the user's wishlist.")
    @DeleteMapping("/remove/{wishListItemId}")
    public ResponseEntity<Void> removeProductFromWishList(
            @PathVariable Long wishListItemId) {
        log.info("Removing wishlist item {}", wishListItemId);
        wishListService.removeProductFromWishList(wishListItemId);
        return ResponseEntity.noContent().build();
    }

}
