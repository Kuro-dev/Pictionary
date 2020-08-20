package org.kurodev.pictionary.logic;

import org.kurodev.pictionary.logic.net.encoding.EasyByteStream;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.StringEncoder;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author kuro
 **/
public class Pictionary implements Encodable {
    private final StringEncoder encoder = new StringEncoder();
    private int timerTime;
    private String word;

    public Pictionary(String word, int time) {
        this.word = word;
        timerTime = time;
    }

    public Pictionary(int time) {
        timerTime = time;
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

    public void submit(String other) {

    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public boolean matches(String solution) {
        return word.equalsIgnoreCase(solution);
    }

    public String getWord() {
        return word;
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
}
