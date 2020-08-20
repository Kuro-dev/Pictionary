package org.kurodev.pictionary.logic;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.StringEncoder;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Pictionary implements Encodable {
    private final StringEncoder encoder = new StringEncoder();
    private String word;

    public Pictionary() {
        this("");
    }

    public Pictionary(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Pictionary{" +
                "word='" + word + '\'' +
                '}';
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

    public boolean matches(String solution) {
        return word.equalsIgnoreCase(solution);
    }

    public String getWord() {
        return word;
    }

    @Override
    public void decode(byte[] bytes) {
        word = encoder.decodeNextString(bytes);
    }

    @Override
    public byte[] encode() {
        byte[] word = encoder.encode(this.word);
        return ByteUtils.combine(word);
    }
}
