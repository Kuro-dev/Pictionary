package org.kurodev.pictionary.logic.net.stream;

import org.kurodev.pictionary.logic.net.encoding.EasyByteStream;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.StringEncoder;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Message implements Encodable {
    private String msg;
    private long time;

    public Message(EasyByteStream bytes) {
        decode(bytes);
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

    public Message(String msg) {
        time = System.currentTimeMillis();
        this.msg = msg;
    }

    @Override
    public void decode(EasyByteStream data) {
        time = data.readLong();
        msg = StringEncoder.decodeNextString(data.readRemaining());
    }

    @Override
    public byte[] encode() {
        byte[] time = ByteUtils.longToByte(this.time);
        byte[] msg = StringEncoder.encode(this.msg);
        return ByteUtils.combine(time, msg);
    }
}
