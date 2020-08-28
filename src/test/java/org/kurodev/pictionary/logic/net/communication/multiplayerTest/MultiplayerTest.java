package org.kurodev.pictionary.logic.net.communication.multiplayerTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.communication.*;
import org.kurodev.pictionary.logic.net.communication.command.tokens.DrawToken;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author kuro
 **/
public class MultiplayerTest {
    protected HostSession host;
    protected NetworkHandler[] clients;

    @Before
    public void buildSession() throws IOException {
        MultiplayerSession session = createSession(5);
        host = session.getHost();
        clients = session.getClients();
        host.start();
    }

    @After
    public void cleanup() {
        for (NetworkHandler client : clients) {
            client.disconnect();
        }
        host.disconnect();
    }

    protected MultiplayerSession createSession(int players) throws IOException {
        final int port = 9000;
        //Setup callback class
        VoidCallback hostCallback = new VoidCallback("host");
        //create host session
        HostSession session = Session.host("Kuro", port, hostCallback);
        //open session for the desired playerCount
        session.open(players);
        List<NetworkHandler> clients = new ArrayList<>(players);
        //players join
        for (int i = 0; i < players; i++) {
            VoidCallback callback = new VoidCallback("player " + i);
            clients.add(Session.join(new Participant("player" + (i + 1)), callback, "localhost", port));
        }
        //wait for the connections
        if (!session.awaitConnections(3, TimeUnit.SECONDS)) {
            fail("Timeout");
        }
        return new MultiplayerSession(session, clients.toArray(NetworkHandler[]::new));
    }

    @Test
    public void synchronizationHasWorkedTest() {
        for (NetClient client : host.getClients()) {
            assertNotNull(client.getHandler());
        }
    }

    @Test
    public void nextDrawerFindsADrawer() {
        DrawToken token = host.evaluateNextDrawer();
        NetClient client = host.find(token.getParticipant());
        assertEquals(client.getClient(), token.getParticipant());
    }

    @Test
    public void evaluateNextDrawerTest() throws InterruptedException {
        final List<Encodable> received = new ArrayList<>();
        NetworkCallback callback = new NetworkCallback() {
            @Override
            public void onObjectReceived(Encodable obj) {
                received.add(obj);
            }

            @Override
            public void onConnectionLost(Exception e) {
                e.printStackTrace();
            }
        };
        for (NetworkHandler client : clients) {
            client.setCallback(callback);
        }
        DrawToken token = host.evaluateNextDrawer();
        host.send(token);
        Thread.sleep(500);
        assertTrue(received.contains(token));
    }
}
