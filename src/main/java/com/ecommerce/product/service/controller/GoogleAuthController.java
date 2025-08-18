package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.config.jwtConfigs.Jwt;
import com.ecommerce.product.service.config.jwtConfigs.JwtService;
import com.ecommerce.product.service.dtos.JwtDtos.JwtResponse;
import com.ecommerce.product.service.entity.Role;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.repository.UserRepository;
import com.ecommerce.product.service.security.UserDetailsServiceImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ecommerce.product.service.services.GoogleAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/google")
@AllArgsConstructor
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code){
        try {
            Map<String, String> tokens = googleAuthService.handleGoogleLogin(code);
            return ResponseEntity.ok(tokens);
        }catch (Exception e) {
            log.error("Error in GoogleAuthController: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Google authentication failed");
        }
    }
}
