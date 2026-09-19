package tictactoe;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        List<Player> players = List.of(
                new Player("Ayush", Mark.O),
                new Player("Vrinda", Mark.X)
        );
        GameEngine gameEngine = new GameEngine(board, players);
        gameEngine.playGame();
    }
}
