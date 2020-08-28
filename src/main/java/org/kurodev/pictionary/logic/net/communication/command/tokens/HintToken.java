package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

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
    public void encode(EasyByteWriter out) {
        out.write(hint);
    }
}
