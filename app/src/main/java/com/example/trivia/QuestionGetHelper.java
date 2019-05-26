package com.example.trivia;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionGetHelper implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback currentActivity;

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    // constructor
    public QuestionGetHelper(Context aContext) {
        context = aContext;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        currentActivity.gotQuestionsError(errorMessage);
    }

    @Override
    public void onResponse(JSONObject response) {

        // Empty ArrayList that we will fill with Questions.
        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            JSONArray questionsArray = response.getJSONArray("results");

            for (int i = 0; i < questionsArray.length(); i++) {

                JSONObject questionJson = questionsArray.getJSONObject(i);

                String category = Html.fromHtml(questionJson.getString("category"), Html.FROM_HTML_MODE_COMPACT).toString();
                String type = Html.fromHtml(questionJson.getString("type"), Html.FROM_HTML_MODE_COMPACT).toString();
                String difficulty = Html.fromHtml(questionJson.getString("question"), Html.FROM_HTML_MODE_COMPACT).toString();
                String questionPhrase = Html.fromHtml(questionJson.getString("question"), Html.FROM_HTML_MODE_COMPACT).toString();
                String correctAnswer = Html.fromHtml(questionJson.getString("correct_answer"), Html.FROM_HTML_MODE_COMPACT).toString();
                JSONArray incorrectAnswersObject = questionJson.getJSONArray("incorrect_answers");

                ArrayList<String> incorrectAnswers = new ArrayList<String>();
                for (int j = 0; j < incorrectAnswersObject.length(); j++) {
                    String value = Html.fromHtml(incorrectAnswersObject.getString(j), Html.FROM_HTML_MODE_COMPACT).toString();
                    incorrectAnswers.add(value);
                }

                Question questionObject = new Question(category, type, difficulty, questionPhrase, correctAnswer, incorrectAnswers);

                questions.add(questionObject);

            }

            currentActivity.gotQuestions(questions);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    public void getQuestions(Callback activity) {
        currentActivity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=20&difficulty=medium&type=multiple", null, this, this);
        queue.add(jsonObjectRequest);

    }
}
