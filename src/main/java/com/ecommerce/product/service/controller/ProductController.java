package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.UserDto;
import com.ecommerce.product.service.mappers.UserMapper;
import com.ecommerce.product.service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class ProductController {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUser(){
    return userRepository.findAll()
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
