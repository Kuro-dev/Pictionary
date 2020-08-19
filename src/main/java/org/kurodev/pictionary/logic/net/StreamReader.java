package org.kurodev.pictionary.logic.net;

import org.kurodev.pictionary.logic.net.interfaces.NetworkCallback;

import java.io.IOException;
import java.io.InputStream;

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
            int len = in.read();
            byte[] bytes = new byte[len];
           int readBytes = in.read(bytes);
            if (readBytes != len) {
                System.err.println("something went wrong in stream reader");
            }
        } catch (IOException e) {
            callback.onConnectionLost(e);
        }
    }
}
