package com.app.ecommerce_backend.service.impl;

import com.app.ecommerce_backend.dto.CartDTO;
import com.app.ecommerce_backend.dto.CartItemDTO;
import com.app.ecommerce_backend.exception.ResourceNotFoundException;
import com.app.ecommerce_backend.mapper.CartItemMapper;
import com.app.ecommerce_backend.mapper.CartMapper;
import com.app.ecommerce_backend.model.Cart;
import com.app.ecommerce_backend.model.CartItem;
import com.app.ecommerce_backend.model.Product;
import com.app.ecommerce_backend.model.User;
import com.app.ecommerce_backend.repository.CartItemRepository;
import com.app.ecommerce_backend.repository.CartRepository;
import com.app.ecommerce_backend.repository.ProductRepository;
import com.app.ecommerce_backend.repository.UserRepository;
import com.app.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public CartDTO getCartByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    Cart savedCart = cartRepository.save(newCart);
                    log.debug("Created new cart for user with id: {}", userId);
                    return savedCart;
                });
        log.debug("Fetched cart for user with id: {}", userId);
        return CartMapper.toDTO(cart);
    }

    @Override
    public CartItemDTO addProductToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        Optional<CartItem> optionalCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        CartItem cartItem;
        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            log.debug("Updated cart item quantity for product id: {} in user cart with id: {}", productId, userId);
        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(cartItem);
            log.debug("Added new product id: {} to cart for user with id: {}", productId, userId);
        }
        cartItemRepository.save(cartItem);
        return CartItemMapper.toDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        log.debug("Updated quantity for cart item id: {} to {} ", cartItemId, quantity);
        return CartItemMapper.toDTO(cartItem);
    }

    @Override
    public void removeProductFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));
        cartItemRepository.delete(cartItem);
        Long userId = Optional.of(cartItem)
                .map(CartItem::getCart)
                .map(Cart::getUser)
                .map(User::getId)
                .orElse(null);
        log.debug("Removed cart item id: {} for user with id: {}", cartItemId, userId);

    }
}
