package com.ecommerce.product.service.dtos.usersDtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
}
