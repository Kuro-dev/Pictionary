package org.kurodev.pictionary.logic.net.encoding;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
public class StringEncoder {
    //Changing this value will affect the tests.
    private static final char[] DELIMITER = {0x2};

    public static byte[] encode(String... strings) {
        StringBuilder out = new StringBuilder();
        for (String string : strings) {
            out.append(DELIMITER).append(string);
        }
        out.append(DELIMITER);
        return out.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] encode(String string) {
        StringBuilder b = new StringBuilder(30);
        char[] del = DELIMITER;
        return b.append(del).append(string).append(del).toString().getBytes(StandardCharsets.UTF_8);
    }

    public static String[] decode(byte[] bytes) {
        String delimiter = new String(DELIMITER);
        String string = new String(bytes);
        String[] strings = string.split(delimiter);
        List<String> list = new ArrayList<>();
        for (String s : strings) {
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list.toArray(new String[0]);
    }

    private static byte peek(PushbackInputStream in) {
        return peek(in, 1)[0];
    }

    public static String decodeNextString(byte[] bytes) {
        return decode(bytes)[0];
    }

    @SuppressWarnings("SameParameterValue")
    private static byte[] peek(PushbackInputStream in, int length) {
        byte[] b = new byte[length];
        if (length > 0) {
            try {
                in.read(b);
                in.unread(b);
            } catch (IOException e) {
                //should never be thrown
                throw new RuntimeException(e);
            }
            return b;
        }
        throw new IllegalArgumentException("Length must not be 0");
    }
}
