package org.wj.prajumsook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String refreshToken;
}
