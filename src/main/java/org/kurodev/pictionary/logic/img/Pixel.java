package org.kurodev.pictionary.logic.img;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.Arrays;
import java.util.Objects;


/**
 * @author kuro
 **/
public class Pixel implements Encodable {
    private int x;
    private int y;
    private int argb;

    /**
     * Applies the state from the given bytes and inherits them into this instance.
     *
     * @param bytes representation resulting from {@link #encode()}
     */
    public Pixel(byte[] bytes) {
        decode(bytes);
    }

    public Pixel() {
        this(0, 0, 0);
    }

    public Pixel(int x, int y, int argb) {
        this.x = x;
        this.y = y;
        this.argb = argb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y &&
                argb == pixel.argb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, argb);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getArgb() {
        return argb;
    }

    @Override
    public void decode(byte[] bytes) {
        x = ByteUtils.byteToInt(Arrays.copyOfRange(bytes, 0, 4));
        y = ByteUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 8));
        argb = ByteUtils.byteToInt(Arrays.copyOfRange(bytes, 8, 12));
    }

    @Override
    public byte[] encode() {
        byte[] out = new byte[12];
        byte[] x = ByteUtils.intToByte(this.x);
        byte[] y = ByteUtils.intToByte(this.y);
        byte[] argb = ByteUtils.intToByte(this.argb);
        return ByteUtils.combine(x, y, argb);
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "x=" + x +
                ", y=" + y +
                ", argb=" + argb +
                '}';
    }
}
