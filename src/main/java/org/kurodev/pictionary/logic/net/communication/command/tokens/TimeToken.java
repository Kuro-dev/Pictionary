package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class TimeToken extends Token {
    private int remaining;

    public TimeToken(EasyByteReader in) {
        super(in);
    }

    public TimeToken(int remaining) {

        this.remaining = remaining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeToken timeToken = (TimeToken) o;
        return remaining == timeToken.remaining;
    }

    @Override
    public int hashCode() {
        return Objects.hash(remaining);
    }

    @Override
    public void decode(EasyByteReader data) {
        remaining = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(remaining);
    }


    public int getTime() {
        return remaining;
    }
}
