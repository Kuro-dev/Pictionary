package org.kurodev.pictionary.logic.util;

import java.nio.ByteBuffer;

/**
 * @author kuro
 **/
public class ByteUtils {
    public static int byteToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
    public static byte[] intToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }
}
