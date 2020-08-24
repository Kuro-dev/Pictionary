package org.kurodev.pictionary.logic.net.communication;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionTest {

    @Test
    public void hostJoinTest() throws IOException {
        int playerCount = 3;
        final int port = 9000;
        HostSession session = Session.host("Kuro", port, new VoidCallback("host"));
        session.open(playerCount);
        List<NetworkHandler> clients = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            Session.join(new Participant("player" + i), new VoidCallback("player " + i), "localhost", port);
        }
        if (!session.awaitConnections(3, TimeUnit.SECONDS)) {
            Assert.fail("Timeout");
        }

    }

}