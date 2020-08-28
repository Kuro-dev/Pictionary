package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;

/**
 * @author kuro
 **/
public abstract class Token implements Encodable {
    public Token() {
    }

    public Token(EasyByteReader in) {
        decode(in);
    }
}
