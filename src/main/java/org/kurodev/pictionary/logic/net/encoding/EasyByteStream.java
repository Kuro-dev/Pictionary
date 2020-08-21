package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.util.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author kuro
 **/
@SuppressWarnings("ALL")
public class EasyByteStream extends ByteArrayInputStream {
    public EasyByteStream(byte[] buf) {
        super(buf);
    }

    public EasyByteStream(byte[] buf, int offset, int length) {
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
}
