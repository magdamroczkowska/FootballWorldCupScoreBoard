package pl.scoreboard;

import lombok.Getter;

@Getter
public class Match {

    private final String homeTeam;
    private final String awayTeam;

    private int homeTeamScore;
    private int awayTeamScore;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    void updateScore(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}