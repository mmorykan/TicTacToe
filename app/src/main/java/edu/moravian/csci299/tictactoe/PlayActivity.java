package edu.moravian.csci299.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    /** All button ids from the layout from top left to bottom right of board */
    private final List<Integer> buttonIds = Arrays.asList(R.id.button1, R.id.button2,
            R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8,
            R.id.button9);

    /** The Game object to start rounds */
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Make game state persist on rotation
        game = new ViewModelProvider(this).get(Game.class);

        // Set the on click listener for each button
        for (int id : buttonIds) findViewById(id).setOnClickListener(this);

        // If game hasn't started, get the chosen difficulty, set the ai accordingly, and play game
        if (!game.hasStarted()) {
            String difficulty = getIntent().getStringExtra("difficulty");
            setAI(difficulty);
            game.startNewRound();
        }
        setPlayerPieceMessage();
        updateBoard();
    }

    /**
     * Get the id of the button clicked and play that piece.
     */
    @Override
    public void onClick(View v) {
        int buttonIndex = buttonIds.indexOf(v.getId());
        game.playPiece(buttonIndex / 3, buttonIndex % 3);
        updateBoard();
        checkGameOver();
    }

    /**
     * If the game is over, create a new Intent and
     * send the activity the status message and game stats.
     */
    private void checkGameOver() {
        if (game.hasPlayerWon() || game.hasAIWon() || game.hasTied()) {
            int messageId = game.hasPlayerWon() ? R.string.player_won : game.hasAIWon()
                    ? R.string.ai_won : R.string.tied_game;
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("status", new int[] {
                    messageId,
                    game.getPlayerWins(),
                    game.getAIWins(),
                    game.getTies(),
            });
            startActivityForResult(intent, 0);  // Doesn't kill this activity
        }
    }

    /**
     * Sets the player's piece message above or beside the board.
     */
    private void setPlayerPieceMessage() {
        ((TextView)findViewById(R.id.player_piece)).setText(getString(R.string.you_are, game.getPlayerPiece()));
    }

    /**
     * Set every button's text on the board.
     */
    private void updateBoard() {
        Board board = game.getBoard();
        for (int i = 0; i < buttonIds.size(); i++)
            ((Button)findViewById(buttonIds.get(i)))
                    .setText(String.valueOf(board.getPiece(i/3, i%3)));
    }

    /**
     * Sets the AI based on difficulty chosen.
     * @param difficulty A String that can be "Easy", "Medium", or "Hard".
     */
    private void setAI(String difficulty) {
        AI ai;
        if (difficulty.equals("Easy")) ai = new EasyAI();
        else if (difficulty.equals("Medium")) ai = new MediumAI();
        else ai = new HardAI();
        game.setAI(ai);
    }

    /**
     * Starts a new round when start new round button is pressed in GameOverActivity.
     * @param requestCode Same value as requestCode given to startActivityForResult(). Not used.
     * @param resultCode Whether the activity executed successfully or was cancelled. Not used.
     * @param data Data added from GameOverActivity. Not used.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        game.startNewRound();
        updateBoard();
        setPlayerPieceMessage();
    }
}
