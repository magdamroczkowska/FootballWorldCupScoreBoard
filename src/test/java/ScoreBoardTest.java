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
        scoreBoard.startGame("Mexico", "Canada");
        assertEquals(1, scoreBoard.getMatches().size());
    }

    @Test
    void shouldNotAllowSameTeamPlayingTwice() {
        scoreBoard.startGame("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("Mexico", "Brazil"));
    }

    @Test
    void shouldNotAllowDuplicateMatch() {
        scoreBoard.startGame("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("Mexico", "Canada"));
    }

    @Test
    void shouldNotAllowTeamPlayingAgainstItself() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("Mexico", "Mexico"));
    }

    @Test
    void shouldNotAllowEmptyOrBlankTeamNames() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("", "Canada"));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("Mexico", " "));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("   ", "   "));
    }

    @Test
    void shouldNotAllowNullTeamNames() {
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame(null, "Canada"));
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.startGame("Mexico", null));
    }

    @Test
    void shouldUpdateScore() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        Match match = scoreBoard.getMatches().get(0);
        assertEquals(0, match.getHomeScore());
        assertEquals(5, match.getAwayScore());
    }

    @Test
    void shouldNotUpdateNonExistingMatch() {
        assertTrue(scoreBoard.getMatches().isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore("Mexico", "Canada", 1, 1));
    }

    @Test
    void shouldNotAllowNegativeScores() {
        scoreBoard.startGame("Mexico", "Canada");

        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore("Mexico", "Canada", -1, 2));

        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.updateScore("Mexico", "Canada", 1, -5));
    }

    @Test
    void shouldFinishGame() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.finishGame("Mexico", "Canada");

        assertTrue(scoreBoard.getMatches().isEmpty());
    }

    @Test
    void shouldNotFinishNonExistingGame() {
        assertTrue(scoreBoard.getMatches().isEmpty());
        assertThrows(IllegalArgumentException.class,
                () -> scoreBoard.finishGame("Mexico", "Canada"));
    }

    @Test
    void shouldReturnSummarySortedByTotalScoreAndCreationOrder() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore("Germany", "France", 2, 2);

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreBoard.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Italy", summary.get(0).getAwayTeam());

        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Brazil", summary.get(1).getAwayTeam());

        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Canada", summary.get(2).getAwayTeam());

        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Australia", summary.get(3).getAwayTeam());

        assertEquals("Germany", summary.get(4).getHomeTeam());
        assertEquals("France", summary.get(4).getAwayTeam());
    }
}