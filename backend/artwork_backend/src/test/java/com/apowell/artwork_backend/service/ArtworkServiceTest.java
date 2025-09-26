package com.apowell.artwork_backend.service;

import com.apowell.artwork_backend.model.Artwork;
import com.apowell.artwork_backend.repository.ArtworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ArtworkServiceTest {

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @InjectMocks
    private ArtworkService artworkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // CREATE
    @Test
    void createArtwork_success() {
        Artwork art = new Artwork();
        art.setTitle("Tree");

        when(sequenceGenerator.getNextSequence("artwork")).thenReturn(1);
        when(artworkRepository.existsById(1)).thenReturn(false);

        artworkService.createArtwork(art);

        verify(artworkRepository).save(art);
        assertEquals(1, art.getId());
    }

    @Test
    void createArtwork_conflict() {
        Artwork art = new Artwork();
        when(sequenceGenerator.getNextSequence("artwork")).thenReturn(1);
        when(artworkRepository.existsById(1)).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> artworkService.createArtwork(art));

        assertEquals("500 INTERNAL_SERVER_ERROR \"There was a problem creating the Artwork.\"", ex.getMessage());
    }

    // READ
    @Test
    void getAllArtworks_returnsList() {
        Artwork art1 = new Artwork();
        Artwork art2 = new Artwork();
        when(artworkRepository.findAll()).thenReturn(List.of(art1, art2));

        List<Artwork> result = artworkService.getAllArtworks();

        assertEquals(2, result.size());
    }

    @Test
    void getArtworkById_found() {
        Artwork art = new Artwork();
        art.setId(1);
        when(artworkRepository.findById(1)).thenReturn(Optional.of(art));

        Artwork result = artworkService.getArtworkById(1);
        assertEquals(1, result.getId());
    }

    @Test
    void getArtworkById_notFound() {
        when(artworkRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> artworkService.getArtworkById(99));
    }

    // UPDATE
    @Test
    void updateArtwork_success() {
        Artwork existing = new Artwork();
        existing.setId(1);
        when(artworkRepository.existsById(1)).thenReturn(true);
        when(artworkRepository.findById(1)).thenReturn(Optional.of(existing));

        Artwork update = new Artwork();
        update.setTitle("New Title");

        artworkService.updateArtwork(1, update);

        assertEquals("New Title", existing.getTitle());
        verify(artworkRepository).save(existing);
    }

    @Test
    void updateArtwork_notFound() {
        when(artworkRepository.existsById(99)).thenReturn(false);

        Artwork update = new Artwork();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> artworkService.updateArtwork(99, update));

        assertEquals("409 CONFLICT \"Artwork not found with ID 99\"", ex.getMessage());
    }

    // DELETE
    @Test
    void deleteArtwork_success() {
        when(artworkRepository.existsById(1)).thenReturn(true);

        artworkService.deleteArtwork(1);

        verify(artworkRepository).deleteById(1);
    }

    @Test
    void deleteArtwork_notFound() {
        when(artworkRepository.existsById(99)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> artworkService.deleteArtwork(99));

        assertEquals("409 CONFLICT \"Artwork not found with ID 99\"", ex.getMessage());
    }
}