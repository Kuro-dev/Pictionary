package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.net.communication.*;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
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

    /**
     * The GUIM calls this method if
     * the player chooses to host the game.
     */
    public void host_game() {

        /*
         * No. of clients expected to connect.
         * I guess it's some what of a lack of
         * functionality.
         *
         * Let the idea stated in next line
         * be called:
         * PATCH Undefined Player Count (UPC)
         * If I add another screen to let host
         * wait for participants and then
         * choose to join the game, will that
         * be better?
         */
        int players = 1;

        /*
         * When this method is called, GUIM
         * prepares a package of info
         * regarding port, and stuff.
         * Use this method to get that package.
         * */
        PackageHost pack = GUIManager.getHostPackage();

        /*
         * YKW it is.
         * GUIManager.myself refers to the
         * Participant object regarding the
         * user
         * */
        HostSession session = Session.host(GUIManager.myself.getName(), pack.getPort(), GUIManager.getMyCallback());

        /*
         * According to PATCH UPC
         * These two statements are redundant.
         * We shall require:
         * session.open();
         * to start waiting for players to connect.
         * And a method in HostSession:
         * session.start()
         * to stop scanning for players.
         * This method shall be used in one
         * of my callbacks.
         * And do what it does normally.
         * */
        session.open(players);
        if (!session.awaitConnections(30, TimeUnit.SECONDS)) fail("Timeout");
        session.start();

        /*
         * Updating connected clients of
         * our (the user's) presence.
         * */
        session.send(GUIManager.myself);

        /*
         * Setting up callbacks for GUIM
         * to act accordingly when something
         * is draw or a message is sent.
         * */
        GUIManager.setNetHandler(new NetHandler() {
            @Override
            public void send(Encodable obj) {
                session.send(obj);
            }

            @Override
            public void disconnect() {
                System.out.println("Bruh");
            }
        });

        GUIManager.sendChat("a", "A");
        GUIManager.sendChat("b", "B");

        // TODO
        // This basically applies to when actual game logic is created
        /*
        GUIManager.updateScore("May", 100);
        GUIManager.setTime(4, 5);
        */

    }

    /**
     * The GUIM calls this method if
     * the player chooses to join a game.
     */
    public void join_game() {
        int players = 1;

        /*
         * Similar to what happened in
         * host_game();
         * This method returns the package
         * created for client side.
         * */
        PackageClient pack = GUIManager.getClientPackage();

        try {
            NetworkHandler client = Session.join(GUIManager.myself, GUIManager.getMyCallback(), pack.getIp(), pack.getPort());
            GUIManager.setNetHandler(new NetHandler() {
                @Override
                public void send(Encodable obj) {
                    client.send(obj);
                }

                @Override
                public void disconnect() {
                    System.out.println("Bruh");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new GUI_Test();
    }
}
