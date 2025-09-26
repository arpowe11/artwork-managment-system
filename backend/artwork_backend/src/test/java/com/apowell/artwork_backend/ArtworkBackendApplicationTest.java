package com.apowell.artwork_backend;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class ArtworkBackendApplicationTest {

    @Test
    void main_runsSpringApplication() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            // Stub SpringApplication.run so it doesn't try to load context
            mocked.when(() -> SpringApplication.run(ArtworkBackendApplication.class, new String[]{}))
                    .thenReturn(null);

            // Call your main
            ArtworkBackendApplication.main(new String[]{});

            // Verify run() was called
            mocked.verify(() -> SpringApplication.run(ArtworkBackendApplication.class, new String[]{}));
        }
    }
}
