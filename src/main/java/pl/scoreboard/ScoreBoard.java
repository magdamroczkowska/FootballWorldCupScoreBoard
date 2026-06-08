package pl.scoreboard;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);

        matches.add(new Match(homeTeam, awayTeam));
    }

    private void validateTeamName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null or blank");
        }
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