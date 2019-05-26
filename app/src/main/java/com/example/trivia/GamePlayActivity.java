package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

public class GamePlayActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private int questionIndex = 0;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        setTitle("Answer these questions");


        if (savedInstanceState != null){
            questionIndex = savedInstanceState.getInt("questionIndex");
            score = savedInstanceState.getInt("score");
            questions = (ArrayList<Question>) savedInstanceState.getSerializable("questions");

            TextView scoreView = findViewById(R.id.textViewScore);
            scoreView.setText(Integer.toString(score));
        } else {
            Intent intent = getIntent();
            questions = (ArrayList<Question>) intent.getSerializableExtra("questions");

        }

        displayQuestion();
    }


    public void displayQuestion() {

        // This is supposed to prevent the user from crashing the app by clicking really fast.
        if (questionIndex >= questions.size()) {
            return;
        }


        TextView remainingView = findViewById(R.id.textViewRemaining);
        remainingView.setText(Integer.toString(questions.size() - questionIndex));


        Question currentQuestion = questions.get(questionIndex);

        TextView questionView = findViewById(R.id.textViewQuestion);

        questionView.setText(currentQuestion.getQuestion());

        ArrayList<String> answers = new ArrayList<>(currentQuestion.getIncorrectAnswers());


        answers.add(currentQuestion.getCorrectAnswer());

        Collections.shuffle(answers);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answers);
        ListView answersList = findViewById(R.id.answersList);
        answersList.setAdapter(adapter);
        answersList.setOnItemClickListener(new ListItemClickListener());

    }


    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            // This prevents the user from clicking really fast and crashing after the last question.
            if (questionIndex == questions.size()) {
                return;
            }

            String clickedAnswer = (String) parent.getItemAtPosition(position);

            if (clickedAnswer == questions.get(questionIndex).getCorrectAnswer()) {

                score ++;
                ((TextView) findViewById(R.id.textViewScore)).setText(Integer.toString(score));
            }


            questionIndex += 1;

            if (questionIndex >= questions.size()) {

                // Go to the highscores.
                Intent intent = new Intent(GamePlayActivity.this, EnterScoreActivity.class );
                intent.putExtra("score", score);

                startActivity(intent);

            } else {
                displayQuestion();
            }
        }
    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("questionIndex", questionIndex);
        outState.putInt("score", score);
        outState.putSerializable("questions", questions);
    }
}
