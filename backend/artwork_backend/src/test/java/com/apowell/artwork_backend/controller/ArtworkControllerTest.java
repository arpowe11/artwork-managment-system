package com.apowell.artwork_backend.controller;

import com.apowell.artwork_backend.model.Artwork;
import com.apowell.artwork_backend.service.ArtworkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = ArtworkController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class
        }
)
public class ArtworkControllerTest {

    @Autowired
    private MockMvc mockMvc; // Allows sending HTTP requests to the controller in tests

    @Autowired
    private ObjectMapper objectMapper; // Converts Java objects to/from JSON

    @MockBean
    private ArtworkService artworkService;  // Mocked service layer (we don’t hit the real DB)

    // Test POST: Creating artwork successfully
    @Test
    void createArtwork_success() throws Exception {
        // Arrange: create a valid artwork object
        Artwork artwork = new Artwork();
        artwork.setTitle("Fall Tree");
        artwork.setArtist("Alex Powell");
        artwork.setDescription("Fall colored tree");
        artwork.setArtType("Canvas");
        artwork.setThumbnail("N/A");

        // Act + Assert: Perform POST request, expect 201 CREATED with success message
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Artwork created successfully"));

        // Verify that service method was called once
        verify(artworkService).createArtwork(any(Artwork.class));
    }

    // Test POST: Conflict when creating duplicate artwork
    @Test
    void createArtwork_conflict() throws Exception {
        // Arrange: minimal artwork with duplicate title
        Artwork artwork = new Artwork();
        artwork.setTitle("Fall Tree");
        artwork.setArtist("Alex Powell");

        // Mock service to throw conflict
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate title"))
                .when(artworkService).createArtwork(any(Artwork.class));

        // Act + Assert: Perform POST, expect 409 CONFLICT with error message
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("409 CONFLICT \"Artwork already exists\""));
    }

    // Test GET: Retrieve all artworks (list of 2)
    @Test
    void getAllArtworks_returnsList() throws Exception {
        // Arrange: mock service to return two artworks
        Artwork art1 = new Artwork();
        art1.setId(1);
        art1.setTitle("Tree");
        art1.setArtist("Alex");

        Artwork art2 = new Artwork();
        art2.setId(2);
        art2.setTitle("Ocean");
        art2.setArtist("Sam");

        when(artworkService.getAllArtworks()).thenReturn(List.of(art1, art2));

        // Act + Assert: Perform GET, expect list of artworks in JSON
        mockMvc.perform(get("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Tree"))
                .andExpect(jsonPath("$[1].title").value("Ocean"));
    }

    // Test GET: Retrieve single artwork by ID (success case)
    @Test
    void getArtworkById_returnsArtwork_whenFound() throws Exception {
        // Arrange: mock service to return one artwork
        Artwork art = new Artwork();
        art.setId(1);
        art.setTitle("Tree");
        art.setArtist("Alex");

        when(artworkService.getArtworkById(1)).thenReturn(art);

        // Act + Assert: Perform GET /1, expect artwork in JSON
        mockMvc.perform(get("/api/v1/artworks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tree"))
                .andExpect(jsonPath("$.artist").value("Alex"));
    }

    // Test GET: Artwork by ID returns NOT_FOUND
    @Test
    void getArtworkById_returnsNotFound_whenMissing() throws Exception {
        // Arrange: mock service to throw exception
        when(artworkService.getArtworkById(anyInt()))
                .thenThrow(new IllegalStateException("Not found"));

        // Act + Assert: Perform GET /99, expect 404 NOT FOUND
        mockMvc.perform(get("/api/v1/artworks/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Artwork ID 99 doesn't exist"));
    }

    // Test PUT: Update artwork successfully
    @Test
    void testUpdateArtwork_Success() throws Exception {
        // Arrange: create updated artwork object
        Artwork updatedArtwork = new Artwork(1, "New Title", "New Artist", "New Desc", "Painting", "N/A");

        // Mock service to do nothing (no error)
        doNothing().when(artworkService).updateArtwork(eq(1), any(Artwork.class));

        // Act + Assert: Perform PUT /1, expect 200 OK with success message
        mockMvc.perform(put("/api/v1/artworks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedArtwork)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Artwork updated successfully"));

        // Verify service call
        verify(artworkService, times(1)).updateArtwork(eq(1), any(Artwork.class));
    }

    // Test PUT: Conflict when updating artwork
    @Test
    void testUpdateArtwork_Conflict() throws Exception {
        // Arrange: artwork with duplicate info
        Artwork updatedArtwork = new Artwork(1, "Title", "Artist", "Desc", "Type", "N/A");

        // Mock service to throw conflict
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Artwork already exists"))
                .when(artworkService).updateArtwork(eq(1), any(Artwork.class));

        // Act + Assert: Perform PUT, expect 409 CONFLICT
        mockMvc.perform(put("/api/v1/artworks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedArtwork)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Failed to update Artwork"));
    }

    // Test DELETE: Delete artwork successfully
    @Test
    void testDeleteArtwork_Success() throws Exception {
        // Arrange: mock service delete to succeed
        doNothing().when(artworkService).deleteArtwork(1);

        // Act + Assert: Perform DELETE /1, expect 200 OK with success message
        mockMvc.perform(delete("/api/v1/artworks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Artwork deleted successfully"));

        // Verify service call
        verify(artworkService, times(1)).deleteArtwork(1);
    }

    // Test DELETE: Conflict when delete fails
    @Test
    void testDeleteArtwork_Conflict() throws Exception {
        // Arrange: mock service to throw conflict
        doThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Failed to delete Artwork"))
                .when(artworkService).deleteArtwork(1);

        // Act + Assert: Perform DELETE, expect 409 CONFLICT
        mockMvc.perform(delete("/api/v1/artworks/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Failed to delete Artwork"));
    }

    //-----------------
    //   Edge Cases
    //-----------------

    // Test GET: Tests if the artwork collection was empty and returned an empty list
    @Test
    void getAllArtworks_emptyList() throws Exception {
        when(artworkService.getAllArtworks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/artworks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}


