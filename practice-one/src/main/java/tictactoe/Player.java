package tictactoe;

public class Player {
    private final String name;
    private final Mark mark;

    public Player(String name, Mark mark) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name of player can not be empty");
        }
        if(mark.equals(Mark.EMPTY)) {
            throw new IllegalArgumentException("Player symbol can not be empty");
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
