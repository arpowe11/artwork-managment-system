package com.apowell.artwork_backend.controller;

import com.apowell.artwork_backend.dto.LoginRequest;
import com.apowell.artwork_backend.dto.RegisterRequest;
import com.apowell.artwork_backend.model.User;
import com.apowell.artwork_backend.repository.UserRepository;
import com.apowell.artwork_backend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class
        }
)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;


    // Test register success
    @Test
    void register_success() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Alex");
        request.setLastName("Powell");
        request.setEmail("alex@test.com");
        request.setPassword("password123");
        request.setRoles(List.of("USER"));

        doNothing().when(authService).register(anyString(), anyString(), anyString(), anyString(), anyList());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created"));
    }

    // Test register fails (duplicate email)
    @Test
    void register_conflict_duplicateEmail() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Alex");
        request.setLastName("Powell");
        request.setEmail("alex@test.com");
        request.setPassword("password123");
        request.setRoles(List.of("USER"));

        doThrow(new IllegalArgumentException("Email already exists"))
                .when(authService).register(anyString(), anyString(), anyString(), anyString(), anyList());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already exists"));
    }

    // Test register fails (unexpected error)
    @Test
    void register_internalServerError() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Sam");
        request.setLastName("Smith");
        request.setEmail("sam@test.com");
        request.setPassword("password123");
        request.setRoles(List.of("USER"));

        doThrow(new RuntimeException("DB is down"))
                .when(authService).register(anyString(), anyString(), anyString(), anyString(), anyList());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Something went wrong"));
    }

    // Test login success
    @Test
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("alex@test.com");
        request.setPassword("password123");

        when(authService.login("alex@test.com", "password123")).thenReturn("jwt-token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    // Test login failure (invalid credentials)
    @Test
    void login_invalidCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("alex@test.com");
        request.setPassword("wrongpassword");

        when(authService.login(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorMessage").value("Invalid credentials"))
                .andExpect(jsonPath("$.token").doesNotExist());
    }

    // Test getCurrentUser success
    @Test
    void getCurrentUser_success() throws Exception {
        User user = new User("2", "Alex", "Powell", "alex@test.com", "password123", List.of("ROLE_USER"));

        when(userRepository.findByEmail("alex@test.com")).thenReturn(Optional.of(user));

        Authentication auth = new UsernamePasswordAuthenticationToken(
                "alex@test.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        mockMvc.perform(get("/api/v1/auth/users")
                        .principal(auth)) // pass proper Authentication
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@test.com"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    // Test getCurrentUser unauthorized (no authentication)
    @Test
    void getCurrentUser_unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/auth/users"))
                .andExpect(status().isUnauthorized());
    }

    // Test getCurrentUser user not found
    @Test
    void getCurrentUser_userNotFound() throws Exception {
        when(userRepository.findByEmail("alex@test.com")).thenReturn(Optional.empty());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                "alex@test.com",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        mockMvc.perform(get("/api/v1/auth/users")
                        .principal(auth))
                .andExpect(status().isConflict());
    }


    //-----------------
    //   Edge Cases
    //-----------------

    @Test
    void register_invalidEmail() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Sam");
        request.setLastName("Smith");
        request.setEmail("invalid-email");
        request.setPassword("password123");
        request.setRoles(List.of("ROLE_USER"));

        doThrow(new IllegalArgumentException("Invalid email"))
                .when(authService).register(anyString(), anyString(), eq("invalid-email"), anyString(), anyList());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid email"));
    }

    @Test
    void login_wrongPassword() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("alex@test.com");
        request.setPassword("wrongpassword");

        when(authService.login("alex@test.com", "wrongpassword"))
                .thenThrow(new IllegalArgumentException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorMessage").value("Invalid credentials"));
    }
}

