package snakeladders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> snakes = new HashMap<>();
        snakes.put(99, 7);
        snakes.put(70, 35);
        snakes.put(52, 21);
        snakes.put(25, 3);

        Map<Integer, Integer> ladders = new HashMap<>();
        ladders.put(4, 56);
        ladders.put(13, 46);
        ladders.put(33, 49);
        ladders.put(42, 63);

        Board board = new Board(snakes, ladders);
        Dice dice   = new Dice();

        List<Player> players = List.of(
                new Player("Ayush"),
                new Player("Vrinda")
        );

        Game game = new Game(board, dice, players);

        while (!game.isGameOver()) {
            game.takeTurn();
        }
    }
}
