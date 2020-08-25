package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.communication.*;
import org.kurodev.pictionary.overlay.uitl.PackageHost;

import java.util.ArrayList;
import java.util.List;

public class GUI_Host_Test {

    public GUI_Host_Test() {
        GUIManager.instantiate(this::host_game, this::join_game);
    }

    public void host_game() {
        int players = 2;
        PackageHost pack = GUIManager.getHostPackage();

        HostSession session = Session.host(GUIManager.myself.getName(), pack.getPort(), GUIManager.getMyCallback());

        session.open(players);

        session.send(GUIManager.myself);

        GUIManager.setOnDrawEvent(session);

    }

    public void join_game() {

    }

    public static void main(String[] args) {
        new GUI_Host_Test();
    }
}
