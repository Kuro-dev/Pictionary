package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

public class TakeThisAsTheHint implements Encodable {
    String hint;

    public TakeThisAsTheHint(String hint) {
        this.hint = hint;
    }

    public TakeThisAsTheHint(EasyByteReader dat) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakeThisAsTheHint that = (TakeThisAsTheHint) o;
        return Objects.equals(hint, that.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hint);
    }
}
