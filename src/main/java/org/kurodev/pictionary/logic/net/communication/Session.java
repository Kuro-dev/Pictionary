package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author kuro
 **/
public class Session {
    public static HostSession host(String username, int port, NetworkCallback callback) {
        try {
            ServerSocket sock = new ServerSocket(port);
            return new HostSession(username, sock, callback);
        } catch (IOException e) {
            throw new IllegalArgumentException("Port is already is use", e);
        }
    }

    public static NetworkHandler join(Participant client, NetworkCallback callback, String ip, int port) throws IOException {
        NetworkHandler net = NetworkHandler.create(ip, port, callback);
        net.send(client);
        return net;
    }
}
