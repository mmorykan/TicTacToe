package edu.moravian.csci299.tictactoe;

import androidx.annotation.NonNull;
import java.util.Arrays;

/**
 * A Tic-Tac-Toe game board. It is always 3x3 and contains the chars ' ' (space) for an empty spot,
 * 'X' and 'O' for player moves.
 */
public class Board implements Cloneable {
    /** The actual board of characters */
    private static final int BOARD_SIZE = 3;
    private final char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

    /**
     * Construct a new board that is filled in completely with spaces.
     */
    public Board() {
        for (char[] chars : this.board) Arrays.fill(chars, ' ');
    }

    /**
     * Construct a new board that is filled in with the same contents of the other board.
     */
    public Board(Board board) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = board.getPiece(i, j);
            }
        }
    }

    /**
     * Gets a piece on the board (either 'X' or 'O') or if it is empty (returning ' ').
     * @param r the row on the board to look up (0-2)
     * @param c the column on the board to look up (0-2)
     * @return one of 'X', 'O', or ' '
     */
    public char getPiece(int r, int c) {
        return this.board[r][c];
    }

    /**
     * Returns true if the location on the board is empty.
     * @param r the row on the board to look up (0-2)
     * @param c the column on the board to look up (0-2)
     * @return same as getPiece(r, c) == ' '
     */
    public boolean isLocationEmpty(int r, int c) {
        return this.getPiece(r, c) == ' ';
    }

    /**
     * Returns the number of pieces of the given type across the entire board.
     * @param piece the piece to check for, one of ' ', 'X', or 'O'
     * @return the number of those pieces on the board
     */
    public int countPieces(char piece) {
        int count = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (getPiece(i, j) == piece) count++;
            }
        }
        return count;
    }

    /**
     * Play a piece onto the board, checking to make sure that the space is empty first.
     * @param r the row where to place the piece (0-2)
     * @param c the column where to place the piece (0-2)
     * @param piece the piece to place, either 'X' or 'O'
     * @return true if the space was empty and the play was successful, false otherwise
     */
    public boolean playPiece(int r, int c, char piece) {
        boolean canPlay = this.getPiece(r, c) == ' ' && !this.isGameOver();
        if (canPlay) this.board[r][c] = piece;

        return canPlay;
    }

    /**
     * @param piece the piece to check for a win with
     * @return true if the player with the given piece has won, false otherwise
     */
    public boolean hasWon(char piece) {
        return this.rowOrColWinner(piece) || this.diagonalWinner(piece);
    }

    /**
     * Determine if there is a row or column of all X's or O's.
     * @param piece 'X' or 'O'.
     * @return Whether or not someone won.
     */
    private boolean rowOrColWinner(char piece) {
        int rowNum = 0, colNum = 0;

        for (int i = 0; i < BOARD_SIZE; i ++) {
            for (int j = 0; j < BOARD_SIZE; j ++) {
                if (this.getPiece(i, j) == piece) rowNum++;
                if (this.getPiece(j, i) == piece) colNum++;
            }
            if (rowNum == BOARD_SIZE || colNum == BOARD_SIZE) return true;
            rowNum = colNum = 0;
        }
        return false;
    }

    /**
     * Determine if there is a diagonal of all X's or O's.
     * @param piece 'X' or 'O'.
     * @return Whether or not there is a winner.
     */
    private boolean diagonalWinner(char piece) {
        int diagonalNum = 0, antiDiagonalNum = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (this.getPiece(i, i) == piece) diagonalNum++;
            if (this.getPiece(i, BOARD_SIZE - 1 - i) == piece) antiDiagonalNum++;
        }

        return diagonalNum == BOARD_SIZE || antiDiagonalNum == BOARD_SIZE;
    }

    /**
     * @return true if the board is full of pieces (no ' ' pieces on the board) and neither X or O
     * has won
     */
    public boolean hasTied() {
        return this.countPieces(' ') == 0 && !this.hasWon('X') && !this.hasWon('O');
    }

    /**
     * @return true if X or O has won or the game is tied
     */
    public boolean isGameOver() {
        return this.hasWon('X') || this.hasWon('O') || this.hasTied();
    }


    ////////// General Object Methods //////////

    @NonNull
    @Override
    protected Object clone() { return new Board(this); }

    @Override
    public String toString() { return "Board{board=" + Arrays.deepToString(board) + '}'; }

    @Override
    public boolean equals(Object o) {
        return this == o || (o != null && getClass() == o.getClass() && Arrays.deepEquals(board, ((Board)o).board));
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(board); }
}
