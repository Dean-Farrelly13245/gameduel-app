package com.example.gameduel;

public class Game {
    private int id;
    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private String coverImageUrl;
    private String description;
    private int wins;
    private int losses;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
    public int getReleaseYear() { return releaseYear; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public String getDescription() { return description; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
}