package com.apowell.artwork_backend.controller;

import com.apowell.artwork_backend.repository.UserRepository;
import com.apowell.artwork_backend.service.AuthService;
import com.apowell.artwork_backend.dto.AuthResponse;
import com.apowell.artwork_backend.dto.RegisterRequest;
import com.apowell.artwork_backend.dto.LoginRequest;
import com.apowell.artwork_backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) { this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest r) {
        try {
            authService.register(r.getFirstName(), r.getLastName(), r.getEmail(), r.getPassword(), r.getRoles());
            return ResponseEntity.status(201).body(Map.of("message", "User created"));
        } catch (IllegalArgumentException e) {
            // username/email already exists
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Something went wrong"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest r) {
        try {
            String token = authService.login(r.getEmail(), r.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            // Invalid credentials
            return ResponseEntity.status(401).body(new AuthResponse(null, "Invalid credentials"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User not found"));
        System.out.println("USER: " + user.getFirstName() + " " + user.getLastName());
        return ResponseEntity.ok(Map.of(
                "firstName", user.getFirstName(),
                "email", user.getEmail(),
                "roles", user.getRoles()
        ));
    }
}
