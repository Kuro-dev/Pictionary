package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class ScoreToken extends Token {

    String name;
    int score;

    public ScoreToken(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public ScoreToken(EasyByteReader d) {
        decode(d);
    }

    @Override
    public String toString() {
        return "ScoreToken{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreToken that = (ScoreToken) o;
        return score == that.score &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }

    @Override
    public void decode(EasyByteReader data) {
        name = data.readString();
        score = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(name);
        out.write(score);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
