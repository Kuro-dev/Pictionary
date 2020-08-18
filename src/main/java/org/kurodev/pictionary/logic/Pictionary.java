package org.kurodev.pictionary.logic;

/**
 * @author kuro
 **/
public class Pictionary {
    private final String word;

    public Pictionary(String word) {
        this.word = word;
    }

    public boolean matches(String solution) {
        return word.equalsIgnoreCase(solution);
    }

    public String getWord() {
        return word;
    }
}
