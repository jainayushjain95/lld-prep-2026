package tictactoe;

import java.util.Arrays;

public class Board {

    private final Mark[][] board = new Mark[3][3];
    private int markersCount;

    public Board() {
        markersCount = 0;
        for(int i = 0; i < 3; i++) {
            Arrays.fill(board[i], Mark.EMPTY);
        }
    }


    public boolean placeMarker(int i, int j, Mark mark) {
        if(i < 0 || i >= 3 || j < 0 || j >= 3) {
            throw new IllegalArgumentException("You cant place marker outside the board");
        }
        if(board[i][j] != Mark.EMPTY) {
            return false;
        }
        board[i][j] = mark;
        markersCount++;
        return true;
    }

    public boolean isBoardFull() {
        return markersCount == 9;
    }

    public void printBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    public Mark checkWinner() {
        //Row wise
        for(int i = 0; i < 3; i++) {
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        //Column wise
        for(int i = 0; i < 3; i++) {
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        //Left to right diagonal wise
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }

        //Right to left diagonal wise
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        return Mark.EMPTY;
    }
}
