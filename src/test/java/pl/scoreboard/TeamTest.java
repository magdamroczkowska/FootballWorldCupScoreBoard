package pl.scoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void shouldNotAllowBlankTeamName() {
        assertThrows(IllegalArgumentException.class, () -> new Team(" "));
        assertThrows(IllegalArgumentException.class, () -> new Team("   "));
    }

    @Test
    void shouldNormalizeMultiWordTeamName() {
        Team team = new Team("   meXiCo   ciTy  ");
        assertEquals("Mexico City", team.name());
    }

    @Test
    void shouldCapitalizeSingleWordName() {
        Team team = new Team("meXicO");
        assertEquals("Mexico", team.name());
    }

    @Test
    void shouldTrimAndNormalizeSpaces() {
        Team team = new Team("   united    states   ");
        assertEquals("United States", team.name());
    }

    @Test
    void shouldConsiderTeamsWithSameNameEqual() {
        Team team1 = new Team("mexico");
        Team team2 = new Team("Mexico");
        assertEquals(team1, team2);
    }
}