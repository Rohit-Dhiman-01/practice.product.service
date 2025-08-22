package com.ecommerce.product.service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ecommerce.product.service.dtos.usersDtos.RegisterUserRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.Role;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.exception.DuplicateUserException;
import com.ecommerce.product.service.mappers.UserMapper;
import com.ecommerce.product.service.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    RegisterUserRequest request;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    private User user1, user2;
    private UserDto dto1, dto2;


    @BeforeEach
    void init() {
        request = new RegisterUserRequest();
        request.setEmail("rohit@gmail.com");
        request.setPassword("password");

        user1 = new User();
        user1.setName("test1");
        user1.setEmail("test1@example.com");

        user2 = new User();
        user2.setName("test2");
        user2.setEmail("test2@example.com");

        dto1 = new UserDto();
        dto1.setName("test1");
        dto1.setEmail("test1@example.com");

        dto2 = new UserDto();
        dto2.setName("test2");
        dto2.setEmail("test2@example.com");
    }
    @Test
    void registerUserExceptionTest() {

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        DuplicateUserException ex = assertThrows(DuplicateUserException.class,
                () -> userService.registerUser(request));

        assertEquals("User with email rohit@gmail.com already exists", ex.getMessage());
        verify(userRepository, never()).save(any());
    }
    @Test
    void registerUserTest(){

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword("newPassword");
        user.setRole(Role.USER);

        UserDto expectedDto = new UserDto();
        expectedDto.setEmail(request.getEmail());

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("newPassword");
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserDto result = userService.registerUser(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("rohit@gmail.com", result.getEmail());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }
//    --------------------------
    @Test
    void getAllUsersEmailTest(){
        when(userRepository.findAll(Sort.by("email"))).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.toDto(user1)).thenReturn(dto1);
        when(userMapper.toDto(user2)).thenReturn(dto2);

        Iterable<UserDto> result = userService.getAllUsers("email");
        List<UserDto> resultList = (List<UserDto>) result;
        assertEquals(2, resultList.size());
        assertEquals("test1@example.com", resultList.get(0).getEmail());

        verify(userRepository).findAll(Sort.by("email"));
        verify(userMapper, times(2)).toDto(any(User.class));

    }
    @Test
    void getAllUsersNameTest(){
        when(userRepository.findAll(Sort.by("name"))).thenReturn(Arrays.asList(user1));
        when(userMapper.toDto(user1)).thenReturn(dto1);

        Iterable<UserDto> result = userService.getAllUsers("invalid");

        List<UserDto> resultList = (List<UserDto>) result;
        assertEquals(1, resultList.size());
        assertEquals("test1", resultList.get(0).getName());

        verify(userRepository).findAll(Sort.by("name"));
        verify(userMapper).toDto(user1);
    }
}
