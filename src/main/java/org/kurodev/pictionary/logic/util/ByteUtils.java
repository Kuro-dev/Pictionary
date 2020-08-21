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

    public static byte[] intToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public static int[][] byteToInt2D(byte[] bytes, int width, int height) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        int[][] out = new int[width][height];
        for (int x = 0; x < out.length; x++) {
            for (int y = 0; y < out[0].length; y++) {
                byte[] integer = new byte[4];
                try {
                    //noinspection ResultOfMethodCallIgnored
                    bis.read(integer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int val = byteToInt(integer);
                out[x][y] = val;
            }
        }
        return out;
    }

    public static byte[] combine(byte[]... bytes) {
        int size = 0;
        for (byte[] aByte : bytes) {
            size += aByte.length;
        }
        byte[] out = new byte[size];
        int x = 0;
        for (byte[] aByteArray : bytes) {
            System.arraycopy(aByteArray, 0, out, x, aByteArray.length);
            x += aByteArray.length;
        }
        return out;
    }

    public static long byteToLong(byte[] n) {
        return ByteBuffer.wrap(n).getLong();
    }

    public static byte[] longToByte(long i) {
        return ByteBuffer.allocate(8).putLong(i).array();
    }
}
