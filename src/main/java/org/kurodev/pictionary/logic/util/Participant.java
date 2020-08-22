package org.kurodev.pictionary.logic.util;

public class Participant {

    public String name;
    public int score;
    public String colour;

    public Participant(String name, int score) {
        if (name.length() > 9) name = name.substring(0, 9);
        this.name = name;
        this.score = score;
        this.colour = RandomColor.getHex();
        System.out.println(colour);
    }
}
