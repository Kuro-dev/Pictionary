package org.kurodev.pictionary.logic.net.encoding.stream;

import org.kurodev.pictionary.logic.net.encoding.EncodingException;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Arrays;

/**
 * @author kuro
 **/
@SuppressWarnings("ALL")
public class EasyByteReader extends ByteArrayInputStream {
    public EasyByteReader(byte[] buf) {
        super(buf);
    }

    public EasyByteReader(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public byte[] read(int size) {
        byte[] n = new byte[size];
        try {
            read(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return n;
    }

    public long readLong() {
        byte[] n = new byte[8];
        try {
            read(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ByteUtils.byteToLong(n);
    }

    public int readInt() {
        byte[] n = new byte[4];
        try {
            read(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ByteUtils.byteToInt(n);
    }

    /**
     * @return All remaining not yet read bytes.
     */
    public byte[] readRemaining() {
        byte[] b = new byte[available()];
        try {
            read(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    public EasyByteReader readEncodable() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length = EasyByteWriter.ENCODABLE_DELIMITER.length;
        PushbackInputStream s = new PushbackInputStream(this, length);
        try {
            while (!Arrays.equals(StringEncoder.peek(s, length), EasyByteWriter.ENCODABLE_DELIMITER) && s.available() != -1) {
                out.write(s.read());
            }
            s.skip(length);
        } catch (IOException e) {
            throw new EncodingException(e);
            //never thrown
        }
        return new EasyByteReader(out.toByteArray());
    }

    public String readString() {
        String out = "";
        int length = StringEncoder.DELIMITER.length;
        PushbackInputStream s = new PushbackInputStream(this, length);
        try {
            while (!Arrays.equals(StringEncoder.peek(s, length), StringEncoder.DELIMITER) && s.available() != -1) {
                out += (char) s.read();
            }
        } catch (IOException e) {
            throw new EncodingException(e);
            //never thrown
        }
        return out;
    }
}
