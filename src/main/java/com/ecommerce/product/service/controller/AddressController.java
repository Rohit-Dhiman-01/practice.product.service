package com.ecommerce.product.service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/Address")
public class AddressController {
    @PostMapping
    public ResponseEntity<Void> createAddress(){
        return null;
    }
}
