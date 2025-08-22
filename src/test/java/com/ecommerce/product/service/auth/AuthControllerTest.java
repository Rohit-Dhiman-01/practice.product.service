package com.ecommerce.product.service.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerce.product.service.config.jwtConfigs.Jwt;
import com.ecommerce.product.service.config.jwtConfigs.JwtService;
import com.ecommerce.product.service.config.jwtConfigs.UserPrincipal;
import com.ecommerce.product.service.dtos.JwtDtos.LoginRequest;
import com.ecommerce.product.service.dtos.JwtDtos.LoginResponse;
import com.ecommerce.product.service.entity.Role;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.repository.UserRepository;
import com.ecommerce.product.service.services.AuthService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import com.ecommerce.product.service.exception.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock private JwtService jwtService;
    @Mock private UserRepository userRepository;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks private AuthService authService;

    private User user;

    @BeforeEach
    void  init() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
    }
    @Test
    void getCurrentUserTest(){
        UserPrincipal principal = new UserPrincipal(user.getId(),"Test User","test@example.com");

        when(authentication.getPrincipal()).thenReturn(principal);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = authService.getCurrentUser();

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void refreshAccessTokenTest(){
        when(jwtService.parseToken("bad")).thenReturn(null);

        assertThrows(BadCredentialsException.class,
                () -> authService.refreshAccessToken("bad"));
    }
    @Test
    void loginTest(){
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Jwt accessToken = mock(Jwt.class);
        Jwt refreshToken = mock(Jwt.class);

        when(jwtService.generateAccessToken(user)).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(accessToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }
}
