package com.example.gameduel;

public class Matchup {
    private int id;
    private int gameAId;
    private int gameBId;
    private Integer winnerId;
    private String createdAt;
    private String votedAt;

    public Matchup() {
    }

    public Matchup(int gameAId, int gameBId, Integer winnerId) {
        this.gameAId = gameAId;
        this.gameBId = gameBId;
        this.winnerId = winnerId;
    }

    public int getId() {
        return id;
    }

    public int getGameAId() {
        return gameAId;
    }

    public void setGameAId(int gameAId) {
        this.gameAId = gameAId;
    }

    public int getGameBId() {
        return gameBId;
    }

    public void setGameBId(int gameBId) {
        this.gameBId = gameBId;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getVotedAt() {
        return votedAt;
    }
}