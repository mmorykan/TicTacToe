package edu.moravian.csci299.tictactoe;

import java.util.Random;

/**
 * An AI for playing Tic-Tac-Toe. This is the abstract base class and doesn't implement a specific
 * strategy but provides the abstract play() method which is implemented by subclasses for their
 * individual strategies along with several other methods that are useful for the subclasses to use
 * when implementing their strategies.
 */
public abstract class AI {
    /** One, and only one, random variable for generating values. */
    private static final Random random = new Random();

    /**
     * Asks the AI to play the given piece onto the given board. The actual implementation of this
     * method is left to the sub-classes based on their individual strategy.
     * @param board the board to play on
     * @param piece the piece to play (either 'X' or 'O')
     */
    public abstract void play(Board board, char piece);

    /**
     * Play the piece randomly onto the board. It will continually try to place the piece until it
     * succeeds.
     * @param board the board to play on
     * @param piece the piece to play (either 'X' or 'O')
     */
    protected void playRandomMove(Board board, char piece) {
        int r = random.nextInt(3);
        int c = random.nextInt(3);
        int count = 0;
        while (!board.playPiece(r, c, piece)) {
            r = random.nextInt(3);
            c = random.nextInt(3);
            if (count++ > 10000) { throw new RuntimeException("playRandomMove() unable to succeed"); }
        }
    }

    /**
     * Finds an empty location on the board where placing the given piece would win the game. These
     * are locations where the piece is seen to be twice in a row, column, or diagonal with the
     * third space being empty.
     *
     * This may be used to either find a place where the AI can win or where the AI can block the
     * other player.
     *
     * @param board the board to be played on
     * @param piece the piece to play (either 'X' or 'O') that would cause the win
     * @return a 2-element list with the row and column of the empty location to get the win, or
     * null if no immediate wins are found
     */
    protected int[] findWin(Board board, char piece) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (isWinningLocation(board, r, c, piece)) {
                    return new int[] {r, c};
                }
            }
        }
        return null;
    }

    /**
     * This checks if the given location is empty, and one of the following is true:
     *  - the other two pieces in the row are equal to the given piece
     *  - the other two pieces in the column are equal to the given piece
     *  - the piece is along a diagonal and the other two pieces in the diagonal are equal to the
     *    given piece
     *
     * @param board the board to be played on
     * @param r the row which would receive the piece
     * @param c the column which would receive the piece
     * @param piece the piece to play (either 'X' or 'O') that would cause the win
     * @return true if placing the given piece at the given location is allowed and doing so would
     * win the game for that player
     */
    protected boolean isWinningLocation(Board board, int r, int c, char piece) {
        return board.isLocationEmpty(r, c) && ( // destination must be empty
                // check the row
                (       (r == 0 || board.getPiece(0, c) == piece) &&
                        (r == 1 || board.getPiece(1, c) == piece) &&
                        (r == 2 || board.getPiece(2, c) == piece)) ||
                // check the column
                (       (c == 0 || board.getPiece(r, 0) == piece) &&
                        (c == 1 || board.getPiece(r, 1) == piece) &&
                        (c == 2 || board.getPiece(r, 2) == piece)) ||
                // check the forward diagonal
                (r == c &&
                        (r == 0 || board.getPiece(0, 0) == piece) &&
                        (r == 1 || board.getPiece(1, 1) == piece) &&
                        (r == 2 || board.getPiece(2, 2) == piece)) ||
                // check the reverse diagonal
                (r == 2 - c &&
                        (r == 0 || board.getPiece(0, 2) == piece) &&
                        (r == 1 || board.getPiece(1, 1) == piece) &&
                        (r == 2 || board.getPiece(2, 0) == piece))
        );
    }

    /**
     * Gets the opposite piece, thus if given 'X' return 'O' and vice-versa.
     * @param piece either 'X' or 'O'
     * @return the opposite: 'O' or 'X'
     */
    protected char oppositePiece(char piece) {
        return piece == 'X' ? 'O' : 'X';
    }
}
