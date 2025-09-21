package com.ecommerce.product.service.services;

import com.ecommerce.product.service.dtos.orderDtos.OrderDto;
import com.ecommerce.product.service.entity.Order;
import com.ecommerce.product.service.exception.AccessDeniedException;
import com.ecommerce.product.service.exception.OrderNotFoundException;
import com.ecommerce.product.service.mappers.OrderMapper;
import com.ecommerce.product.service.repository.OrderRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class OrderService {
    public final AuthService authService;
    public final OrderRepository orderRepository;
    public final OrderMapper orderMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId){
        var order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You don't have access to this order.");
        }
        return orderMapper.toDto(order);
    }
//  --------------------------
    public String payNow(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
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