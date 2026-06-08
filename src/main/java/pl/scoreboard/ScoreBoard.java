package pl.scoreboard;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startGame(Team homeTeam, Team awayTeam) {
        validateTeamsNotNull(homeTeam, awayTeam);
        validateTeamsAreDifferent(homeTeam, awayTeam);
        validateTeamIsNotPlaying(homeTeam, awayTeam);

        matches.add(new Match(homeTeam, awayTeam));
    }

    private static void validateTeamsNotNull(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
    }

    private static void validateTeamsAreDifferent(Team homeTeam, Team awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Teams must be different");
        }
    }

    private void validateTeamIsNotPlaying(Team homeTeam, Team awayTeam) {
        boolean teamAlreadyPlaying = matches.stream()
                .flatMap(m -> Stream.of(m.getHomeTeam(), m.getAwayTeam()))
                .anyMatch(team -> team.equals(homeTeam) || team.equals(awayTeam));

        if (teamAlreadyPlaying) {
            throw new IllegalArgumentException("Team is already playing");
        }
    }

    public void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        validateTeamsNotNull(homeTeam, awayTeam);
        Match match = findMatch(homeTeam, awayTeam);
        match.updateScore(homeScore, awayScore);
    }

    private Match findMatch(Team homeTeam, Team awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
    }

    public void finishGame(Team homeTeam, Team awayTeam) {
        validateTeamsNotNull(homeTeam, awayTeam);
        Match match = findMatch(homeTeam, awayTeam);
        matches.remove(match);
    }

    public List<Match> getSortedMatches() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt((Match m) -> m.getHomeTeamScore() + m.getAwayTeamScore())
                        .reversed()
                        .thenComparing(Match::getCreatedAt, Comparator.reverseOrder())
                )
                .toList();
    }
}