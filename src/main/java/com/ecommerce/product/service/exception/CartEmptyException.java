package com.ecommerce.product.service.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException() {super("Cart is empty");}
}
