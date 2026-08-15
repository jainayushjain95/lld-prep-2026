package tictactoe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final Board board;
    private final List<Player> players;
    private int currentPlayerIndex;

    public boolean isGameOver() {
        return isGameOver;
    }

    private boolean isGameOver;

    public Game(Board board, List<Player> players) {
        if (board == null || players == null)
            throw new IllegalArgumentException("Board and players cannot be null");
        if (players.size() != 2)
            throw new IllegalArgumentException("Tic-Tac-Toe requires exactly 2 players");

        Set<String> set = new HashSet<>();
        for(Player player : players) {
            if(set.contains(player.getMark().name())) {
                throw new IllegalArgumentException("No two player can have same mark or symbol");
            }
            set.add(player.getMark().name());
        }

        this.board = board;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    public void takeTurn(int row, int col) {
        if (isGameOver) {
            throw new IllegalStateException("Game is already over");
        }

        boolean placed = board.placeMarker(row, col, players.get(currentPlayerIndex).getMark());

        if (!placed) {
            return;
        }

        if(board.checkWinner() != Mark.EMPTY) {
            System.out.println(players.get(currentPlayerIndex).getName() + " wins!");
            isGameOver = true;
            return;
        }
        if (board.isBoardFull()) {
            System.out.println("Draw!");
            isGameOver = true;
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}
