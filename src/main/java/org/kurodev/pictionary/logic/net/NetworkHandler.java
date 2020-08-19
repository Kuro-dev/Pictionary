package org.kurodev.pictionary.logic.net;

import org.kurodev.pictionary.logic.net.interfaces.NetworkCallback;

import java.io.IOException;
import java.net.Socket;

/**
 * @author kuro
 **/
public class NetworkHandler {
    private final Socket socket;
    private final NetworkCallback callback;
    private final StreamReader in;
    private final StreamWriter out;

    private NetworkHandler(Socket socket, NetworkCallback callback, StreamWriter out, StreamReader in) {
        this.socket = socket;
        this.callback = callback;
        this.out = out;
        this.in = in;
    }

    public static NetworkHandler create(String ip, int port, NetworkCallback callback) throws IOException {
        Socket socket = new Socket(ip, port);
        StreamReader reader = new StreamReader(socket.getInputStream(), callback);
        StreamWriter writer = new StreamWriter(socket.getOutputStream());
        return new NetworkHandler(socket, callback, writer, reader);
    }
}
