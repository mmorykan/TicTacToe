package edu.moravian.csci299.tictactoe;

import org.junit.Test;

import static edu.moravian.csci299.tictactoe.UnitTestUtilities.*;

/**
 * Local unit tests for hard AI. They are broken down into the different situations that the AI
 * looks for. Depends on the Board class so make sure that is working first.
 */
public class HardAIUnitTest {
    /**
     * Check that the hard AI plays starts by playing the in the upper-left or middle spaces
     * depending on if it is first or second player (and where the first player played).
     */
    @Test
    public void play_firstMove() {
        HardAI ai = new HardAI();
        Board board = new Board();
        assertAIPlay(board, ai, 'O', 0, 0);
        assertAIPlay(board, ai, 'X', 1, 1);
        assertAIPlay("   | O |   ", ai, 'X', 0, 0);
    }

    /**
     * Check that the hard AI plays to win (even if they could block).
     */
    @Test
    public void play_win() {
        HardAI ai = new HardAI();

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
     * Check that the hard AI plays to block the other player if they can't win.
     */
    @Test
    public void play_block() {
        HardAI ai = new HardAI();

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
     * Check that the hard AI plays to the center when not winning, forking, or blocking.
     */
    @Test
    public void play_center() {
        HardAI ai = new HardAI();

        //// As first player O ////
        // Will never happen when playing second (accept as during opening move)
        assertAIPlay("O  |   |  X", ai, 'O', 1, 1);
        assertAIPlay("O  |   | X ", ai, 'O', 1, 1);
        assertAIPlay("O  |  X|   ", ai, 'O', 1, 1);
        assertAIPlay("O X|   |   ", ai, 'O', 1, 1);
    }

    /**
     * Check that the hard AI plays to a corner opposite the opponent.
     */
    @Test
    public void play_oppositeCorner() {
        HardAI ai = new HardAI();

        //// As first player O ////
        assertAIPlay("OX | O |XOX", ai, 'O', 0, 2);
        assertAIPlay("O X|XOO|  X", ai, 'O', 2, 0);

        //// As second player X ////
        assertAIPlay("  O|OX |   ", ai, 'X', 2, 0);
        assertAIPlay("  O|OXX|  O", ai, 'X', 0, 0);
        assertAIPlay("  O|OXX|XOO", ai, 'X', 0, 0);
        assertAIPlay(" O | X |O  ", ai, 'X', 0, 2);
        assertAIPlay("O  | X | O ", ai, 'X', 2, 2);
    }

    /**
     * Check that the hard AI plays to any corner eventually.
     */
    @Test
    public void play_anyCorner() {
        HardAI ai = new HardAI();

        //// As first player O ////
        assertAIPlay("O  | X |   ", ai, 'O', 0, 2);
        assertAIPlay("O  |XO |  X", ai, 'O', 0, 2);
        assertAIPlay("OX | O |  X", ai, 'O', 0, 2);
        assertAIPlay("OXO| X | OX", ai, 'O', 2, 0);

        //// As second player X ////
        assertAIPlay("   | XO| O ", ai, 'X', 0, 0);
        assertAIPlay("   |OXO|   ", ai, 'X', 0, 0);
        assertAIPlay("  O| X |O  ", ai, 'X', 0, 0);
        assertAIPlay("O  | XO| OX", ai, 'X', 0, 2);
    }

    /**
     * Check that the hard AI plays finally plays in any spot.
     */
    @Test
    public void play_any() {
        HardAI ai = new HardAI();

        //// As first player O ////
        assertAIPlay("O X|XOO|OXX", ai, 'O', 0, 1);
        assertAIPlay("OXO| OX|XOX", ai, 'O', 1, 0);
        assertAIPlay("OXO|XO |XOX", ai, 'O', 1, 2);
        assertAIPlay("OXX|XOO|O X", ai, 'O', 2, 1);

        //// As second player X ////
        assertAIPlay("X O|OOX|X O", ai, 'X', 0, 1);
        assertAIPlay("XOX| O |OXO", ai, 'X', 1, 2);
    }
}
