package com.apowell.artwork_backend.service;

import com.apowell.artwork_backend.model.Artwork;
import com.apowell.artwork_backend.repository.ArtworkRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;
    private final SequenceGeneratorService sequenceGenerator;

    public ArtworkService(ArtworkRepository artworkRepository, SequenceGeneratorService sequenceGenerator) {
        this.artworkRepository = artworkRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    // Creates a new artwork
    public void createArtwork(Artwork artwork) {
        try {
            artwork.setId(sequenceGenerator.getNextSequence("artwork"));  // Auto increment the id for artwork

            if (artworkRepository.existsById(artwork.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Artwork already exists, please use a different id");
            }

            this.artworkRepository.save(artwork);

        } catch (DataAccessException err) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Database error.", err);
        } catch (Exception err) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "There was a problem creating the Artwork.", err);
        }
    }

    // Gets all the artwork
    public List<Artwork> getAllArtworks() {
        return this.artworkRepository.findAll();
    }

    // Get artwork by ID
    public Artwork getArtworkById(Integer id) {
        return this.artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Artwork with ID " + id + " does not exist"));
    }

    // Update artwork by ID
    public void updateArtwork(Integer id, Artwork update) {
        checkIfArtworkExists(id);

        Artwork artwork = this.getArtworkById(id);
        artwork.setTitle(update.getTitle());
        artwork.setArtist(update.getArtist());
        artwork.setDescription(update.getDescription());
        artwork.setArtType(update.getArtType());
        artwork.setThumbnail(update.getThumbnail());

        this.artworkRepository.save(artwork);
    }

    // Delete artwork by ID
    public void deleteArtwork(Integer id) {
        checkIfArtworkExists(id);
        this.artworkRepository.deleteById(id);
    }

    private void checkIfArtworkExists(Integer id) {
        if (!this.artworkRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Artwork not found with ID " + id);
        }
    }
}
