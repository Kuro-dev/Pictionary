package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class IGottaDrawThisWord implements Encodable {

    String word;

    public IGottaDrawThisWord(String ch) {
        this.word = ch;
    }

    public IGottaDrawThisWord(EasyByteReader data) {
        decode(data);
    }

    @Override
    public void decode(EasyByteReader data) {
        word = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(word);
    }

    public String getWord() {
        return word;
    }
}
