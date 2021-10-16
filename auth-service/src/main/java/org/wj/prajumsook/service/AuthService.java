package org.wj.prajumsook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.wj.prajumsook.model.TokenRequest;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Value("${keycloak.token.url}")
    private String tokenUrl;
    private RestTemplate restTemplate;
    private MultiValueMap<String, Object> postParam;
    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<ResponseEntity> getAccessToken(TokenRequest request) {
        return auth("password", request);
    }

    public Optional<ResponseEntity> refreshToken(TokenRequest request) {
        return auth("refresh_token", request);
    }

    private Optional<ResponseEntity> auth(String type, TokenRequest tokenRequest) {
        postParam = new LinkedMultiValueMap<>();
        postParam.add("client_id", tokenRequest.getClientId());
        postParam.add("client_secret", tokenRequest.getClientSecret());
        postParam.add("username", tokenRequest.getUsername());
        postParam.add("password", tokenRequest.getPassword());
        if("password".equalsIgnoreCase(type)) {
            postParam.add("grant_type", type);
        } else if("refresh_token".equalsIgnoreCase(type)) {
            postParam.add("grant_type", "refresh_token");
            postParam.add("refresh_token", tokenRequest.getRefreshToken());
        }

        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(postParam, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

        log.info("User found, now return access token for {}", tokenRequest.getUsername());
        return Optional.ofNullable(responseEntity);
    }
}
