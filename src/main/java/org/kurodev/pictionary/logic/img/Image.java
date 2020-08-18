package org.kurodev.pictionary.logic.img;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kuro
 **/
public class Image {
    private final int[][] pixels;
    private final int width;
    private final int height;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width][height];
        initialize();
    }

    public void write(Pixel pix) {
        write(pix.getX(), pix.getY(), pix.getArgb());
    }

    public void write(int x, int y, int argb) {
        pixels[x][y] = (byte) argb;
    }

    private void initialize() {
        for (int[] pixel : pixels) {
            Arrays.fill(pixel, 0xffffff);
        }
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
}
