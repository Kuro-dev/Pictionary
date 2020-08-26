package org.kurodev.pictionary.logic.net.communication.command.tokens;

import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class CorrectGuessToken extends Token {
    private Participant p;

    public CorrectGuessToken(EasyByteReader in) {
        super(in);
    }

    public CorrectGuessToken(Participant p) {

        this.p = p;
    }

    @Override
    public void decode(EasyByteReader data) {
        p = new Participant(data.readEncodable());
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorrectGuessToken that = (CorrectGuessToken) o;
        return Objects.equals(p, that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
