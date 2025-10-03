package com.apowell.artwork_backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedOrigins(
//                                 "https://ams-frontend-bkg8bmdhhsabbfem.canadacentral-01.azurewebsites.net",
//                                 "https://ams-frontend-bkg8bmdhhsabbfem.canadacentral-01.azurewebsites.net/",
//                                 "http://localhost:5173"
//                                 )
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                         .allowedHeaders("Authorization", "Content-Type")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "https://ams-frontend-bkg8bmdhhsabbfem.canadacentral-01.azurewebsites.net",
                "https://ams-frontend-bkg8bmdhhsabbfem.canadacentral-01.azurewebsites.net/",
                "http://localhost:5173"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization","Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
