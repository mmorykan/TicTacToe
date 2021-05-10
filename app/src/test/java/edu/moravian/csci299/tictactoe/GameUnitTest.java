package edu.moravian.csci299.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Game. Since Game relies heavily on Board, this may identify issues with Board as
 * well. It is recommended to get the Board class passing all tests before worrying about the ones
 * here.
 */
public class GameUnitTest {
    /**
     * Tests all of the methods of Game at once by playing several games using a scriptable AI.
     */
    @Test
    public void game() {
        Game game = new Game();
        assertFalse(game.hasStarted());
        ScriptableAI ai = new ScriptableAI();
        game.setAI(ai);
        int ai_wins = 0;

        // Play several rounds won either by the player or the AI
        for (int i = 0; i < 8; i++) {
            ai.set(0, 0);
            game.startNewRound();
            assertTrue(game.hasStarted());
            if (game.getPlayerPiece() == 'O') {
                // Player is going first (and wins)
                assertTrue(game.playPiece(1, 1));
                ai.set(1, 0);
                assertTrue(game.playPiece(2, 0));
                ai.set(1, 0); // illegal move by AI, but player will win before that
                assertTrue(game.playPiece(0, 2));
                assertTrue(game.hasPlayerWon());
                assertFalse(game.hasAIWon());
            } else {
                // AI went first (and wins)
                assertEquals('O', game.getBoard().getPiece(0, 0));
                ai.set(1, 0);
                assertTrue(game.playPiece(1, 1));
                ai.set(2, 0);
                assertFalse(game.playPiece(1, 1)); // illegal move by player
                assertTrue(game.playPiece(2, 1));
                assertFalse(game.hasPlayerWon());
                assertTrue(game.hasAIWon());
                ai_wins++;
            }
            assertFalse(game.hasTied());
            assertEquals(i-ai_wins+1, game.getPlayerWins());
            assertEquals(ai_wins, game.getAIWins());
            assertEquals(0, game.getTies());
        }

        // Play several rounds that end in ties
        for (int i = 0; i < 4; i++) {
            ai.set(0, 0);
            game.startNewRound();
            assertTrue(game.hasStarted());
            if (game.getPlayerPiece() == 'O') {
                // Player is going first (ties)
                assertTrue(game.playPiece(1, 1));
                ai.set(2, 1);
                assertTrue(game.playPiece(0, 1));
                ai.set(1, 2);
                assertTrue(game.playPiece(1, 0));
                ai.set(2, 0);
                assertTrue(game.playPiece(0, 2));
                assertTrue(game.playPiece(2, 2));
            } else {
                // AI went first (ties)
                assertEquals('O', game.getBoard().getPiece(0, 0));
                ai.set(1, 0);
                assertTrue(game.playPiece(1, 1));
                ai.set(0, 2);
                assertTrue(game.playPiece(2, 0));
                ai.set(2, 1);
                assertTrue(game.playPiece(0, 1));
                ai.set(2, 2);
                assertTrue(game.playPiece(1, 2));
            }
            assertFalse(game.hasPlayerWon());
            assertFalse(game.hasAIWon());
            assertTrue(game.hasTied());
            assertEquals(8-ai_wins, game.getPlayerWins());
            assertEquals(ai_wins, game.getAIWins());
            assertEquals(i+1, game.getTies());
        }

    }

    /**
     * An AI that makes moves that it is told to be used for testing.
     */
    private static class ScriptableAI extends AI {
        public int row_next = 0, col_next = 0;
        public void set(int r, int c) { row_next = r; col_next = c; }

        @Override
        public void play(Board board, char piece) {
            assertTrue(board.playPiece(row_next, col_next, piece));
        }
    }
}
