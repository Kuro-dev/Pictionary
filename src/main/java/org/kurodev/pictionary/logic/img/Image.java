package org.kurodev.pictionary.logic.img;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author kuro
 **/
public class Image implements Encodable {
    private int backgroundColor = 0xffffff;
    private int[][] pixels;
    private int width;
    private int height;

    public Image(byte[] bytes) {
        decode(bytes);
    }

    public Image() {
        this(0, 0);
    }

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width][height];
        initialize();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void write(Pixel pix) {
        write(pix.getX(), pix.getY(), pix.getArgb());
    }

    public void write(int x, int y, int argb) {
        System.out.printf("Writing %d to %dx%d\n", argb, x, y);
        pixels[x][y] = argb;
    }

    private void initialize() {
        for (int[] pixel : pixels) {
            Arrays.fill(pixel, backgroundColor);
        }
    }

    public boolean isTransparent(int x, int y) {
        return (pixels[x][y] & backgroundColor) == backgroundColor;
    }

    public int[] getImage() {
        List<Integer> list = new ArrayList<>(width * height);
        for (int[] pixel : pixels) {
            for (int b : pixel) {
                list.add(b);
            }
        }
        int[] img = new int[width * height];
        for (int i = 0; i < img.length; i++) {
            img[i] = list.get(i);
        }
        return img;
    }

    @Override
    public void decode(byte[] bytes) {
        width = ByteUtils.byteToInt(Arrays.copyOfRange(bytes, 0, 4));
        height = ByteUtils.byteToInt(Arrays.copyOfRange(bytes, 4, 8));
        pixels = ByteUtils.byteToInt2D(Arrays.copyOfRange(bytes, 8, bytes.length), width, height);
    }

    @Override
    public String toString() {
        return "Image{" +
                "width=" + width +
                ", height=" + height +
                ", pixels=" + Arrays.toString(getImage()) +
                '}';
    }

    @Override
    public byte[] encode() {
        byte[] width = ByteUtils.intToByte(this.width);
        byte[] height = ByteUtils.intToByte(this.height);
        byte[] pix = ByteUtils.intToByte(this.getImage());
        byte[] out = new byte[width.length + height.length + pix.length];
        System.arraycopy(width, 0, out, 0, width.length);
        System.arraycopy(height, 0, out, 4, height.length);
        System.arraycopy(pix, 0, out, 8, pix.length);
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return width == image.width &&
                height == image.height &&
                Arrays.equals(getImage(), image.getImage());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.hashCode(pixels);
        return result;
    }
}
