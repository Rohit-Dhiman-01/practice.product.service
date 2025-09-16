package com.ecommerce.product.service.repository;

import com.ecommerce.product.service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {}
