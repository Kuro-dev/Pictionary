package org.kurodev.pictionary.logic.net.stream;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Message implements Encodable {
    private String msg;
    private long time;

    public Message(EasyByteReader bytes) {
        decode(bytes);
    }

    public Message(String msg) {
        time = System.currentTimeMillis();
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return time == message.time &&
                Objects.equals(msg, message.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg, time);
    }

    @Override
    public void decode(EasyByteReader data) {
        time = data.readLong();
        msg = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(time);
        out.write(msg);
    }
}
