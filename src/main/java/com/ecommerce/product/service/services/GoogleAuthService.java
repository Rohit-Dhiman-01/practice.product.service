package com.ecommerce.product.service.services;


import com.ecommerce.product.service.config.jwtConfigs.JwtService;
import com.ecommerce.product.service.entity.Role;
import com.ecommerce.product.service.entity.User;
import com.ecommerce.product.service.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GoogleAuthService {

    private final String clientId;
    private final String clientSecret;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GoogleAuthService(
            @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.google.client-secret}") String clientSecret,
            RestTemplate restTemplate,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Map<String, String> handleGoogleLogin(String code) {
        try {
            var tokenEndPoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndPoint, request, Map.class);

            var idToken = (String) tokenResponse.getBody().get("id_token");
            var userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                var email = (String) userInfo.get("email");
                var name = (String) userInfo.get("name");

                User user;
                try {
                    user = userRepository.findByEmail(email).orElseThrow();
                } catch (Exception e) {
                    User userNew = new User();
                    userNew.setEmail(email);
                    userNew.setName(name);
                    var password = "password";
                    userNew.setPassword(passwordEncoder.encode(password));
                    userNew.setRole(Role.USER);
                    userRepository.save(userNew);
                    user = userRepository.findByEmail(email).orElseThrow();
                }

//                var accessToken = jwtService.generateAccessToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);

                Map<String, String> ans = new HashMap<>();
//                ans.put("access token", accessToken.toString());
                ans.put("token", refreshToken.toString());

                return ans;
            }
            throw new RuntimeException("Failed to fetch user info from Google");
        } catch (Exception e) {
            log.error("Error during Google login: {}", e.getMessage(), e);
            throw new RuntimeException("Google login failed", e);
        }
    }
}
