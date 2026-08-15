package tictactoe;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();

        List<Player> players = List.of(
                new Player("Ayush", Mark.O),
                new Player("Vrinda", Mark.X)
        );

        Game game = new Game(board, players);
        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            board.printBoard();
            Player current = game.getCurrentPlayer();
            System.out.println(current.getName() + " (" + current.getMark() + ") — enter row and col (0-2):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            game.takeTurn(row, col);
        }

    }
}
