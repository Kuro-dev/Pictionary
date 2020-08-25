package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

/**
 * @author kuro
 **/
public class NetClient implements NetworkCallback {
    private NetworkHandler handler;
    private Participant client;

    public NetClient() {
    }

    public boolean hasClient() {
        return client != null;
    }


    @Override
    public void onObjectReceived(Encodable obj) {
        if (obj instanceof Participant) {
            client = (Participant) obj;
            System.out.println("received client info from " + client.getName());
        }
    }

    @Override
    public void onConnectionLost(Exception e) {
        throw new RuntimeException(e);
    }

    public NetworkHandler getHandler() {
        return handler;
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "NetClient{" +
                "client=" + client +
                '}';
    }
}
