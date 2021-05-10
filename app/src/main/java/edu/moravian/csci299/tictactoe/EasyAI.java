package edu.moravian.csci299.tictactoe;

/**
 * The easy AI always plays pieces randomly.
 */
public class EasyAI extends AI {
    /**
     * The easy AI always plays pieces randomly.
     * @param board the board to play on
     * @param piece the piece to play (either 'X' or 'O')
     */
    @Override
    public void play(Board board, char piece) {
        playRandomMove(board, piece);
    }
}
