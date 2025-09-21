package com.ecommerce.product.service.dtos.orderDtos;

import com.ecommerce.product.service.dtos.productDtos.ProductDto;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private ProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
