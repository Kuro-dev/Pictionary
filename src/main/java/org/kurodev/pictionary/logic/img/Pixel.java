package org.kurodev.pictionary.logic.img;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

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
     * @param data representation resulting from {@link Encodable#encode(EasyByteWriter)}
     */
    public Pixel(EasyByteReader data) {
        decode(data);
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
    public void decode(EasyByteReader data) {
        x = data.readInt();
        y = data.readInt();
        argb = data.readInt();
    }

    @Override
    public void encode(EasyByteWriter out) {
        out.write(x);
        out.write(y);
        out.write(argb);
    }

    @Override
    public String toString() {
        return "Pixel{" +
                "x=" + x +
                ", y=" + y +
                ", argb=" + Integer.toHexString(argb) +
                '}';
    }
}
