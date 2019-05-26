package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements HighscoresGetHelper.Callback {

    private ArrayList<Score> highscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        if (savedInstanceState == null) {
            HighscoresGetHelper request = new HighscoresGetHelper(this);
            request.getHighscores(this);
        } else {

            highscores = (ArrayList<Score>) savedInstanceState.getSerializable("highscores");
            displayScores(highscores);
        }

        setTitle("Highscores");
    }


    public void displayScores(ArrayList<Score> scores) {

        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.score_row, scores);

        ListView listview = findViewById(R.id.scoreList);

        listview.setAdapter(adapter);

    }


    @Override
    // We want to returns to the main screen.
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void gotHighscores(ArrayList<Score> scores) {

        highscores = scores;

        displayScores(highscores);

    }


    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this, "Could not load highscores", Toast.LENGTH_SHORT).show();
    }


    // This method is not necessary for functionality, but it saves some bandwidth and is generally faster.
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putSerializable("highscores", highscores);

    }
}
