package com.example.trivia;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class HighScorePostHelper implements Response.Listener, Response.ErrorListener {

    private String name;
    private int score;

    private Context context;
    private Callback currentActivity;


    public interface Callback {
        void postedScore();
        void postedScoreError(String message);
    }


    public HighScorePostHelper(Context aContext, String aName, int aScore) {
        name = aName;
        score = aScore;
        context = aContext;

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        currentActivity.postedScoreError(error.getMessage());

    }


    @Override
    public void onResponse(Object response) {
        currentActivity.postedScore();
    }


    private class PostRequest extends StringRequest {


        public PostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        // Method to supply parameters to the request.
        @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("score", Integer.toString(score));
            params.put("name", name);
            return params;
        }
    }


    public void postScore(Callback activity) {
        currentActivity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        PostRequest postRequest = new PostRequest(Request.Method.POST, "https://ide50-leno88.legacy.cs50.io/triviaScores2", this, this);
        queue.add(postRequest);

    }
}
