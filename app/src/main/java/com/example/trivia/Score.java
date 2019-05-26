package com.example.trivia;

public class Score implements Comparable<Score> {

    private String name;
    private int score;


    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    // This makes it possible to sort the scores which is useful to display them in descending order.
    @Override
    public int compareTo(Score compareScoreObject) {
        int compareScore = compareScoreObject.getScore();
        return compareScore - this.score;
    }

}
