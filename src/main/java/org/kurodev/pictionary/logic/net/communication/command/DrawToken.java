package org.kurodev.pictionary.logic.net.communication.command;

import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class DrawToken implements Encodable {


    private Participant participant;

    public DrawToken(EasyByteReader bytes) {
        decode(bytes);
    }

    public DrawToken(Participant participant) {
        if (participant == null) {
            throw new NullPointerException("Participant must not be null");
        }
        this.participant = participant;
    }

    @Override
    public void decode(EasyByteReader data) {
        participant = new Participant(data);
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(participant);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawToken drawToken = (DrawToken) o;
        return Objects.equals(participant, drawToken.participant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participant);
    }
}
