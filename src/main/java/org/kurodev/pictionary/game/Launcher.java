package org.kurodev.pictionary.game;

import org.kurodev.pictionary.logic.net.communication.HostSession;
import org.kurodev.pictionary.logic.net.communication.NetClient;
import org.kurodev.pictionary.logic.net.communication.NetworkHandler;
import org.kurodev.pictionary.logic.net.communication.Session;
import org.kurodev.pictionary.logic.timer.Countdown;
import org.kurodev.pictionary.overlay.GUIManager;
import org.kurodev.pictionary.overlay.util.PackageClient;
import org.kurodev.pictionary.overlay.util.PackageHost;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.concurrent.TimeUnit;

public class Launcher {

    Countdown counter;

    public Launcher() {

        GUIManager.instantiate(this::host_game, this::join_game);

    }
/*

     A possible idea:
      At the host's place:
        When session starts (ie has collected all players)
        it will make a list of all participants (which already exists)
        and start the game (ie allowed top play)
        when timer runs out || everyone has guessed right
          let the very next player to host start the next round.
          and at host's side, drawing is disabled.
        Let host system manage whose turn is next, ie,
        the host will manage each time the round finishes.
      At the client's place:
        It waits for it's turn to come, until which drawing is disabled.
*/

    public void host_game() {

        // NETWORK INIT: HOST (Action#Event)
        int players = 1;
        PackageHost pack = GUIManager.getHostPackage();
        HostSession session = Session.host(GUIManager.myself.getName(), pack.getPort(), GUIManager.getMyCallback());

        session.open(players);
        if (!session.awaitConnections(30, TimeUnit.SECONDS)) throw new RuntimeException("Session Timed Out!");
        session.start();

        session.send(GUIManager.myself);
        GUIManager.setNetHandler(session::send);


        // STARTING GAME
        NetClient drawer = session.evaluateNextDrawer();
        counter = new Countdown(pack.getTime(), remaining -> {

//             Pick 3 from wordlist and send it to drawer
//            drawer.getHandler().send();

        });

        /*
        GUIManager.updateScore("May", 100);
        GUIManager.setTime(4, 5);
        */

    }

    public void join_game() {
        int players = 1;

        PackageClient pack = GUIManager.getClientPackage();

        try {
            NetworkHandler client = Session.join(GUIManager.myself, GUIManager.getMyCallback(), pack.getIp(), pack.getPort());
            GUIManager.setNetHandler(client::send);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void tick(int time) {

    }

}
