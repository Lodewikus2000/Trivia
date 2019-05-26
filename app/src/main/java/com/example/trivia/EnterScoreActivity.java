package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterScoreActivity extends AppCompatActivity implements HighScorePostHelper.Callback {

    int retrievedScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_score);

        Intent intent = getIntent();
        retrievedScore = intent.getIntExtra("score", 0);

        setTitle("Almost done...");


        final EditText editText = findViewById(R.id.editText);

        // This makes the 'ok' or 'done' button on the keyboard also press the 'ok' button on the screen.
        // Found it here: https://stackoverflow.com/questions/9596010/android-use-done-button-on-keyboard-to-click-button
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitScore();
                    return true;
                }
                return false;
            }
        });
    }


    public void onClicked(View view){
        submitScore();

    }


    public void submitScore(){

        // We don't want the user to submit their score several times by spamming the button.
        Button button = findViewById(R.id.buttonSubmit);
        button.setEnabled(false);

        EditText editText = findViewById(R.id.editText);

        String name = editText.getText().toString();

        HighScorePostHelper request = new HighScorePostHelper(this, name, retrievedScore);
        request.postScore(this);

    }


    @Override
    public void postedScore() {

        Intent intent = new Intent(EnterScoreActivity.this, HighscoresActivity.class );

        startActivity(intent);

    }


    @Override
    public void postedScoreError(String message) {

        Button button = findViewById(R.id.buttonSubmit);
        button.setEnabled(true);

        Toast.makeText(this, "Error submitting your score. Please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    // We don't want to go back to the questions activity.
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
