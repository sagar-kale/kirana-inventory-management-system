package com.sagar.repository;

import com.sagar.entity.Order;
import com.sagar.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Product> findByProductId(Long productId, Pageable pageable);

    Optional<Product> findByIdAndProductId(Long orderId, Long productId);
}
