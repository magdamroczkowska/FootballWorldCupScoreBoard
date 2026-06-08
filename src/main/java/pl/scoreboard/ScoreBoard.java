package pl.scoreboard;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = findMatch(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }
}