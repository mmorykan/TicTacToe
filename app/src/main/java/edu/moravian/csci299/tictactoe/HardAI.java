package edu.moravian.csci299.tictactoe;

/**
 * The hard AI tries the following plays in this order:
 *  - that will win for the AI
 *  - that will block the opponent from winning
 *  - in the center
 *  - in a corner opposite the opponent
 *  - in any corner
 *  - in any (edge) spot
 * See https://en.wikipedia.org/wiki/Tic-tac-toe#Strategy (note: the forking checks are not actually
 * necessary as they are covered by the other steps)
 */
public class HardAI extends AI {
    /** Corner locations on the board */
    private final static int[][] corners = { {0, 0}, {0, 2}, {2, 2}, {2, 0} };
    /** Edge locations on the board (not including corners) */
    private final static int[][] edges = { {0, 1}, {1, 2}, {2, 1}, {1, 0} };

    /**
     * Tries to find a location to win, then a location to block, the center location, a corner
     * location opposite the opponent, any empty corner, any remaining (edge) location. To make the
     * first moves easier, they are hard-coded (if first player then top-left corner, if second
     * player then center or top-left corner).
     *
     * See https://en.wikipedia.org/wiki/Tic-tac-toe#Strategy (note: the forking checks are not
     * actually necessary as they are covered by the other steps)
     *
     * @param board the board to play on
     * @param piece the piece to play (either 'X' or 'O')
     */
    @Override
    public void play(Board board, char piece) {
        char opponent = oppositePiece(piece);

        // First move is fixed
        if (piece == 'O' && board.isLocationEmpty(0, 0)) { // AI is first player
            board.playPiece(0, 0, piece); // picks upper-left first
            return;
        } else if (piece == 'X' && board.countPieces('O') == 1) { // AI is second player and other player has only played one piece
            if (board.isLocationEmpty(1, 1)) { board.playPiece(1, 1, piece); } // picks center first
            else { board.playPiece(0, 0, piece); } // picks top-left first if center is already taken
            return;
        }

        // Find a win or block a win
        int[] location = findWin(board, piece);
        if (location == null) { location = findWin(board, opponent);  }
        if (location != null) { board.playPiece(location[0], location[1], piece); return; }

        // Play in the center (not needed since it is covered by the opening moves)
        if (board.isLocationEmpty(1, 1)) { board.playPiece(1, 1, piece); return; }

        // Play in a corner opposite of the opponent
        for (int[] loc : corners) {
            if (board.getPiece(2-loc[0], 2-loc[1]) == opponent && board.isLocationEmpty(loc[0], loc[1])) {
                board.playPiece(loc[0], loc[1], piece);
                return;
            }
        }

        // Play in any corner
        for (int[] loc : corners) {
            if (board.isLocationEmpty(loc[0], loc[1])) { board.playPiece(loc[0], loc[1], piece); return; }
        }

        // Play in any edge (we have already eliminated center and corners, only places left)
        for (int[] loc : edges) {
            if (board.isLocationEmpty(loc[0], loc[1])) { board.playPiece(loc[0], loc[1], piece); return; }
        }
    }

}
