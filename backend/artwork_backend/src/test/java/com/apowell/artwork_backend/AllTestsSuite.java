package com.apowell.artwork_backend;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// Main entry point for all unit tests, can be executed using './mvnw test'
@Suite
@SelectClasses({
        com.apowell.artwork_backend.ArtworkBackendApplicationTest.class,
        com.apowell.artwork_backend.controller.ArtworkControllerTest.class,
        com.apowell.artwork_backend.controller.AuthControllerTest.class,
        com.apowell.artwork_backend.service.ArtworkServiceTest.class,
        com.apowell.artwork_backend.service.SequenceGeneratorTest.class,
        com.apowell.artwork_backend.service.AuthServiceTest.class,
        com.apowell.artwork_backend.config.CorsConfigTest.class,
        com.apowell.artwork_backend.security.JwtAuthenticationFilterTest.class,
        com.apowell.artwork_backend.security.JwtUtilTest.class,
        com.apowell.artwork_backend.security.OAuth2LoginSuccessHandlerTest.class,
})

public class AllTestsSuite {
    // No methods needed here
}

