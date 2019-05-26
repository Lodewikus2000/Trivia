package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements QuestionGetHelper.Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {

        QuestionGetHelper request = new QuestionGetHelper(this);
        request.getQuestions(this);

    }

    public void showHighscores(View view) {
        Intent intent = new Intent(MainActivity.this, HighscoresActivity.class);
        startActivity(intent);
    }


    @Override
    public void gotQuestions(ArrayList<Question> someQuestions) {

        Intent intent = new Intent(MainActivity.this, GamePlayActivity.class);
        intent.putExtra("questions", someQuestions);
        startActivity(intent);

    }


    @Override
    public void gotQuestionsError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Couldn't connect to questions database", Toast.LENGTH_LONG).show();

    }
}
