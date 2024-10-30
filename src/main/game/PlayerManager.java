package main.game;

import java.time.Instant;

public class PlayerManager {

    private int score;
    private int bestScore;

    private double nextLevelRequiredScore;
    private final double nextLevelRequiredScoreMultiplier;
    private int level;

    private Instant startTime;

    public PlayerManager() {
        startTime = Instant.now();
        level = 1;
        nextLevelRequiredScore = 1;
        nextLevelRequiredScoreMultiplier = 1.5;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public void update() {
        if (score > bestScore) {
            bestScore = score;
        }
        if (score >= nextLevelRequiredScore) {
            updateLevelOptions();
        }
    }

    private void updateLevelOptions() {
        level++;
        nextLevelRequiredScore *= nextLevelRequiredScoreMultiplier;
    }

    public void reset() {
        score = 0;
        startTime = Instant.now();
        nextLevelRequiredScore = 1;
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public Instant getStartTime() {
        return startTime;
    }

}
