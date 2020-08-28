package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

/**
 * @author kuro
 **/
@Deprecated(forRemoval = true)
public class TimeOutToken extends Token {
    public TimeOutToken() {
    }

    @Override
    public void decode(EasyByteReader data) {

    }

    @Override
    public void encode(EasyByteWriter out) {

    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
