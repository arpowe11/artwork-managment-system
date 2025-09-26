package com.apowell.artwork_backend.service;

import com.apowell.artwork_backend.model.User;
import com.apowell.artwork_backend.repository.UserRepository;
import com.apowell.artwork_backend.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() {
        when(userRepository.existsByEmail("alex@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        authService.register("Alex", "Powell", "alex@test.com", "password123", null);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_existingEmail_throwsException() {
        when(userRepository.existsByEmail("alex@test.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                authService.register("Alex", "Powell", "alex@test.com", "password123", null)
        );

        assertEquals("Email already registered.", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setEmail("alex@test.com");
        user.setRoles(List.of("ROLE_USER"));

        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(userRepository.findByEmail("alex@test.com")).thenReturn(java.util.Optional.of(user));
        when(jwtUtil.generateToken(user.getEmail(), user.getRoles())).thenReturn("token123");

        String token = authService.login("alex@test.com", "password123");

        assertEquals("token123", token);
    }

    @Test
    void login_wrongPassword_throwsException() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                authService.login("alex@test.com", "wrongpassword")
        );

        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void loginWithOAuth_newUser_createsAndGeneratesToken() {
        OAuth2User oauthUser = mock(OAuth2User.class);
        when(oauthUser.getAttribute("email")).thenReturn("newuser@test.com");
        when(oauthUser.getAttribute("given_name")).thenReturn("New");
        when(oauthUser.getAttribute("family_name")).thenReturn("User");

        when(userRepository.findByEmail("newuser@test.com")).thenReturn(java.util.Optional.empty());
        when(jwtUtil.generateToken("newuser@test.com", List.of("ROLE_USER"))).thenReturn("token123");

        String token = authService.loginWithOAuth(oauthUser);

        assertEquals("token123", token);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void loginWithOAuth_existingUser_generatesToken() {
        OAuth2User oauthUser = mock(OAuth2User.class);
        when(oauthUser.getAttribute("email")).thenReturn("alex@test.com");

        User user = new User();
        user.setEmail("alex@test.com");
        user.setRoles(List.of("ROLE_USER"));

        when(userRepository.findByEmail("alex@test.com")).thenReturn(java.util.Optional.of(user));
        when(jwtUtil.generateToken(user.getEmail(), user.getRoles())).thenReturn("token123");

        String token = authService.loginWithOAuth(oauthUser);

        assertEquals("token123", token);
        verify(userRepository, never()).save(any(User.class)); // No save for existing user
    }
}
