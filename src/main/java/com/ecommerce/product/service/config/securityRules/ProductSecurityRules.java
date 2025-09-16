package com.ecommerce.product.service.config.securityRules;

import com.ecommerce.product.service.entity.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurityRules implements SecurityRules{
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                .requestMatchers(HttpMethod.POST, "/api/v1/products/**")
                .hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**")
                .hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                .hasRole(Role.ADMIN.name());
    }
}
