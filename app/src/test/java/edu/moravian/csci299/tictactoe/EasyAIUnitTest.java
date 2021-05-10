package edu.moravian.csci299.tictactoe;

import org.junit.Test;

import static edu.moravian.csci299.tictactoe.UnitTestUtilities.*;

/**
 * Local unit tests for easy AI. Depends on the Board class so make sure that is working first.
 */
public class EasyAIUnitTest {
    @Test
    public void play() {
        EasyAI ai = new EasyAI();
        char piece = 'O';
        Board board = assertAIPlaysRandomly(new Board(), ai, piece);
        while (!board.isGameOver()) {
            piece = piece == 'O' ? 'X' : 'O'; // switch player
            board = assertAIPlaysRandomly(board, ai, piece);
        }
    }
}
