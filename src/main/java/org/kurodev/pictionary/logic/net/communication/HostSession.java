package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author kuro
 **/
public class HostSession implements Runnable, NetHandler {
    private final String username;
    private final ServerSocket socket;
    private final List<NetClient> clients = new ArrayList<>();
    private NetworkCallback callback;
    private int playerCount = 0;
    private CountDownLatch latch = new CountDownLatch(1);

    public HostSession(String username, ServerSocket socket, NetworkCallback callback) {
        this.socket = socket;
        this.username = username;
        this.callback = callback;
    }

    public List<NetClient> getClients() {
        return clients;
    }

    public void open(int playerCount) {
        latch = new CountDownLatch(1);
        this.playerCount = playerCount;
        for (int i = 0; i < playerCount; i++) {
            acceptPlayer();
        }
    }

    private void acceptPlayer() {
        Thread thread = new Thread(this, "Player acceptance thread ");
        thread.setDaemon(true);
        thread.start();
    }

    public int connectedPlayers() {
        return (int) clients.stream().filter(NetClient::hasClient).count();
    }

    /**
     * Sets the Callbacks to the actual GUI and draw callback to start synchronizing the the players.
     */
    public void start() {
        for (NetClient client : clients) {
            client.getHandler().setCallback(callback);
        }
    }

    @Override
    public void run() {
        try {
            Socket sock = socket.accept();
            NetClient client = new NetClient();
            NetworkHandler han = NetworkHandler.create(sock, client);
            client.setHandler(han);
            clients.add(client);
            if (clients.size() == playerCount) {
                latch.countDown();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits until the desired Playercount has been reached
     */
    public void awaitConnections() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            //just don't call interrupts.
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits until the desired Playercount has been reached
     */
    public boolean awaitConnections(int time, TimeUnit unit) {
        try {
            return latch.await(time, unit);
        } catch (InterruptedException e) {
            //just don't call interrupts.
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Encodable obj) {
        clients.forEach(netClient -> netClient.getHandler().send(obj));
    }
}
