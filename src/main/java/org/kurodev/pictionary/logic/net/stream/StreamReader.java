package org.kurodev.pictionary.logic.net.stream;

import org.kurodev.pictionary.logic.net.encoding.Code;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.interfaces.NetworkCallback;
import org.kurodev.pictionary.logic.util.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author kuro
 **/
public class StreamReader implements Runnable {
    private final InputStream in;
    private final NetworkCallback callback;

    public StreamReader(InputStream in, NetworkCallback callback) {
        this.in = in;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            callback.onObjectReceived(interpret(read()));
        } catch (IOException e) {
            callback.onConnectionLost(e);
        }
    }

    public byte[] read() throws IOException {
        byte[] buf = new byte[4];
        //noinspection ResultOfMethodCallIgnored
        in.read(buf);
        int len = ByteUtils.byteToInt(buf);
        byte[] bytes = new byte[len];
        int readBytes = in.read(bytes);
        if (readBytes != len) {
            System.err.println("something went wrong in stream reader");
        }
        return bytes;
    }

    public Encodable interpret(byte[] bytes) {
        final int codeIndex = 0;
        Code code = Code.get(bytes[codeIndex]);
        byte[] obj = Arrays.copyOfRange(bytes, 1, bytes.length);
        return code.construct(obj);
    }
}
