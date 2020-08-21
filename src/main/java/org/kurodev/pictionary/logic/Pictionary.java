package org.kurodev.pictionary.logic;

import org.kurodev.pictionary.logic.net.encoding.EasyByteStream;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.StringEncoder;
import org.kurodev.pictionary.logic.timer.Countdown;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Pictionary implements Encodable {
    private final StringEncoder encoder = new StringEncoder();
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
    public void decode(EasyByteStream data) {
        timerTime = data.readInt();
        word = encoder.decodeNextString(data.readRemaining());
    }

    @Override
    public byte[] encode() {
        byte[] word = encoder.encode(this.word);
        byte[] timer = ByteUtils.intToByte(timerTime);
        return ByteUtils.combine(timer, word);
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
