package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class Starter implements Encodable {
    String hint;

    public Starter(String hint) {
        this.hint = hint;
    }

    public Starter(EasyByteReader dat) {
        decode(dat);
    }

    @Override
    public void decode(EasyByteReader data) {
        hint = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(hint);
    }

    public String getHint() {
        return hint;
    }
}
