package org.kurodev.pictionary.logic.net.encoding.stream;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
@SuppressWarnings("ResultOfMethodCallIgnored")
public class StringEncoder {
    //Changing this value will affect the tests.
    public static final byte[] DELIMITER = {0x2};
    private static final char[] DELIMITER_CHARS = toChar();
    private static final Charset SET = StandardCharsets.UTF_8;

    private static char[] toChar() {
        char[] chars = new char[StringEncoder.DELIMITER.length];
        for (int i = 0; i < StringEncoder.DELIMITER.length; i++) {
            chars[i] = (char) StringEncoder.DELIMITER[i];
        }
        return chars;
    }

    public static byte[] encode(String... strings) {
        StringBuilder out = new StringBuilder();
        for (String string : strings) {
            out.append(DELIMITER_CHARS).append(string);
        }
        out.append(DELIMITER_CHARS);
        return out.toString().getBytes(SET);
    }

    public static byte[] encode(String string) {
        StringBuilder b = new StringBuilder(string.length());
        return b.append(string).append(DELIMITER_CHARS).toString().getBytes(SET);
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

    static byte peek(PushbackInputStream in) {
        return peek(in, 1)[0];
    }

    public static String decodeNextString(byte[] bytes) {
        return decode(bytes)[0];
    }

    static byte[] peek(PushbackInputStream in, int length) {
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
