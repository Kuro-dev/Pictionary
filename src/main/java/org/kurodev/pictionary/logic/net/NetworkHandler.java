package org.kurodev.pictionary.logic.net;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author kuro
 **/
public class NetworkHandler {
    public static final int DEFAULT_PORT = 9876;
    private final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private final Socket socket;
    private final StreamReader in;
    private final StreamWriter out;
    private NetworkCallback callback;
    private NetworkHandler(Socket socket, NetworkCallback callback, StreamWriter out, StreamReader in) {
        this.socket = socket;
        this.callback = callback;
        this.out = out;
        this.in = in;
    }

    public static NetworkHandler create(String ip, int port, NetworkCallback callback) throws IOException {
        Socket socket = new Socket(ip, port);
        return create(socket, callback);
    }

    public static NetworkHandler create(String ip, NetworkCallback callback) throws IOException {
        return create(ip, DEFAULT_PORT, callback);
    }

    public static NetworkHandler create(Socket soc, NetworkCallback callback) throws IOException {
        StreamReader reader = new StreamReader(soc.getInputStream(), callback);
        StreamWriter writer = new StreamWriter(soc.getOutputStream());
        NetworkHandler han = new NetworkHandler(soc, callback, writer, reader);
        han.initialize();
        return han;
    }

    public void setCallback(NetworkCallback callback) {
        this.callback = callback;
        in.setCallback(callback);
    }

    private void initialize() {
        ex.scheduleAtFixedRate(in, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void send(Encodable obj) {
        try {
            out.write(obj);
        } catch (IOException e) {
            callback.onConnectionLost(e);
        }
    }
}
