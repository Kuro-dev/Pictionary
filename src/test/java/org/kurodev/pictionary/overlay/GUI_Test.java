package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.net.communication.*;
import org.kurodev.pictionary.overlay.util.EncodableSender;
import org.kurodev.pictionary.overlay.util.PackageClient;
import org.kurodev.pictionary.overlay.util.PackageHost;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class GUI_Test {

    public GUI_Test() {
        GUIManager.instantiate(this::host_game, this::join_game);
    }

    public void host_game() {
        int players = 1;
        PackageHost pack = GUIManager.getHostPackage();

        HostSession session = Session.host(GUIManager.myself.getName(), pack.getPort(), GUIManager.getMyCallback());

        session.open(players);

        if (!session.awaitConnections(30, TimeUnit.SECONDS)) {
            fail("Timeout");
        }

        session.start();

        EncodableSender sender = session::send;

        sender.send(GUIManager.myself);

        GUIManager.setOnDrawEvent(sender);
        GUIManager.setOnMessageEvent(sender);

        // TODO
        /*
        GUIManager.updateScore("May", 100);
        GUIManager.sendChat("May","Hello");
        GUIManager.setTime(4, 5);
        */

    }

    public void join_game() {
        int players = 1;
        PackageClient pack = GUIManager.getClientPackage();

        try {
            NetworkHandler client = Session.join(GUIManager.myself, GUIManager.getMyCallback(), pack.getIp(), pack.getPort());
            GUIManager.setOnDrawEvent(client::send);
            GUIManager.setOnMessageEvent(client::send);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new GUI_Test();
    }
}
