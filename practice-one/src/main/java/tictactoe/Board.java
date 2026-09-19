package tictactoe;

import java.util.Arrays;

public class Board {
    private final Mark[][] board = new Mark[3][3];
    private int countOfPlacedMarkers;

    public Board() {
        for(int i = 0; i < 3; i++) {
            Arrays.fill(board[i], Mark.EMPTY);
        }
        countOfPlacedMarkers = 0;
    }

    public void printBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean placeMarker(int i, int j, Mark mark) {
        if(i >= 3 || j >= 3 || i < 0 || j < 0) {
            throw new IllegalArgumentException("You cant place marker outside the board");
        }
        if(!board[i][j].equals(Mark.EMPTY)) {
            return false;
        }
        board[i][j] = mark;
        countOfPlacedMarkers++;
        return true;
    }

    public Mark getWinner() {
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && !board[i][0].equals(Mark.EMPTY)) {
                return board[i][0];
            }
        }

        for(int i = 0; i < 3; i++) {
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && !board[0][i].equals(Mark.EMPTY)) {
                return board[0][i];
            }
        }

        if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && !board[0][0].equals(Mark.EMPTY)) {
            return board[0][0];
        }

        if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && !board[0][2].equals(Mark.EMPTY)) {
            return board[0][2];
        }

        return Mark.EMPTY;
    }

    public boolean isBoardFull() {
        return countOfPlacedMarkers == 9;
    }
}
