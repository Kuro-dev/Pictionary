package org.kurodev.pictionary.logic.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author kuro
 **/
public class ByteUtils {
    public static int byteToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] intToByte(int[] ints) {
        byte[] b = new byte[ints.length * 4];
        for (int i = 0; i < ints.length; i++) {
            System.arraycopy(intToByte(ints[i]), 0, b, i * 4, 4);
        }
        return b;
    }

    public static int[][] byteToInt(byte[] bytes, int width, int height) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        int[][] out = new int[width][height];
        for (int x = 0; x < out.length; x++) {
            for (int y = 0; y < out[0].length; y++) {
                byte[] integer = new byte[4];
                try {
                    bis.read(integer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int val =byteToInt(integer);
                out[x][y] = val;
            }
        }
        return out;
    }

    public static byte[] intToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }
}
