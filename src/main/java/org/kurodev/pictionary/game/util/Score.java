package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class Score implements Encodable {

    String name;
    int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Score(EasyByteReader d) {
        decode(d);
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
