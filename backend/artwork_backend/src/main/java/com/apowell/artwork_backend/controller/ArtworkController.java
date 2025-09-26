package com.apowell.artwork_backend.controller;

import com.apowell.artwork_backend.service.ArtworkService;
import com.apowell.artwork_backend.model.Artwork;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }


    // POST: Create new Artwork
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addArtwork(@RequestBody Artwork artwork) {
        try {
            artworkService.createArtwork(artwork);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Artwork created successfully"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "409 CONFLICT \"Artwork already exists\""));
        }
    }

    // GET: Retrieve Artwork from the collection
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllArtworks() {
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    // GET: Retrieve Artwork from the collection by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getArtworkById(@PathVariable Integer id) {
        try {
            Artwork artwork = artworkService.getArtworkById(id);
            return ResponseEntity.ok(artwork);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Artwork ID " + id + " doesn't exist"));
        }
    }

    // PUT: Update a specific artwork by ID
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateArtwork(@PathVariable Integer id, @RequestBody Artwork artwork) {
        try {
            artworkService.updateArtwork(id, artwork);
            return ResponseEntity.ok(Map.of("message", "Artwork updated successfully"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Failed to update Artwork"));
        }
    }

    // DELETE: Delete an artwork by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteArtwork(@PathVariable Integer id) {
        try {
            artworkService.deleteArtwork(id);
            return ResponseEntity.ok(Map.of("message", "Artwork deleted successfully"));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Failed to delete Artwork"));
        }
    }
}
