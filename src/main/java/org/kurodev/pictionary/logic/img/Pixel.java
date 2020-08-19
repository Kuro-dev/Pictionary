package org.kurodev.pictionary.logic.img;

import org.kurodev.pictionary.logic.net.interfaces.Encodable;
import org.kurodev.pictionary.logic.net.interfaces.EncodingException;

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
        final String st = new String(bytes);
        String[] split = st.split(delimiter());
        if (split.length == 3) {
            x = Integer.parseInt(split[0]);
            y = Integer.parseInt(split[1]);
            argb = Integer.parseInt(split[2]);
        } else {
            throw new EncodingException("Could not decode Pixel format");
        }
    }

    @Override
    public byte[] encode() {
        return (x + delimiter() + y + delimiter() + argb).getBytes();
    }
}
