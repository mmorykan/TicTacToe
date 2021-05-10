package edu.moravian.csci299.tictactoe;

import org.junit.Test;

import static edu.moravian.csci299.tictactoe.UnitTestUtilities.*;
import static org.junit.Assert.*;

/**
 * Test all of the Board method. Many of these are inter-related so frequently these test methods
 * don't test a single method but instead test several at a time.
 */
public class BoardUnitTest {
    /**
     * Tests the basics of each of default constructor, isLocationEmpty(), getPiece(), and
     * playPiece(). All other tests will end up testing these as well.
     */
    @Test
    public void basics() {
        Board board = new Board();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(board.isLocationEmpty(r, c));
                assertEquals(' ', board.getPiece(r, c));
            }
        }
        assertTrue(board.playPiece(0, 0, 'O'));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 0 && c == 0) {
                    assertFalse(board.isLocationEmpty(r, c));
                    assertEquals('O', board.getPiece(r, c));
                } else {
                    assertTrue(board.isLocationEmpty(r, c));
                    assertEquals(' ', board.getPiece(r, c));
                }
            }
        }
        assertTrue(board.playPiece(0, 1, 'X'));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 0 && c == 0) {
                    assertFalse(board.isLocationEmpty(r, c));
                    assertEquals('O', board.getPiece(r, c));
                } else if (r == 0 && c == 1) {
                    assertFalse(board.isLocationEmpty(r, c));
                    assertEquals('X', board.getPiece(r, c));
                } else {
                    assertTrue(board.isLocationEmpty(r, c));
                    assertEquals(' ', board.getPiece(r, c));
                }
            }
        }
        assertTrue(board.playPiece(0, 2, 'O'));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 0) {
                    if (c == 0) {
                        assertFalse(board.isLocationEmpty(r, c));
                        assertEquals('O', board.getPiece(r, c));
                    } else if (c == 1) {
                        assertFalse(board.isLocationEmpty(r, c));
                        assertEquals('X', board.getPiece(r, c));
                    } else { // c == 2
                        assertFalse(board.isLocationEmpty(r, c));
                        assertEquals('O', board.getPiece(r, c));
                    }
                } else {
                    assertTrue(board.isLocationEmpty(r, c));
                    assertEquals(' ', board.getPiece(r, c));
                }
            }
        }
    }

    /**
     * Tests the copy constructor and makes sure it actually copies.
     */
    @Test
    public void copy() {
        Board board = new Board();
        board.playPiece(0, 0, 'O');
        board.playPiece(0, 1, 'X');
        board.playPiece(1, 1, 'O');
        board.playPiece(2, 2, 'X');
        board.playPiece(1, 0, 'O');
        Board board2 = new Board(board);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertEquals(board.getPiece(r, c), board2.getPiece(r, c));
            }
        }
        assertEquals(board, board2);

        // make sure it is a copy, not just a reference
        board2.playPiece(2, 1, 'X');
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 2 && c == 1) {
                    assertNotEquals(board.getPiece(r, c), board2.getPiece(r, c));
                } else {
                    assertEquals(board.getPiece(r, c), board2.getPiece(r, c));
                }
            }
        }
        assertNotEquals(board, board2);
    }

    /**
     * Tests countPieces().
     */
    @Test
    public void countPieces() {
        Board board = new Board();
        assertPieceCounts(board, 0, 0);
        assertTrue(board.playPiece(0, 0, 'O'));
        assertPieceCounts(board, 0, 1);
        assertTrue(board.playPiece(0, 1, 'X'));
        assertPieceCounts(board, 1, 1);
        assertTrue(board.playPiece(1, 1, 'O'));
        assertPieceCounts(board, 1, 2);
        assertTrue(board.playPiece(2, 2, 'X'));
        assertPieceCounts(board, 2, 2);
        assertTrue(board.playPiece(1, 0, 'O'));
        assertPieceCounts(board, 2, 3);
        assertTrue(board.playPiece(2, 0, 'X'));
        assertPieceCounts(board, 3, 3);
        assertTrue(board.playPiece(2, 1, 'O'));
        assertPieceCounts(board, 3, 4);
        assertTrue(board.playPiece(1, 2, 'X'));
        assertPieceCounts(board, 4, 4);
        assertTrue(board.playPiece(0, 2, 'O'));
        assertPieceCounts(board, 4, 5);
    }

    /**
     * Tests playPiece() including playing on top of another piece or after the end of the game.
     */
    @Test
    public void playPiece() {
        Board board = new Board();
        assertTrue(board.playPiece(0, 0, 'O'));
        assertFalse(board.playPiece(0, 0, 'O'));
        assertFalse(board.playPiece(0, 0, 'X'));
        assertTrue(board.playPiece(1, 1, 'X'));
        assertFalse(board.playPiece(0, 0, 'O'));
        assertFalse(board.playPiece(0, 0, 'X'));
        assertFalse(board.playPiece(1, 1, 'O'));
        assertFalse(board.playPiece(1, 1, 'X'));
        assertTrue(board.playPiece(0, 1, 'O'));
        assertFalse(board.playPiece(0, 1, 'X'));
        assertTrue(board.playPiece(1, 0, 'X'));
        assertFalse(board.playPiece(1, 0, 'X'));
        assertTrue(board.playPiece(0, 2, 'O')); // wins the game for O
        assertFalse(board.playPiece(0, 2, 'X'));
        assertFalse(board.playPiece(2, 2, 'X'));
        assertFalse(board.playPiece(1, 2, 'X'));
        assertFalse(board.playPiece(2, 1, 'X'));
        assertFalse(board.playPiece(2, 0, 'X'));

        board = createBoardFromString("OXX| O |  O"); // a different winning board
        assertFalse(board.playPiece(1, 0, 'X'));
        assertFalse(board.playPiece(1, 2, 'X'));
        assertFalse(board.playPiece(0, 2, 'X'));
    }

    /**
     * Tests hasWon(), hasTied(), and isGameOver() with various winning, tied, and incomplete,
     * boards.
     */
    @Test
    public void isGameOver() {
        // Look at an entire (quick) game
        Board board = new Board();
        assertGameOver(board, false, false, false);
        assertTrue(board.playPiece(0, 0, 'O'));
        assertGameOver(board, false, false, false);
        assertTrue(board.playPiece(0, 1, 'X'));
        assertGameOver(board, false, false, false);
        assertTrue(board.playPiece(1, 1, 'O'));
        assertGameOver(board, false, false, false);
        assertTrue(board.playPiece(1, 0, 'X'));
        assertGameOver(board, false, false, false);
        assertTrue(board.playPiece(2, 2, 'O'));
        assertGameOver(board, false, true, false);
        assertFalse(board.playPiece(2, 1, 'X'));

        // Look at a tie (and right before the tie)
        assertGameOver("OXO|OXO|XO ", false, false, false);
        assertGameOver("OXO|OXO|XOX", false, false, true);

        // Look at lots of ways to win
        assertGameOver("OOX|OX |X  ", true, false, false); // diagonal
        assertGameOver("OXX| OX|  O", false, true, false); // diagonal
        assertGameOver("OXX|O  |O  ", false, true, false); // column
        assertGameOver("OOX|O X|  X", true, false, false); // column
        assertGameOver("OO |XXX|O  ", true, false, false); // row
        assertGameOver("   |XXO|OOO", false, true, false); // row
    }
}