package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // finish() calls onActivityResult() in PlayActivity
        findViewById(R.id.new_game).setOnClickListener(v->finish());
        int[] status = getIntent().getIntArrayExtra("status");
        setStatusAndStats(status);
    }

    /**
     * Sets the outcome message of the game and list the number of wins, losses, and ties.
     * @param status An array containing status message id and the number of wins, losses, and ties.
     */
    private void setStatusAndStats(int[] status) {
        ((TextView)findViewById(R.id.status_message)).setText(status[0]);
        ((TextView)findViewById(R.id.player_stats))
                .setText(getString(R.string.player_stats, status[1], status[2], status[3]));
    }

}