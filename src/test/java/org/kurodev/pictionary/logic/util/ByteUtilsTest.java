package org.kurodev.pictionary.logic.util;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ByteUtilsTest {

    private static List<Byte> convertBytesToList(byte[] bytes) {
        final List<Byte> list = new ArrayList<>();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }

    @Test
    public void byteToInt() {
        byte[] b = new byte[]{0, 0, 0, 16};
        assertEquals(16, ByteUtils.byteToInt(b));
    }

    @Test
    public void intToByte() {
        byte[] b = new byte[]{0, 0, 0, 25};
        assertArrayEquals(b, ByteUtils.intToByte(25));
    }

    @Test
    public void combine() {
        byte[] test = "12345".getBytes(StandardCharsets.UTF_8);
        byte[] test2 = "6789".getBytes(StandardCharsets.UTF_8);
        byte[] combined = ByteUtils.combine(test, test2);
        assertEquals(combined.length, test.length + test2.length);
        List<Byte> b = convertBytesToList(combined);
        assertTrue(b.containsAll(convertBytesToList(test)));
        assertTrue(b.containsAll(convertBytesToList(test2)));
    }
}