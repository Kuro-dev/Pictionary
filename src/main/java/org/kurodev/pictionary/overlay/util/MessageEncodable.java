package org.kurodev.pictionary.overlay.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

import java.util.Objects;

public class MessageEncodable implements Encodable {

    String name;
    String message;

    public MessageEncodable(String _name, String _message) {
        name = _name;
        message = _message;
    }

    public MessageEncodable(EasyByteReader in) {
        decode(in);
    }

    @Override
    public void decode(EasyByteReader data) {
        name = data.readString();
        message = data.readString();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(name);
        out.write(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEncodable that = (MessageEncodable) o;
        return name.equals(that.name) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name + message);
    }

    @Override
    public String toString() {
        return "MessageEncodable:" +
                "  name=    " + name + '\n' +
                "  message= " + message + '\n';
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
