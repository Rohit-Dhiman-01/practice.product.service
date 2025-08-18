package com.ecommerce.product.service.config.jwtConfigs;

public record UserPrincipal(Long userId, String name, String email) {}