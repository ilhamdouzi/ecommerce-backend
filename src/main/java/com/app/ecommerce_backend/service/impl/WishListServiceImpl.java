package com.app.ecommerce_backend.service.impl;

import com.app.ecommerce_backend.dto.WishListDTO;
import com.app.ecommerce_backend.dto.WishListItemDTO;
import com.app.ecommerce_backend.exception.ResourceNotFoundException;
import com.app.ecommerce_backend.mapper.WishListItemMapper;
import com.app.ecommerce_backend.mapper.WishListMapper;
import com.app.ecommerce_backend.model.Product;
import com.app.ecommerce_backend.model.User;
import com.app.ecommerce_backend.model.WishList;
import com.app.ecommerce_backend.model.WishListItem;
import com.app.ecommerce_backend.repository.ProductRepository;
import com.app.ecommerce_backend.repository.UserRepository;
import com.app.ecommerce_backend.repository.WishListItemRepository;
import com.app.ecommerce_backend.repository.WishListRepository;
import com.app.ecommerce_backend.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final WishListItemRepository wishListItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public WishListDTO getWishListByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        WishList wishList = wishListRepository.findByUser(user)
                .orElseGet(() -> {
                    WishList newWishList = WishList.builder().user(user).build();
                    WishList savedWishList = wishListRepository.save(newWishList);
                    log.debug("Created new wishlist for user with id: {}", userId);
                    return savedWishList;
                });
        log.debug("Fetched wishlist for user with id: {}", userId);
        return WishListMapper.toDTO(wishList);
    }

    @Override
    public WishListItemDTO addProductToWishList(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        WishList wishList = wishListRepository.findByUser(user)
                .orElseGet(() -> wishListRepository.save(WishList.builder().user(user).build()));

        Optional<WishListItem> optionalWishListItem = wishList.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (optionalWishListItem.isPresent()) {
            log.debug("Product id: {} already exists in wishlist for user with id: {}", productId, userId);
            return WishListItemMapper.toDTO(optionalWishListItem.get());
        } else {
            WishListItem wishListItem = WishListItem.builder()
                    .wishList(wishList)
                    .product(product)
                    .build();
            wishList.getItems().add(wishListItem);
            wishListItemRepository.save(wishListItem);
            log.debug("Added product id: {} to wishlist for user with id: {}", productId, userId);
            return WishListItemMapper.toDTO(wishListItem);
        }
    }

    @Override
    public void removeProductFromWishList(Long wishListItemId) {
        WishListItem wishListItem = wishListItemRepository.findById(wishListItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found with id: " + wishListItemId));
        wishListItemRepository.delete(wishListItem);
        log.debug("Removed wishlist item with id: {} ", wishListItemId);
    }
}
