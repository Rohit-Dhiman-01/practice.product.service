package com.ecommerce.product.service.exception;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException() {
    super("Order not found");
  }
}
