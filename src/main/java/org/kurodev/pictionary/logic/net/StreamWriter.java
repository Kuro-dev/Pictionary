package org.kurodev.pictionary.logic.net;

import org.kurodev.pictionary.logic.net.encoding.Code;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author kuro
 **/
public class StreamWriter {
    public static final byte[] DELIMITER = "\n\n\n".getBytes();
    Charset set = StandardCharsets.UTF_8;
    private final OutputStream out;

    protected StreamWriter(OutputStream out) {
        this.out = out;
    }

    public void write(byte[] bytes) throws IOException {
        out.write(bytes.length);
        out.write(bytes);
        out.flush();
    }

    public void write(String msg) throws IOException {
        write(msg.getBytes(set));
    }

    public void write(Encodable obj) throws IOException {
        byte[] code = Code.get(obj).name().getBytes(set);
        byte[] arr = new byte[obj.encode().length + code.length + DELIMITER.length];
        System.arraycopy(code, 0, arr, 0, code.length);
        System.arraycopy(DELIMITER, 0, arr, code.length, DELIMITER.length);
        byte[] encode = obj.encode();
        for (int i = 0; i < encode.length; i++) {
            int x = i + code.length + DELIMITER.length;
            arr[x] = encode[i];
        }
        write(arr);
    }
}
