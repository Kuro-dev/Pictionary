package org.kurodev.pictionary.logic.net.communication;

import org.junit.Test;
import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SessionTest {

    @Test
    public void hostJoinTest() throws IOException {
        int playerCount = 3;
        final int port = 9000;
        //Setup callback class
        VoidCallback hostCallback = new VoidCallback("host");
        List<VoidCallback> clientCallbacks = new ArrayList<>();
        //create host session
        HostSession session = Session.host("Kuro", port, hostCallback);
        //open session for the desired playerCount
        session.open(playerCount);
        List<NetworkHandler> clients = new ArrayList<>(playerCount);
        //players join
        for (int i = 0; i < playerCount; i++) {
            VoidCallback callback = new VoidCallback("player " + i);
            clientCallbacks.add(callback);
            clients.add(Session.join(new Participant("player" + i), callback, "localhost", port));
        }
        //wait for the connections
        if (!session.awaitConnections(3, TimeUnit.SECONDS)) {
            fail("Timeout");
        }
        //start the game
        session.start();
        //clients send some data to the host
        for (NetworkHandler client : clients) {
            int randomData = client.hashCode();
            client.send(new Pixel(randomData, randomData, randomData));
        }
        Encodable en = new Pictionary("HelloWorld!", 50);
        //host sends some data to the clients
        session.send(en);
        //open new thread to simulate different devices
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException("will never be thrown");
            }
            //check if the host received ALL the data sent from each client
            for (NetworkHandler client : clients) {
                int randomData = client.hashCode();
                assertTrue(hostCallback.getEncodables().contains(new Pixel(randomData, randomData, randomData)));
            }
            //check if each client received the data sent by the Host
            for (VoidCallback clientCallback : clientCallbacks) {
                assertTrue(clientCallback.getEncodables().contains(en));
            }
        }).start();
    }

}