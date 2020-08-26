package org.kurodev.pictionary.logic.net.encoding.stream;

import org.jetbrains.annotations.NotNull;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author kuro
 **/
public class EasyByteWriter extends ByteArrayOutputStream {
    public EasyByteWriter() {
    }

    public EasyByteWriter(int size) {
        super(size);
    }

    public void write(@NotNull byte[] b) {
        try {
            super.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int i) {
        write(ByteUtils.intToByte(i));
    }

    public void write(int[] ints) {
        write(ByteUtils.intToByte(ints));
    }

    public void write(long i) {
        write(ByteUtils.longToByte(i));
    }

    public void write(String s) {
        write(StringEncoder.encode(s));
    }

    public void write(Encodable en) {
        en.encode(this);
    }
}
