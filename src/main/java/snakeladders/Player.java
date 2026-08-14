package snakeladders;

public class Player {
    private final String name;
    private int position;

    public Player(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player cant have empty name");
        }
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean hasWon(int position) {
        return position == Constants.SIZE;
    }}
