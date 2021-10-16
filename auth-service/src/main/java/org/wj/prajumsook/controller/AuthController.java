package org.wj.prajumsook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wj.prajumsook.model.TokenRequest;
import org.wj.prajumsook.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity getToken(@RequestBody TokenRequest request) {
        log.info("Getting access token for user {}", request.getUsername());
        return authService.getAccessToken(request).get();
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestBody TokenRequest request) {
        log.info("Refresh token for user {}", request.getUsername());
        return authService.refreshToken(request).get();
    }
}
