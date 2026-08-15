package snakeladders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> snakes = new HashMap<>();
        snakes.put(17, 7);
        snakes.put(32, 14);
        snakes.put(47, 26);
        snakes.put(58, 39);
        snakes.put(64, 44);
        snakes.put(74, 53);
        snakes.put(83, 61);
        snakes.put(93, 48);
        snakes.put(99, 65);

        Map<Integer, Integer> ladders = new HashMap<>();
        ladders.put(4, 25);
        ladders.put(9, 31);
        ladders.put(20, 42);
        ladders.put(28, 55);
        ladders.put(36, 57);
        ladders.put(51, 72);
        ladders.put(62, 80);
        ladders.put(71, 91);
        ladders.put(79, 98);

        Board board = new Board(snakes, ladders);
        Dice dice   = new Dice();

        List<Player> players = List.of(
                new Player("Ayush"),
                new Player("Vrinda")
        );

        Game game = new Game(board, dice, players);

        snakeladders.ui.GameApp.launch(board, snakes, ladders, players, game);
    }
}
