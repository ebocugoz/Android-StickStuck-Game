package com.cs310.erdembocugoz;

/**
 * Created by admin on 06/05/16.
 */
public class Score {

    private long id;
    private long score;
    private String playerName;

    public Score() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.valueOf(score) + "@" + playerName;
    }
}
