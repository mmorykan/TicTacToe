package edu.moravian.csci299.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /** The difficulty menu from the Spinner */
    Spinner difficultySelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        difficultySelector = findViewById(R.id.difficulty_select);
        List<String> difficulties = Arrays.asList("Easy", "Medium", "Hard");
        ArrayAdapter<String> dropdown = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficulties);
        dropdown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySelector.setAdapter(dropdown);

        findViewById(R.id.start_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);

        // Only information to send to play activity is the difficulty chosen
        intent.putExtra("difficulty", (String)difficultySelector.getSelectedItem());
        startActivity(intent);
    }
}