package com.ecommerce.product.service.services;

import com.ecommerce.product.service.dtos.usersDtos.ChangePasswordRequest;
import com.ecommerce.product.service.dtos.usersDtos.RegisterUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UpdateUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.exception.AccessDeniedException;
import com.ecommerce.product.service.exception.DuplicateUserException;
import com.ecommerce.product.service.exception.UserNotFoundException;
import com.ecommerce.product.service.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Iterable<UserDto> getAllUsers(String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(this::toUserDto)
                .toList();
    }

    public UserDto getUserById(Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id: "+id));
        return this.toUserDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateUserException("User with email "+ request.getEmail() +" already exists");
        }
            request.setPassword( passwordEncoder.encode(request.getPassword()) );
            var user = this.toEntity(request);
            userRepository.save(user);
            return this.toUserDto(user);
    }
    public UserDto updateUser(Long id, UpdateUserRequest request){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id "+ id));
        this.update(request,user);
        return this.toUserDto(user);
    }
    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id "+ userId));
        userRepository.delete(user);
    }
    public void changePassword(Long userId, ChangePasswordRequest request) {
        var user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id "+ userId));
        if (!request.getOldPassword().equals(user.getPassword())) {
            throw new AccessDeniedException("Password does not match");
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
//    Helper Functions
    private UserDto toUserDto(User user){
        return new UserDto(user.getId(),user.getName(),user.getEmail(), LocalDateTime.now());
    }
    private User toEntity(RegisterUserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
    private void update(UpdateUserRequest request, User user){
        user.setName(request.getName());
        user.setEmail(request.getEmail());
    }
}
