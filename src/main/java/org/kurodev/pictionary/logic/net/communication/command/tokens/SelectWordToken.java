package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;
import org.kurodev.pictionary.logic.net.encoding.stream.StringEncoder;

import java.util.Arrays;

/**
 * @author kuro
 **/
public class SelectWordToken extends Token {
    private String[] words;

    public SelectWordToken() {
        this("");
    }

    public SelectWordToken(String... words) {

        this.words = words;
    }

    @Override
    public String toString() {
        return "SelectWordToken{" +
                "words=" + Arrays.toString(words) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectWordToken that = (SelectWordToken) o;
        return Arrays.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(words);
    }

    @Override
    public void decode(EasyByteReader data) {
        words = StringEncoder.interpretArray(data.readString());
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(Arrays.toString(words));
    }
}
