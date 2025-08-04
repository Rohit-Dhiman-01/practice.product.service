package com.ecommerce.product.service.dtos.JwtDtos;

import com.ecommerce.product.service.security.config.Jwt;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private Jwt accessToken;
    private Jwt refreshToken;
}
