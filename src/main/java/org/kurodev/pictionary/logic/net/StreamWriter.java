package org.kurodev.pictionary.logic.net;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author kuro
 **/
public class StreamWriter {
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
        write(msg.getBytes(StandardCharsets.UTF_8));
    }
}
