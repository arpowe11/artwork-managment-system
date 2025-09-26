package com.apowell.artwork_backend.service;

import com.apowell.artwork_backend.model.User;
import com.apowell.artwork_backend.repository.UserRepository;
import com.apowell.artwork_backend.security.JwtUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void register(String firstName, String lastName, String email, String password, List<String> roles) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered.");
        }

        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));

        // If no roles provided, default to ROLE_USER
        if (roles == null || roles.isEmpty()) {
            roles = List.of("ROLE_USER");
        }

        u.setRoles(roles);
        userRepository.save(u);
    }

    public String login(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.findByEmail(email).orElseThrow();
            return jwtUtil.generateToken(user.getEmail(), user.getRoles());
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public String loginWithOAuth(OAuth2User oauthUser) {
        // Get email from Google OAuth
        String email = oauthUser.getAttribute("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // First-time login → create the user
            user = new User();
            user.setFirstName(oauthUser.getAttribute("given_name"));
            user.setLastName(oauthUser.getAttribute("family_name"));
            user.setEmail(email);

            // Assign default role
            user.setRoles(List.of("ROLE_USER"));
            userRepository.save(user);
        }

        // Return JWT
        return jwtUtil.generateToken(user.getEmail(), user.getRoles());
    }
}

