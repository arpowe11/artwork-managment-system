package com.apowell.artwork_backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    // Use a long random secret for HS256; at least 32 bytes
    private static final String SECRET = "thisIsASecretKeyThatIsLongEnough1234567890";
    private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, EXPIRATION_MS);
    }

    @Test
    void generateToken_thenValidate_shouldBeValid() {
        String token = jwtUtil.generateToken("test@example.com", List.of("ROLE_USER"));

        boolean valid = jwtUtil.validateToken(token);

        assertThat(valid).isTrue();
    }

    @Test
    void generateToken_thenExtractUsername_shouldReturnCorrectUsername() {
        String token = jwtUtil.generateToken("test@example.com", List.of("ROLE_USER"));

        String username = jwtUtil.extractUsername(token);

        assertThat(username).isEqualTo("test@example.com");
    }

    @Test
    void generateToken_thenExtractRoles_shouldReturnCorrectRoles() {
        String token = jwtUtil.generateToken("test@example.com", List.of("ROLE_USER", "ROLE_ADMIN"));

        List<String> roles = jwtUtil.extractRoles(token);

        assertThat(roles).containsExactlyInAnyOrder("ROLE_USER", "ROLE_ADMIN");
    }

    @Test
    void validateToken_withInvalidToken_shouldReturnFalse() {
        String invalidToken = "thisIsNotAJwt";

        boolean valid = jwtUtil.validateToken(invalidToken);

        assertThat(valid).isFalse();
    }

    @Test
    void validateToken_withExpiredToken_shouldReturnFalse() throws InterruptedException {
        JwtUtil shortLivedJwtUtil = new JwtUtil(SECRET, 1); // expires after 1 ms
        String token = shortLivedJwtUtil.generateToken("expired@example.com", List.of("ROLE_USER"));

        // Wait a bit to ensure expiration
        Thread.sleep(5);

        boolean valid = shortLivedJwtUtil.validateToken(token);

        assertThat(valid).isFalse();
    }
}
