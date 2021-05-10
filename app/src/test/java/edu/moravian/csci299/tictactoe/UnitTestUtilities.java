package edu.moravian.csci299.tictactoe;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * A set of utilities to make writing the unit tests for Tic-Tac-Toe much easier.
 */
public class UnitTestUtilities {
    /**
     * Assert that the board has the given counts for Xs and Os (and spaces).
     * @param board the board to check
     * @param x the expected number of Xs
     * @param o the expected number of Os
     */
    public static void assertPieceCounts(Board board, int x, int o) {
        assertEquals(x, board.countPieces('X'));
        assertEquals(o, board.countPieces('O'));
        assertEquals(9-x-o, board.countPieces(' '));
    }

    /**
     * Assert if the board is in a game-over state and won has won or tied depending on the
     * parameters.
     * @param board the board to check, to be converted with createBoardFromString()
     * @param xWon if X has won on the given board
     * @param oWon if O has won on the given board
     * @param tied if there is a tie
     */
    public static void assertGameOver(String board, boolean xWon, boolean oWon, boolean tied) {
        assertGameOver(createBoardFromString(board), xWon, oWon, tied);
    }

    /**
     * Assert if the board is in a game-over state and won has won or tied depending on the
     * parameters.
     * @param board the board to check
     * @param xWon if X has won on the given board
     * @param oWon if O has won on the given board
     * @param tied if there is a tie
     */
    public static void assertGameOver(Board board, boolean xWon, boolean oWon, boolean tied) {
        assertEquals(xWon, board.hasWon('X'));
        assertEquals(oWon, board.hasWon('O'));
        assertEquals(tied, board.hasTied());
        assertEquals(xWon | oWon | tied, board.isGameOver());
    }

    /**
     * Assert that an AI plays on the board with the given piece in the expected location.
     * @param board the board that is being played on, to be converted with createBoardFromString()
     * @param ai the AI making the play
     * @param piece the piece being played
     * @param r the expected row the piece will be played
     * @param c the expected column the piece will be played
     */
    public static void assertAIPlay(String board, AI ai, char piece, int r, int c) {
        assertAIPlay(createBoardFromString(board), ai, piece, r, c);
    }

    /**
     * Assert that an AI plays on the board with the given piece in the expected location.
     * @param board the board that is being played on
     * @param ai the AI making the play
     * @param piece the piece being played
     * @param r the expected row the piece will be played
     * @param c the expected column the piece will be played
     */
    public static void assertAIPlay(Board board, AI ai, char piece, int r, int c) {
        int x = board.countPieces('X'), o = board.countPieces('O');
        ai.play(board, piece);
        if (piece == 'X') { x++; } else { o++; }
        assertPieceCounts(board, x, o);
        assertEquals(piece, board.getPiece(r, c));
    }

    /**
     * Assert that the AI plays randomly on the given board with the given piece. The board will be
     * copied many times and it will be checked that the AI will end up playing on any available
     * space on the original board.
     * @param board the board that is being played on, to be converted with createBoardFromString()
     * @param ai the AI making the play
     * @param piece the piece being played
     * @return one of the board with the piece randomly played on it
     */
    public static Board assertAIPlaysRandomly(String board, AI ai, char piece) {
        return assertAIPlaysRandomly(createBoardFromString(board), ai, piece);
    }

    /**
     * Assert that the AI plays randomly on the given board with the given piece. The board will be
     * copied many times and it will be checked that the AI will end up playing on any available
     * space on the original board.
     * @param board the board that is being played on
     * @param ai the AI making the play
     * @param piece the piece being played
     * @return one of the board with the piece randomly played on it
     */
    public static Board assertAIPlaysRandomly(Board board, AI ai, char piece) {
        int x = board.countPieces('X'), o = board.countPieces('O'), spaces = 9 - x - o;
        if (piece == 'X') { x++; } else { o++; }
        Board original = board;
        HashSet<Board> boards = new HashSet<Board>();
        for (int i = 0; i < 50; i++) {
            board = new Board(original);
            ai.play(board, piece);
            assertPieceCounts(board, x, o);
            boards.add(board);
        }
        assertEquals(spaces, boards.size());
        return board;
    }

    /**
     * Converts a String representation of a board to an actual board. The String uses X and O for
     * pieces, spaces for empty places, and | to indicate the breaks between rows. The String can
     * be missing trailing empty spaces. The board should not represent a game that is already over.
     * @param board the string representation of the board
     * @return the actual Board object
     */
    public static Board createBoardFromString(String board) {
        Board b = new Board();
        int r = 0, c = 0;
        for (char piece : board.toCharArray()) {
            if (piece == '|') {
                r++; c = 0;
            } else {
                if (piece != ' ') { b.playPiece(r, c, piece); }
                c++;
            }
        }
        return b;
    }
}
