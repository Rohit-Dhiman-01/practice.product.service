package com.ecommerce.product.service.services;

import com.ecommerce.product.service.dtos.checkoutDtos.CheckoutRequest;
import com.ecommerce.product.service.dtos.checkoutDtos.CheckoutResponse;
import com.ecommerce.product.service.entity.Order;
import com.ecommerce.product.service.exception.CartEmptyException;
import com.ecommerce.product.service.exception.CartNotFoundException;
import com.ecommerce.product.service.repository.CartRepository;
import com.ecommerce.product.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;

    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        return new CheckoutResponse(order.getId());
    }
}
