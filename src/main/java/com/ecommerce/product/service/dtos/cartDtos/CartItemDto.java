package com.ecommerce.product.service.dtos.cartDtos;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemDto {
    private ProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
