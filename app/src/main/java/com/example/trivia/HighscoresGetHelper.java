package com.example.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoresGetHelper implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private Callback currentActivity;


    public interface Callback {
        void gotHighscores(ArrayList<Score> scores);
        void gotHighscoresError(String message);
    }


    public HighscoresGetHelper(Context aContext) {
        context = aContext;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        currentActivity.gotHighscoresError(errorMessage);
    }

    @Override
    public void onResponse(JSONArray response) {

        // Empty ArrayList that we will fill with Score items.
        ArrayList<Score> scores = new ArrayList<Score>();

            try {

                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = (JSONObject) response.get(i);
                    Score score = new Score(object.getString("name"), object.getInt("score"));
                    scores.add(score);
                }


                Collections.sort(scores);


                currentActivity.gotHighscores(scores);



            } catch (JSONException e) {
                e.printStackTrace();
            }

    }


    public void getHighscores(Callback activity) {
        currentActivity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://ide50-leno88.legacy.cs50.io/triviaScores2", null, this, this);
        queue.add(jsonArrayRequest);
    }
}
