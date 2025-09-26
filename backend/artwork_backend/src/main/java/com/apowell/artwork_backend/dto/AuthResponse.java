package com.apowell.artwork_backend.dto;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String errorMessage;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
