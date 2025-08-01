package com.ecommerce.product.service.dtos.productDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RegisterProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    @JsonProperty("category_id")
    private Byte categoryId;

}
