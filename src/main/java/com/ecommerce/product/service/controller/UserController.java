package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.usersDtos.ChangePasswordRequest;
import com.ecommerce.product.service.dtos.usersDtos.RegisterUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UpdateUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Iterable<UserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "",name = "sort") String sort
    ){
        return userService.getAllUsers(sort);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping
    public ResponseEntity<?> createUser (
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder
    ){
        var userDto = userService.registerUser(request);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request
    ){
        return  userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/change-password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ){
        userService.changePassword(id, request);
    }

}
