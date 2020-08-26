package org.kurodev.pictionary.logic.net.encoding.stream;

import org.kurodev.pictionary.logic.net.encoding.Code;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author kuro
 **/
public class StreamWriter {
    final Charset set = StandardCharsets.UTF_8;
    private final OutputStream out;

    public StreamWriter(OutputStream out) {
        this.out = out;
    }

    public void write(byte[] bytes) throws IOException {
        out.write(ByteUtils.intToByte(bytes.length));
        out.write(bytes);
        out.flush();
    }

    public void write(String msg) throws IOException {
        write(msg.getBytes(set));
    }

    public void write(Encodable obj) throws IOException {
        final int codeLength = 1;
        final byte code = (byte) Code.get(obj).ordinal();
        EasyByteWriter writer = new EasyByteWriter();
        obj.encode(writer);
        byte[] encode = writer.toByteArray();
        final byte[] arr = new byte[encode.length + codeLength];
        arr[0] = code;
        for (int i = 0; i < encode.length; i++) {
            int x = i + codeLength;
            arr[x] = encode[i];
        }
        write(arr);
    }
}
