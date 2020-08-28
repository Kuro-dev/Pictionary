package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class Trio implements Encodable {

    String a, b, c;

    public Trio(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Trio(EasyByteReader data) {
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
}
