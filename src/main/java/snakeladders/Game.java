package snakeladders;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final Dice dice;
    private final List<Player> players;

    private boolean isGameOver;
    private int currentPlayerIndex;

    public Game(Board board, Dice dice, List<Player> players) {
        this.board = board;
        this.dice = dice;
        this.players = new ArrayList<>(players);
        this.isGameOver = false;
        this.currentPlayerIndex = 0;
    }

    public void takeTurn() {
        if(isGameOver) {
            throw new IllegalStateException("Game is already over");
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        int newPosition = currentPlayer.getPosition() + dice.roll();
        int roll = dice.roll();
        System.out.println(currentPlayer.getName() + " rolled " + roll);

        if(newPosition >= board.getSize()) {
            System.out.println(currentPlayer.getName() + " overshoots — stays at " + currentPlayer.getPosition());
        } else {
            int destination = board.getDestination(newPosition);
            if (destination < newPosition) {
                System.out.println("Snake! " + currentPlayer.getName() + " slides from " + newPosition + " to " + destination);
            }
            else if (destination > newPosition) {
                System.out.println("Ladder! " + currentPlayer.getName() + " climbs from " + newPosition + " to " + destination);
            }
            currentPlayer.setPosition(destination);
            System.out.println(currentPlayer.getName() + " is now at position " + destination);
            if(currentPlayer.hasWon()) {
                System.out.println(currentPlayer.getName() + " wins!");
                isGameOver = true;
                return;
            }
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
