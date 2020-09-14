package org.kurodev.pictionary.logic.net.communication.command.state;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class InternalState implements Encodable {
    private State state;

    public InternalState(EasyByteReader in) {
        decode(in);
    }

    public InternalState(State state) {
        this.state = state;
    }


    @Override
    public void decode(EasyByteReader data) {
        state = State.get(data.readInt());
    }

    @Override
    public String toString() {
        return "InternalState{" +
                "state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InternalState that = (InternalState) o;
        return state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(state.ordinal());
    }
}