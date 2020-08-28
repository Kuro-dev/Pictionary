package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

public class IGiveYouThreeWordsToChooseFrom implements Encodable {

    String a, b, c;

    public IGiveYouThreeWordsToChooseFrom(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public IGiveYouThreeWordsToChooseFrom(EasyByteReader data) {
        decode(data);
    }

    @Override
    public void decode(EasyByteReader data) {
        a = data.readString();
        b = data.readString();
        c = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(a);
        out.write(b);
        out.write(c);
    }

    public String get(int i) {
        return i == 0 ? a : (i == 1 ? b : c);
    }

    @Override
    public String toString() {
        return "IGiveYouThreeWordsToChooseFrom{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IGiveYouThreeWordsToChooseFrom that = (IGiveYouThreeWordsToChooseFrom) o;
        return Objects.equals(a, that.a) &&
                Objects.equals(b, that.b) &&
                Objects.equals(c, that.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
