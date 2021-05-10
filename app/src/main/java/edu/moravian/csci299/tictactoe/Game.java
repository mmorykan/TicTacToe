package edu.moravian.csci299.tictactoe;

import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.Objects;

/**
 * The model of the entire game. The wraps up several rounds of the game into a single model.
 */
public class Game extends ViewModel {
    /** The AI being used to play against the human */
    private AI ai;
    /** The current board for the game, replaced for each new round */
    private Board board;
    /** The pieces used by the human and AI players */
    private final char[] pieces = {'?', '?'};
    /** The number of wins by the human ndn AI players */
    private final int[] wins = {0, 0};
    /** The number of ties between the human and AI players */
    private int ties = 0;

    /**
     * @return true if the game has been started at all (i.e. startNewRound() has ever been called)
     */
    public boolean hasStarted() {
        return board != null;
    }

    /**
     * Set the AI for this game. This must be called before any other method is called. It should
     * only be called once.
     * @param ai the AI to use for this game.
     */
    public void setAI(AI ai) {
        this.ai = ai;
    }

    /**
     * Start a new round of the game. This is only allowed right after the game is created or the
     * previous round has ended. At any other point this will raise an exception.
     *
     * This creates a new board for the game and randomly assigns the human and AI to the X and O
     * pieces. If the AI is selected to go first (it is the 'O' piece), it takes its first turn.
     */
    public void startNewRound() {
        if (board != null && !board.isGameOver()) {
            throw new IllegalStateException("wrong time to start a round");
        }
        board = new Board();
        if (Math.random() < 0.5) {
            pieces[0] = 'X';
            pieces[1] = 'O';
            ai.play(board, 'O'); // AI (piece O) goes first
        } else {
            pieces[0] = 'O';
            pieces[1] = 'X';
        }
    }

    /**
     * Gets the human player's piece, either 'O' or 'X'. Must be called after startNewRound().
     * @return either 'O' or 'X' for the human player's piece
     */
    public char getPlayerPiece() { return pieces[0]; }

    /**
     * @return the current game board, which is null before the first round
     */
    public Board getBoard() { return board; }

    /**
     * Has the human play on the board in the given row and column. This returns false if the move
     * was illegal. If the player does not end the game then the AI also takes its turn. If this
     * method is called before the first round has started or when the game is over it raises an
     * exception.
     *
     * @param r the row to play in, from 0 to 2
     * @param c the column to play in, from 0 to 2
     * @return true the player's piece was successfully played, false otherwise
     */
    public boolean playPiece(int r, int c) {
        if (board == null || board.isGameOver()) {
            throw new IllegalStateException("wrong time to play a piece");
        }
        if (!board.playPiece(r, c, pieces[0])) { return false; }
        if (!checkGameOver()) {
            ai.play(board, pieces[1]);
            checkGameOver();
        }
        return true;
    }

    /**
     * @return true if the game is over and the human player has won
     */
    public boolean hasPlayerWon() {
        return board.hasWon(pieces[0]);
    }

    /**
     * @return true if the game is over and the AI player has won
     */
    public boolean hasAIWon() {
        return board.hasWon(pieces[1]);
    }

    /**
     * @return true if the game is over and it is a tie
     */
    public boolean hasTied() {
        return board.hasTied();
    }

    /**
     * Checks if the game has ended and updates the counters appropriately.
     * @return true if the game has ended, false otherwise
     */
    private boolean checkGameOver() {
        if (!board.isGameOver()) { return false; }
        if (board.hasTied()) { ties++; }
        else { wins[hasPlayerWon() ? 0 : 1]++; }
        return true;
    }

    /**
     * @return the number of rounds the human player has won
     */
    public int getPlayerWins() { return wins[0]; }

    /**
     * @return the number of rounds the AI player has won
     */
    public int getAIWins() { return wins[1]; }

    /**
     * @return the number of rounds the human and AI have tied
     */
    public int getTies() { return ties; }


    ////////// General Object Methods //////////

    @Override
    public String toString() {
        return "Game{" +
                "ai=" + ai +
                ", board=" + board +
                ", pieces=" + Arrays.toString(pieces) +
                ", wins=" + Arrays.toString(wins) +
                ", ties=" + ties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return ties == game.ties &&
                Objects.equals(ai, game.ai) &&
                Objects.equals(board, game.board) &&
                Arrays.equals(pieces, game.pieces) &&
                Arrays.equals(wins, game.wins);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(ai, board, ties);
        result = 31 * result + Arrays.hashCode(pieces);
        result = 31 * result + Arrays.hashCode(wins);
        return result;
    }
}
