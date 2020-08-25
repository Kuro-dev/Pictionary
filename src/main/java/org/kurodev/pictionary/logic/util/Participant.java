package org.kurodev.pictionary.logic.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

public class Participant implements Encodable {

    private String name;
    private int score;
    private String colour;

    public Participant(EasyByteReader in) {
        decode(in);
    }

    public Participant(String name, int score) {
        if (name.length() > 9) name = name.substring(0, 9);
        this.name = name;
        this.score = score;
        this.colour = RandomColor.getHex();
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", colour='" + colour + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return score == that.score &&
                Objects.equals(name, that.name) &&
                Objects.equals(colour, that.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, colour);
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

    @Override
    public void decode(EasyByteReader in) {
        name = in.readString();
        colour = in.readString();
        score = in.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(name);
        out.write(colour);
        out.write(score);
    }
}
