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
        byte[] out = new byte[24];
        byte[] b = ByteUtils.intToByte(this.x);
        System.arraycopy(b, 0, out, 0, 4);
        b = ByteUtils.intToByte(this.y);
        System.arraycopy(b, 0, out, 4, 4);
        b = ByteUtils.intToByte(this.argb);
        System.arraycopy(b, 0, out, 8, 4);
        return out;
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
