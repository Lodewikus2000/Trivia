package com.example.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter {

    private ArrayList<Score> scores;


    public HighscoreAdapter(Context context, int resource, ArrayList<Score> objects) {
        super(context, resource, objects);
        scores = objects;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_row, parent, false);
        }

        Score score = scores.get(position);

        ((TextView) convertView.findViewById(R.id.textViewName)).setText(score.getName());

        ((TextView) convertView.findViewById(R.id.textViewScore)).setText(Integer.toString(score.getScore()));


        return convertView;

    }
}
