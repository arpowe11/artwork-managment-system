package com.apowell.artwork_backend.security;

import com.apowell.artwork_backend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService userService; // Optional: service to save/check users

    public OAuth2LoginSuccessHandler(JwtUtil jwtTokenProvider,
                                     AuthService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, java.io.IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String token = userService.loginWithOAuth(oauthUser);

//        String frontendUrl = "http://localhost:5173/oauth2-redirect?token=" + token;
//        response.sendRedirect(frontendUrl);
        String script = "<script>" +
                "window.opener.postMessage({ token: '" + token + "' }, 'http://localhost:5173/');" +
                "window.close();" +
                "</script>";
        response.setContentType("text/html");
        response.getWriter().write(script);
    }
}
