package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class HintToken extends Token {
    private String hint;

    public HintToken(String hint) {
        this.hint = hint;
    }

    public HintToken(EasyByteReader in) {
        super(in);
    }

    @Override
    public void decode(EasyByteReader data) {
        hint = data.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HintToken hintToken = (HintToken) o;
        return Objects.equals(hint, hintToken.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hint);
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(hint);
    }

    public String getHint() {
        return hint;
    }
}
