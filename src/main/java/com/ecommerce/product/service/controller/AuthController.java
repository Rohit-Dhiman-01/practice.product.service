package com.ecommerce.product.service.controller;

import com.ecommerce.product.service.dtos.JwtDtos.JwtResponse;
import com.ecommerce.product.service.dtos.JwtDtos.LoginRequest;
import com.ecommerce.product.service.dtos.usersDtos.UserDto;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.config.jwtConfigs.JwtConfig;
import com.ecommerce.product.service.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization API")
public class AuthController {
    private final JwtConfig jwtConfig;
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ){
        var loginResult = authService.login(request);
        var refreshToken = loginResult.getRefreshToken().toString();

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(loginResult.getAccessToken().toString());
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ){
        var accessToken = authService.refreshAccessToken(refreshToken);
        return new JwtResponse(accessToken.toString());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var user = authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        var userDto = this.toUserDto(user);
        return ResponseEntity.ok(userDto);
    }
    //    Helper Functions
    private UserDto toUserDto(User user){
        return new UserDto(user.getId(),user.getName(),user.getEmail(), LocalDateTime.now());
    }
}
