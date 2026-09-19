package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameEngine {
    private final Board board;
    private final List<Player> players;
    private int currentPlayerIndex;
    private boolean isGameOver;

    public GameEngine(Board board, List<Player> players) {
        if (board == null || players == null)
            throw new IllegalArgumentException("Board and players cannot be null");
        if (players.size() != 2)
            throw new IllegalArgumentException("Tic-Tac-Toe requires exactly 2 players");
        if(players.get(0).getMark().equals(players.get(1).getMark()) || players.get(0).getName().equals(players.get(1).getName())) {
            throw new IllegalArgumentException("Players cant have same name or symbol");
        }
        this.board = board;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
        this.isGameOver = false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void takeTurn(int i, int j) {
        if(isGameOver()) {
            throw new IllegalStateException("game is already over");
        }
        boolean placed = board.placeMarker(i, j, players.get(currentPlayerIndex).getMark());
        if(!placed) {
            return;
        }

        if(!board.getWinner().equals(Mark.EMPTY)) {
            System.out.println("Player: " + players.get(currentPlayerIndex).getName() + " wins!");
            isGameOver = true;
            return;
        }

        if(board.isBoardFull()) {
            System.out.println("Game Draw, play again!");
            isGameOver = true;
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }


    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while(!isGameOver()) {
            board.printBoard();
            Player current = players.get(currentPlayerIndex);
            System.out.println(current.getName() + " (" + current.getMark() + ") — enter row and col (0-2):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            takeTurn(row, col);
        }
    }

}
