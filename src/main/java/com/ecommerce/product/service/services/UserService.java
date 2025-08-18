package com.ecommerce.product.service.services;

import com.ecommerce.product.service.config.jwtConfigs.UserPrincipal;
import com.ecommerce.product.service.dtos.usersDtos.ChangePasswordRequest;
import com.ecommerce.product.service.dtos.usersDtos.RegisterUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UpdateUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.Role;
import com.ecommerce.product.service.exception.AccessDeniedException;
import com.ecommerce.product.service.exception.DuplicateUserException;
import com.ecommerce.product.service.exception.UserNotFoundException;
import com.ecommerce.product.service.mappers.UserMapper;
import com.ecommerce.product.service.repository.UserRepository;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Iterable<UserDto> getAllUsers(String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(Long id){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id: "+id));
        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateUserException("User with email "+ request.getEmail() +" already exists");
        }
            request.setPassword( passwordEncoder.encode(request.getPassword()) );
            var user = userMapper.toEntity(request);
            user.setRole(Role.USER);
            userRepository.save(user);

            return userMapper.toDto(user);
    }
    public UserDto updateUser(Long id, UpdateUserRequest request){
        var user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id "+ id));
        userMapper.update(request,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
    public void deleteUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id "+ userId));
        userRepository.delete(user);
    }
    public void changePassword(Long userId, ChangePasswordRequest request) {
        var user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found with id "+ userId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new AccessDeniedException("Invalid authentication principal");
        }
        if (!user.getEmail().equals(principal.email())) {
            throw new AccessDeniedException("You are not authorized to change this user's password");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AccessDeniedException("Password does not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
