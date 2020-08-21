package org.kurodev.pictionary.logic.net.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
public class HostSession {
    private final String username;
    private final int port;
    List<Client> clients = new ArrayList<>();

    public HostSession(int port) {
        this(System.getProperty("user.name"), port);
    }

    public HostSession(String username, int port) {

        this.username = username;
        this.port = port;
    }

    public List<Client> getClients() {
        return clients;
    }

}
