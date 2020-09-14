package org.kurodev.pictionary.logic.net.communication.multiplayerTest;

import org.kurodev.pictionary.logic.net.communication.HostSession;
import org.kurodev.pictionary.logic.net.communication.NetworkHandler;

/**
 * @author kuro
 **/
public class MultiplayerSession {
    private final HostSession host;
    private final NetworkHandler[] clients;

    protected MultiplayerSession(HostSession host, NetworkHandler[] clients) {

        this.host = host;
        this.clients = clients;
    }

    public HostSession getHost() {
        return host;
    }

    public NetworkHandler[] getClients() {
        return clients;
    }
}
