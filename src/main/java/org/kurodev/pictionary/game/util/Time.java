package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

public class Time implements Encodable {

    int time;

    public Time(int time) {
        this.time = time;
    }

    public Time(EasyByteReader dat) {
        decode(dat);
    }

    @Override
    public void decode(EasyByteReader data) {
        time = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(time);
    }

    public int getTime() {
        return time;
    }
}
