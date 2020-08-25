package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

public class Participant implements Encodable {

    public int score;
    private String name;
    private String colour;

    public Participant(EasyByteReader in) {
        decode(in);
    }

    public Participant(String name) {
        this(name, "", 0);
    }

    public Participant(String name, String colour) {
        this(name, colour, 0);
    }

    public Participant(String name, String colour, int score) {
        this.name = name;
        this.colour = colour;
        this.score = score;
    }

    public Participant(String name, int i) {
        this(name, "", i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return score == that.score &&
                Objects.equals(name, that.name);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name=" + name +
                ", score=" + score +
                '}';
    }

    @Override
    public void decode(EasyByteReader data) {
        name = data.readString();
        colour = data.readString();
        score = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(name);
        out.write(colour);
        out.write(score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getColour() {
        return colour;
    }
}
