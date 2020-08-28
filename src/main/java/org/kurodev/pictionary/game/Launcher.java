package org.kurodev.pictionary.game;

import org.kurodev.pictionary.game.util.*;
import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.communication.HostSession;
import org.kurodev.pictionary.logic.net.communication.NetClient;
import org.kurodev.pictionary.logic.net.communication.NetworkHandler;
import org.kurodev.pictionary.logic.net.communication.Session;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.timer.Countdown;
import org.kurodev.pictionary.overlay.GUIManager;
import org.kurodev.pictionary.overlay.util.MessageEncodable;
import org.kurodev.pictionary.overlay.util.PackageClient;
import org.kurodev.pictionary.overlay.util.PackageHost;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Launcher {

    Countdown counter;

    PackageHost host_pack;
    HostSession session;
    NetworkHandler client;

    NetClient drawer;

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
        host_pack = GUIManager.getHostPackage();
        GUIManager.setGame_callback(new host_callback());
        session = Session.host(GUIManager.myself.getName(), host_pack.getPort(), GUIManager.getMyCallback());

        session.open(players);
        if (!session.awaitConnections(30, TimeUnit.SECONDS)) throw new RuntimeException("Session Timed Out!");
        session.start();

        session.send(GUIManager.myself);
        GUIManager.setNetHandler(session::send);


        // STARTING GAME
        drawer = session.evaluateNextDrawer();
//             Pick 3 from wordlist and send it to drawer
//            drawer.getHandler().send();
        drawer.getHandler().send(new Trio("Apple", "Bapple", "Capple"));

    }

    class host_callback implements NetworkCallback {

        String word = "";
        int rema = 0;

        @Override
        public void onObjectReceived(Encodable obj) {

            if (obj instanceof Choice) {
                Choice choice = (Choice) obj;
                word = choice.getWord();
                session.send(new Starter("_".repeat(choice.getWord().length())));
                counter.setCallback(remaining -> {

                    if (remaining <= 1) {
                        session.send(new Score(drawer.getClient().getName(), -host_pack.getTime() / 3));
                    }

                    if (host_pack.getTime() * 2 / 3 == remaining) {
                        System.out.println("two third");
                        int sp = (int) (Math.random() + choice.getWord().length());
                        session.send(new Starter(choice.getWord().substring(0, sp) + '_' + choice.getWord().substring(sp + 1)));
                    }
                    session.send(new Time(remaining));
                    rema = remaining;
                });

            }
            if (obj instanceof MessageEncodable) {
                MessageEncodable e = (MessageEncodable) obj;
                if (e.getMessage().equalsIgnoreCase(word)) {
                    session.send(new Score(e.getName(), rema));
                    session.send(new Score(drawer.getClient().getName(), rema / 2));
                    counter.reset();
                    drawer = session.evaluateNextDrawer();
                }
            }

        }

        @Override
        public void onConnectionLost(Exception e) {
            e.printStackTrace();
        }
    }

    public void join_game() {
        int players = 1;

        PackageClient pack = GUIManager.getClientPackage();
        GUIManager.setGame_callback(new join_callback());

        try {
            client = Session.join(GUIManager.myself, GUIManager.getMyCallback(), pack.getIp(), pack.getPort());
            GUIManager.setNetHandler(client::send);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class join_callback implements NetworkCallback {


        @Override
        public void onObjectReceived(Encodable obj) {

            if (obj instanceof Trio) {
                // Get the user to choose from one of the words.
                // and the button action to send the word.
                // till then
                client.send(new Choice(((Trio) obj).get(0)));
                GUIManager.setDrawing(true);

            } else if (obj instanceof Starter) {
                GUIManager.setHint(((Starter) obj).getHint());

            } else if (obj instanceof Time) {
                GUIManager.setTime(((Time) obj).getTime());

            } else if (obj instanceof Score) {
                Score sc = (Score) obj;
                GUIManager.setScore(sc.getName(), sc.getScore());
                GUIManager.setDrawing(false);

            }

        }

        @Override
        public void onConnectionLost(Exception e) {
            e.printStackTrace();
        }
    }

}
