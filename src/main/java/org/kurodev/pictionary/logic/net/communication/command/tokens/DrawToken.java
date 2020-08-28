package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * Specifies which Participant is currently allowed to Draw.
 *
 * @author kuro
 **/
public class DrawToken extends Token {


    private Participant participant;

    public DrawToken(EasyByteReader bytes) {
        super(bytes);
    }

    public DrawToken(Participant participant) {
        if (participant == null) {
            throw new NullPointerException("Participant must not be null");
        }
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    @Override
    public void decode(EasyByteReader data) {
        participant = new Participant(data.readEncodable());
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
