package com.app.ecommerce_backend.repository;

import com.app.ecommerce_backend.model.User;
import com.app.ecommerce_backend.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUser(User user);
}