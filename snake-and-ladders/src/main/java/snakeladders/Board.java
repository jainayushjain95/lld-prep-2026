package snakeladders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final int SIZE = Constants.SIZE;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;


    public Board(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        validate(snakes, ladders);
        this.snakes = Collections.unmodifiableMap(new HashMap<>(snakes));
        this.ladders = Collections.unmodifiableMap(new HashMap<>(ladders));
    }

    private void validate(Map<Integer, Integer> snakes, Map<Integer, Integer> ladders) {
        if(snakes == null || ladders == null) {
            throw new IllegalArgumentException("Snake and ladder maps can not be empty");
        }
        for(Integer head : snakes.keySet()) {
            int tail = snakes.get(head);
            if(tail >= head) {
                throw new IllegalArgumentException("Snake head must be above tail");
            }
            if(head < 1 || head >= SIZE || tail < 1) {
                throw new IllegalArgumentException("Snake position out of bounds");
            }
        }

        for(Integer bottom : ladders.keySet()) {
            int top = ladders.get(bottom);
            if(top <= bottom) {
                throw new IllegalArgumentException("Ladder top be above bottom");
            }
            if(top < 1 || top > SIZE || bottom < 1) {
                throw new IllegalArgumentException("Ladder position out of bounds");
            }
        }
    }

    public int getDestination(int position) {
        if(snakes.containsKey(position)) {
            return snakes.get(position);
        }
        if(ladders.containsKey(position)) {
            return ladders.get(position);
        }
        return position;
    }

    public int getSize() { return SIZE; }
}
