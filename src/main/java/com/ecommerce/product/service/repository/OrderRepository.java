package com.ecommerce.product.service.repository;

import com.ecommerce.product.service.entity.Order;
import com.ecommerce.product.service.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    List<Order> getOrdersByCustomer(@Param("customer") User customer);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional<Order> getOrderWithItems(@Param("orderId") Long orderId);
}
