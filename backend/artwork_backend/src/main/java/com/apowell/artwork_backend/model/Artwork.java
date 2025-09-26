package com.apowell.artwork_backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "artwork")
public class Artwork {

    @Id
    private int id;
    private String title;
    private String artist;;
    private String description;
    private String art_type;
    private String thumbnail;

    public Artwork() {}

    public Artwork(int id, String title, String artist, String description, String art_type, String thumbnail) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.art_type = art_type;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtType() {
        return this.art_type;
    }

    public void setArtType(String art_type) {
        this.art_type = art_type;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
