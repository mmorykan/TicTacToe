package edu.moravian.csci299.tictactoe;

import org.junit.Test;

import static edu.moravian.csci299.tictactoe.UnitTestUtilities.*;

/**
 * Local unit tests for medium AI. They are broken down into the different situations that the AI
 * looks for. Depends on the Board class so make sure that is working first.
 */
public class MediumAIUnitTest {
    /**
     * Check that the medium AI plays to win (even if they could block).
     */
    @Test
    public void play_win() {
        MediumAI ai = new MediumAI();

        //// As first player O ////
        assertAIPlay("OO |XX |   ", ai, 'O', 0, 2); // row  (also a block to be performed that should be ignored)
        assertAIPlay("XOX| O |   ", ai, 'O', 2, 1); // column
        assertAIPlay("OXX| O |   ", ai, 'O', 2, 2); // diagonal

        //// As second player X ////
        assertAIPlay("XX | OO|  O", ai, 'X', 0, 2); // row
        assertAIPlay("OOX| O |  X", ai, 'X', 1, 2); // column
        assertAIPlay("OOX|O  |X  ", ai, 'X', 1, 1); // diagonal
    }

    /**
     * Check that the medium AI plays to block the other player if they can't win.
     */
    @Test
    public void play_block() {
        MediumAI ai = new MediumAI();

        //// As first player O ////
        assertAIPlay("XX |O  |O  ", ai, 'O', 0, 2); // row
        assertAIPlay("OOX|  X|   ", ai, 'O', 2, 2); // column
        assertAIPlay("OOX| X |   ", ai, 'O', 2, 0); // diagonal

        //// As second player X ////
        assertAIPlay("OO |OXX|   ", ai, 'X', 0, 2); // row
        assertAIPlay("OXX|O  | O ", ai, 'X', 2, 0); // column
        assertAIPlay("O  |OOX|X  ", ai, 'X', 2, 2); // diagonal
    }

    /**
     * Check that the medium AI plays randomly when there is no winning or blocking move.
     */
    @Test
    public void play_random() {
        MediumAI ai = new MediumAI();
        // Check on an empty board
        assertAIPlaysRandomly(new Board(), ai, 'O');

        // Check on an board with 1 piece
        assertAIPlaysRandomly("O  |   |   ", ai, 'X');

        // Check on an board with 2 pieces
        assertAIPlaysRandomly("OX |   |   ", ai, 'O');

        // Check on an board with 3 pieces
        assertAIPlaysRandomly("OXO|   |   ", ai, 'X');

        // Check on an board with 4 pieces
        assertAIPlaysRandomly("OXO|X  |   ", ai, 'O');

        // Check on an board with 8 pieces
        assertAIPlaysRandomly("OXO| OX|XOX", ai, 'O');
    }
}