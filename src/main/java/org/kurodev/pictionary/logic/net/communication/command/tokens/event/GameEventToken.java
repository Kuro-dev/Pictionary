package org.kurodev.pictionary.logic.net.communication.command.tokens.event;

import org.kurodev.pictionary.logic.net.communication.command.tokens.Token;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class GameEventToken extends Token {


    private Event event;

    public GameEventToken(Event event) {

        this.event = event;
    }

    public GameEventToken(EasyByteReader in) {
        decode(in);
    }

    @Override
    public void decode(EasyByteReader data) {
        event = Event.get(data.readInt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEventToken that = (GameEventToken) o;
        return event == that.event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(event.ordinal());
    }
}
