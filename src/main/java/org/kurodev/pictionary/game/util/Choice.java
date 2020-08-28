package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class Choice implements Encodable {

    String ch;

    public Choice(String ch) {
        this.ch = ch;
    }

    public Choice(EasyByteReader data) {
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
