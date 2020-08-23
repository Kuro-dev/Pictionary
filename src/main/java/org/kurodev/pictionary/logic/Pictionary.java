package org.kurodev.pictionary.logic;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;
import org.kurodev.pictionary.logic.timer.Countdown;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Pictionary implements Encodable {
    private final Countdown timer;
    private int timerTime;
    private String word;
    private boolean won = false;

    public Pictionary(String word, int time) {
        this.word = word;
        timerTime = time;
        timer = new Countdown(time);
    }

    public Pictionary(int time) {
        this("", time);
    }

    public Pictionary() {
        this("");
    }

    public Pictionary(String word) {
        this(word, 90);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pictionary that = (Pictionary) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    @Override
    public String toString() {
        return "Pictionary{" +
                "word='" + word + '\'' +
                ", timerTime=" + timerTime +
                '}';
    }

    @Override
    public void decode(EasyByteReader data) {
        word = data.readString();
        timerTime = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(word);
        out.write(timerTime);
    }

    public boolean isWon() {
        return won;
    }

    public boolean isGameOver() {
        return won || timer.getCurrentTime() == 0;
    }

    public void submit(String solution) {
        if (!won)
            won = word.equalsIgnoreCase(solution);
    }

    public String getWord() {
        return word;
    }

    public Countdown getTimer() {
        return timer;
    }
}
