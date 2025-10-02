package com.apowell.artwork_backend.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.apowell.artwork_backend.security.*;


@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    /**
     * API security chain - uses JWT only
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/artworks/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\":\"Forbidden - missing or invalid token\"}");
                        })
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .oauth2Login(AbstractHttpConfigurer::disable); // disable OAuth2 for API endpoints

        return http.build();
    }

    /**
     * Web/OAuth2 security chain
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurity(HttpSecurity http, OAuth2LoginSuccessHandler oauth2SuccessHandler, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/google").permitAll()
                        .requestMatchers("/oauth2/callback/google").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth -> oauth.successHandler(oauth2SuccessHandler))
                .cors(cors -> cors.configurationSource(corsConfigurationSource));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


// package com.apowell.artwork_backend.config;

// import com.apowell.artwork_backend.security.*;
// import jakarta.servlet.http.HttpServletResponse;

// import java.util.List;

// import org.springframework.context.annotation.*;
// import org.springframework.core.annotation.Order;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// @Configuration
// public class SecurityConfig {

//     private final CustomUserDetailsService userDetailsService;
//     private final JwtUtil jwtUtil;

//     public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
//         this.userDetailsService = userDetailsService;
//         this.jwtUtil = jwtUtil;
//     }

//     @Bean
//     public JwtAuthenticationFilter jwtAuthenticationFilter() {
//         return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
//     }

//     /**
//      * Security chain for API endpoints: uses JWT only.
//      */
//     @Bean
//     @Order(1)
//     public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
//         http
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                         .requestMatchers("/api/v1/auth/**").permitAll() // public auth endpoints
//                         .requestMatchers(HttpMethod.GET, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
//                         .requestMatchers(HttpMethod.POST, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
//                         .requestMatchers(HttpMethod.PUT, "/api/v1/artworks/**").hasAnyRole("USER", "ADMIN")
//                         .requestMatchers(HttpMethod.DELETE, "/api/v1/artworks/**").hasRole("ADMIN")
//                         .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                 .exceptionHandling(ex -> ex
//                         .authenticationEntryPoint((request, response, authException) -> {
//                             response.setContentType("application/json");
//                             response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                             response.getWriter().write("{\"error\":\"Forbidden - missing or invalid token\"}");
//                         })
//                 )
//                 .cors(Customizer.withDefaults());

//         // Disable OAuth2 for this chain to force 403 for unauthorized access
//         http.oauth2Login(AbstractHttpConfigurer::disable);

//         return http.build();
//     }

//     /**
//      * Security chain for web/OAuth2 login.
//      */
//     @Bean
//     @Order(2)
//     public SecurityFilterChain webSecurity(HttpSecurity http, OAuth2LoginSuccessHandler oauth2SuccessHandler) throws Exception {
//         http
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/v1/auth/google").permitAll()
//                         .requestMatchers("/oauth2/callback/google").permitAll()
//                         .anyRequest().permitAll()
//                 )
//                 .oauth2Login(oauth -> oauth
//                         .successHandler(oauth2SuccessHandler)
//                 )
//                 .cors(Customizer.withDefaults());

//         return http.build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
//         return cfg.getAuthenticationManager();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }

