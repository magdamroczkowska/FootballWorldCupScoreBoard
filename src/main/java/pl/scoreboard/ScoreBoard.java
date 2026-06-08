package pl.scoreboard;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);

        validateIfTeamNamesAreNotTheSame(homeTeam, awayTeam);
        validateIfTeamIsNotAlreadyPlaying(homeTeam, awayTeam);

        matches.add(new Match(homeTeam, awayTeam));
    }

    private void validateTeamName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null or blank");
        }
    }

    private static void validateIfTeamNamesAreNotTheSame(String homeTeam, String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Teams must be different");
        }
    }

    private void validateIfTeamIsNotAlreadyPlaying(String homeTeam, String awayTeam) {
        boolean teamAlreadyPlaying = matches.stream()
                .flatMap(m -> Stream.of(m.getHomeTeam(), m.getAwayTeam()))
                .anyMatch(team -> team.equals(homeTeam) || team.equals(awayTeam));

        if (teamAlreadyPlaying) {
            throw new IllegalArgumentException("Team is already playing");
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