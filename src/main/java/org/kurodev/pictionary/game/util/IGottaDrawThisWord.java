package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class IGottaDrawThisWord implements Encodable {

    String ch;

    public IGottaDrawThisWord(String ch) {
        this.ch = ch;
    }

    public IGottaDrawThisWord(EasyByteReader data) {
        decode(data);
    }

    @Override
    public void decode(EasyByteReader data) {
        ch = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(ch);
    }

    public String getWord() {
        return ch;
    }
}
