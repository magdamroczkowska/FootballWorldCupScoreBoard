package pl.scoreboard;

public class Main {
    public static void main(String[] args) {

        ScoreBoard board = new ScoreBoard();

        board.startGame(new Team("Mexico"), new Team("Poland"));
        board.updateScore(new Team("Mexico"), new Team("Poland"), 1, 0);

        board.startGame(new Team("Spain"), new Team("Brazil"));
        board.updateScore(new Team("Spain"), new Team("Brazil"), 2, 2);

        board.startGame(new Team("Germany"), new Team("France"));
        board.updateScore(new Team("Germany"), new Team("France"), 3, 1);

        System.out.println("=== Live ScoreBoard ===");
        board.getSortedMatches().forEach(match ->
                System.out.println(
                        match.getHomeTeam().name() + " "
                                + match.getHomeTeamScore() + " - "
                                + match.getAwayTeamScore() + " "
                                + match.getAwayTeam().name()
                )
        );
    }
}
