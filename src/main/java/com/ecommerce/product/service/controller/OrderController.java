package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.entity.Order;
import com.ecommerce.product.service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final RestTemplate restTemplate = new RestTemplate();
    OrderRepository orderRepository;

    @PostMapping("/{orderId}")
    public String payNow(@PathVariable Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow();
        Integer value = order.getTotalPrice().intValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Integer> request = new HttpEntity<>(value, headers);
        return restTemplate.postForObject(
                "http://localhost:9000/razorpay/initiate",
                request,
                String.class
                );
    }
}

