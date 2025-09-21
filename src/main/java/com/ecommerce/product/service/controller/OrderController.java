package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.orderDtos.OrderDto;
import com.ecommerce.product.service.services.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{orderId}")
    public String payNow(@PathVariable Long orderId){
        return orderService.payNow(orderId);
    }
//-----
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }
}

