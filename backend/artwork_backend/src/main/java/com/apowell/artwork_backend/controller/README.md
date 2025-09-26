# Spring Boot Controllers

## What is a Controller?

- A **Controller** is a class that handles **HTTP requests** from clients (like browsers or mobile apps).
- It acts as the **entry point** to your application.
- It decides what to do with incoming requests and which response to send back.
- In Spring Boot, controllers are typically annotated with:
    - `@RestController` → for APIs that return JSON or XML
    - `@Controller` → for applications that return HTML views (Thymeleaf, JSP, etc.)

---

## How Controllers Work

1. A client sends an HTTP request (GET, POST, PUT, DELETE, etc.).
2. The Controller receives the request.
3. The Controller calls a **Service** class to handle business logic (optional but recommended).
4. The Controller returns a **response** to the client.

---

## Basic Example

```java
package com.example.myproject.controller;

import com.example.myproject.model.Artwork;
import com.example.myproject.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artworks") // Base URL for all endpoints in this controller
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    // Get all artworks
    @GetMapping
    public List<Artwork> getAllArtworks() {
        return artworkService.getAllArtworks();
    }

    // Get artwork by title
    @GetMapping("/{title}")
    public Artwork getArtworkByTitle(@PathVariable String title) {
        return artworkService.getArtworkByTitle(title);
    }

    // Add a new artwork
    @PostMapping
    public Artwork addArtwork(@RequestBody Artwork artwork) {
        return artworkService.saveArtwork(artwork);
    }

    // Delete an artwork by ID
    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable String id) {
        artworkService.deleteArtwork(id);
    }
}
