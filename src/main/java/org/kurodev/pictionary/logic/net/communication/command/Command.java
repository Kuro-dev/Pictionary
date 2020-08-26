package org.kurodev.pictionary.logic.net.communication.command;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

/**
 * @author kuro
 **/
public class Command implements Encodable {
    private String command;
    private long time;

    public Command(EasyByteReader bytes) {
        decode(bytes);
    }

    public Command(String command) {
        time = System.currentTimeMillis();
        this.command = command;
    }

    @Override
    public String toString() {
        return "Command{" +
                "msg='" + command + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command message = (Command) o;
        return time == message.time &&
                Objects.equals(command, message.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, time);
    }

    @Override
    public void decode(EasyByteReader data) {
        time = data.readLong();
        command = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(time);
        out.write(command);
    }
}
