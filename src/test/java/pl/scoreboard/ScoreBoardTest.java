package pl.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldStartGame() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        assertEquals(1, scoreBoard.getMatches().size());
    }

    @Test
    void shouldNotAllowSameTeamPlayingTwice() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("Mexico"), new Team("Brazil")));
    }

    @Test
    void shouldNotAllowDuplicateMatch() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("Mexico"), new Team("Canada")));
    }

    @Test
    void shouldNotAllowTeamPlayingAgainstItself() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("Mexico"), new Team("Mexico")));
    }

    @Test
    void shouldNotAllowEmptyOrBlankTeamNames() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team(""), new Team("Canada")));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("Mexico"), new Team(" ")));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("   "), new Team("   ")));
    }

    @Test
    void shouldNotAllowNullTeamNames() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(null, new Team("Canada")));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(new Team("Mexico"), null));
    }

    @Test
    void shouldUpdateScore() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        scoreBoard.updateScore(new Team("Mexico"), new Team("Canada"), 0, 5);

        Match match = scoreBoard.getMatches().get(0);
        assertEquals(0, match.getHomeTeamScore());
        assertEquals(5, match.getAwayTeamScore());
    }

    @Test
    void shouldNotUpdateNonExistingMatch() {
        assertTrue(scoreBoard.getMatches().isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore(new Team("Mexico"), new Team("Canada"), 1, 1));
    }

    @Test
    void shouldNotAllowNegativeScores() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));

        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore(new Team("Mexico"), new Team("Canada"), -1, 2));

        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore(new Team("Mexico"), new Team("Canada"), 1, -5));
    }

    @Test
    void shouldFinishGame() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        scoreBoard.finishGame(new Team("Mexico"), new Team("Canada"));

        assertTrue(scoreBoard.getMatches().isEmpty());
    }

    @Test
    void shouldNotFinishNonExistingGame() {
        assertTrue(scoreBoard.getMatches().isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.finishGame(new Team("Mexico"), new Team("Canada")));
    }

    @Test
    void shouldReturnSummarySortedByTotalScoreAndCreationOrder() {
        scoreBoard.startGame(new Team("Mexico"), new Team("Canada"));
        scoreBoard.updateScore(new Team("Mexico"), new Team("Canada"), 0, 5);

        scoreBoard.startGame(new Team("Spain"), new Team("Brazil"));
        scoreBoard.updateScore(new Team("Spain"), new Team("Brazil"), 10, 2);

        scoreBoard.startGame(new Team("Germany"), new Team("France"));
        scoreBoard.updateScore(new Team("Germany"), new Team("France"), 2, 2);

        scoreBoard.startGame(new Team("Uruguay"), new Team("Italy"));
        scoreBoard.updateScore(new Team("Uruguay"), new Team("Italy"), 6, 6);

        scoreBoard.startGame(new Team("Argentina"), new Team("Australia"));
        scoreBoard.updateScore(new Team("Argentina"), new Team("Australia"), 3, 1);

        List<Match> sortedMatches = scoreBoard.getSortedMatches();

        assertEquals("Uruguay", sortedMatches.get(0).getHomeTeam().name());
        assertEquals("Italy", sortedMatches.get(0).getAwayTeam().name());

        assertEquals("Spain", sortedMatches.get(1).getHomeTeam().name());
        assertEquals("Brazil", sortedMatches.get(1).getAwayTeam().name());

        assertEquals("Mexico", sortedMatches.get(2).getHomeTeam().name());
        assertEquals("Canada", sortedMatches.get(2).getAwayTeam().name());

        assertEquals("Argentina", sortedMatches.get(3).getHomeTeam().name());
        assertEquals("Australia", sortedMatches.get(3).getAwayTeam().name());

        assertEquals("Germany", sortedMatches.get(4).getHomeTeam().name());
        assertEquals("France", sortedMatches.get(4).getAwayTeam().name());
    }
}