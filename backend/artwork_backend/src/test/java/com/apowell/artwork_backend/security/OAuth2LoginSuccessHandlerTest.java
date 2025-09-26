package com.apowell.artwork_backend.security;

import com.apowell.artwork_backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class OAuth2LoginSuccessHandlerTest {

    private AuthService authService;
    private OAuth2LoginSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        // JwtUtil isn’t actually used here, so just pass null
        successHandler = new OAuth2LoginSuccessHandler(null, authService);
    }

    @Test
    void onAuthenticationSuccess_shouldWriteHtmlWithToken() throws Exception {
        // Arrange
        String token = "fake-jwt-token";
        OAuth2User oauthUser = mock(OAuth2User.class);
        Authentication authentication = mock(Authentication.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        when(authentication.getPrincipal()).thenReturn(oauthUser);
        when(authService.loginWithOAuth(oauthUser)).thenReturn(token);
        when(response.getWriter()).thenReturn(writer);

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert service was called
        verify(authService).loginWithOAuth(oauthUser);

        // Assert correct headers/content type
        verify(response).setContentType("text/html");

        // Capture script written to response
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(captor.capture());

        String script = captor.getValue();
        assertThat(script).contains("window.opener.postMessage")
                .contains(token)
                .contains("window.close()");
    }
}
