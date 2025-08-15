package com.ecommerce.product.service.services;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GoogleAuthService {
//
//    private final String clientId ;
//    private final String clientSecret ;
//    private final RestTemplate restTemplate;
//
//    public void handleGoogleCallback(String code){
//            var tokenEndPoint  = "https://oauth2.googleapis.com/token";
//            Map<String, String> params= new HashMap<>();
//            params.put("code", code);
//            params.put("client_id", clientId);
//            params.put("client_secret", clientSecret);
//            params.put("redirect_uri", "https://developers.google.com/oauthplayground");
//            params.put("grand_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType (MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<Map<String,String>> request = new HttpEntity<>(params,headers);
//        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndPoint, request, Map.class);
//
//        var idToken = (String) tokenResponse.getBody().get("id_token");
//        var userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
//    }
}
