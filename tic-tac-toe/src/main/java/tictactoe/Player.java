package tictactoe;

public class Player {
    private final String name;
    private final Mark mark;


    public Player(String name, Mark mark) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player cant have empty name");
        }
        if(mark == Mark.EMPTY) {
            throw new IllegalArgumentException("Player cant have empty symbol, either O or X");
        }
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public Mark getMark() {
        return mark;
    }
}
