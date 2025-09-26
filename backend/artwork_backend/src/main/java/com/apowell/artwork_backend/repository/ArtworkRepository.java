package com.apowell.artwork_backend.repository;

import com.apowell.artwork_backend.model.Artwork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends MongoRepository<Artwork, Integer> {
    boolean existsByTitle(String title);
}
