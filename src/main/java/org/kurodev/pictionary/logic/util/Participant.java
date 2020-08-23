package org.kurodev.pictionary.logic.util;

public class Participant {

    private String name;
    private int score;
    private String colour;
    public Participant(String name, int score) {
        if (name.length() > 9) name = name.substring(0, 9);
        this.name = name;
        this.score = score;
        this.colour = RandomColor.getHex();
        System.out.println(colour);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getColour() {
        return colour;
    }
}
