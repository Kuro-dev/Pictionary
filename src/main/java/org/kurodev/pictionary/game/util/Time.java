package org.kurodev.pictionary.game.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time1 = (Time) o;
        return time == time1.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
