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
    NetClient drawer;

    NetworkHandler client;

    /**
     * The main class which also initialises GUIManager
     * <p>
     * This starts the first dialog system where the user
     * is prompted for name and stuff.
     * <p>
     * It also adds events to correspond to when the
     * user attempts to either join a game, or hosts it.
     */
    public Launcher() {
        GUIManager.instantiate(this::host_game, this::join_game);
    }


    /**
     * The method is PART CONSTRUCTOR
     * It is called when user wants to host a game.
     * <p>
     * This method is called after the user has
     * provided info like name, colour, time,
     * extra words and port.
     */
    private void host_game() {

        // YKW
        int players = 1;
        // Getting info regarding game time, extra words and port.
        host_pack = GUIManager.getHostPackage();
        // Adding callback to act on when objects are received.
        GUIManager.setGame_callback(new host_callback());
        /*
         * Initializing a HOST session. parts:
         * 1 > GUIManager.myself.getName()
         *       GUIManager.myself is the Participant object
         *       regarding the user.
         * 2 > host_pack.getPort()
         *       host_pack is as explained above.
         * 3 > GUIManager.getMyCallback()
         *       here we give the Session a callback for gui
         *       so that GUI system can act when objects
         *       are received.
         * */
        session = Session.host(GUIManager.myself.getName(), host_pack.getPort(), GUIManager.getMyCallback());

        // YKW
        session.open(players);
        if (!session.awaitConnections(30, TimeUnit.SECONDS)) throw new RuntimeException("Session Timed Out!");
        session.start();

        session.send(GUIManager.myself);
        GUIManager.setNetHandler(session);


        // STARTING GAME
        drawer = session.evaluateNextDrawer();
//             Pick 3 from wordlist and send it to drawer
//            drawer.getHandler().send();
        drawer.getHandler().send(new IGiveYouThreeWordsToChooseFrom("Apple", "Bapple", "Capple"));

    }

    class host_callback implements NetworkCallback {

        // These two variables are to be able to use these values across different events
        // By different events i mean to when different objects are received.
        String word = "";
        int remainingtime = 0;

        @Override
        public void onObjectReceived(Encodable obj) {

            if (obj instanceof IGottaDrawThisWord) {
                // Receiving this object means, the drawer has selected a word and has started drawing.
                IGottaDrawThisWord choice = (IGottaDrawThisWord) obj;
                word = choice.getWord();

                // Sending a hint as a series of '_' the length of the word.
                session.send(new TakeThisAsTheHint("_".repeat(choice.getWord().length())));

                /*
                 * Setting up a server side counter to:
                 * 1 > Tell each client about each second pass.
                 *     Yup, I decided to keep track of time at server side.
                 *     If you want it to be changed, well, i'll do so.
                 * 2 > Give a new and more informative hint every third.
                 * */
                counter.setCallback(remaining -> {

                    /*
                     * Sending this object to tell that the drawer
                     * has failed to draw a drawing good enough for
                     * others to guess correctly in the given time.
                     *
                     * Note sending this object also means that the
                     * game has ended.
                     * */
                    if (remaining <= 1) {
                        session.send(new GameEndedAndHereIsTheScore(drawer.getClient().getName(), -host_pack.getTime() / 3));

                    } else {

                        // when two third time is remaining give a better hint, revealing one letter
                        if (host_pack.getTime() * 2 / 3 == remaining) {
                            System.out.println("two third");
                            int sp = (int) (Math.random() * word.length());
                            session.send(new TakeThisAsTheHint("_".repeat(sp) + word.charAt(sp) + "_".repeat(word.length() - sp - 1)));

                            // revealing 2 letters
                        } else if (host_pack.getTime() / 3 == remaining) {
                            System.out.println("one third");
                            int sp = (int) (Math.random() * choice.getWord().length());
                            String hint = "_".repeat(sp) + word.charAt(sp) + "_".repeat(word.length() - sp - 1);
                            sp = (int) (Math.random() * choice.getWord().length());
                            hint = "_".repeat(sp) + hint.charAt(sp) + "_".repeat(hint.length() - sp - 1);
                            session.send(new TakeThisAsTheHint(hint));
                        }

                        // Updating the clients of the time.
                        session.send(new Time(remaining));

                        remainingtime = remaining;
                    }
                });

                // checking if what the users are sending as messages is the correct word or not.
            } else if (obj instanceof MessageEncodable) {
                MessageEncodable e = (MessageEncodable) obj;
                if (e.getMessage().equalsIgnoreCase(word)) {
                    session.send(new GameEndedAndHereIsTheScore(e.getName(), remainingtime));
                    session.send(new GameEndedAndHereIsTheScore(drawer.getClient().getName(), remainingtime / 2));
                    counter.reset();
                    drawer = session.evaluateNextDrawer();
                    drawer.getHandler().send(new IGiveYouThreeWordsToChooseFrom("Apple", "Bapple", "Capple"));
                }
            }

        }

        @Override
        public void onConnectionLost(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method is PART CONSTRUCTOR
     * It is called when user wants to join a game.
     * <p>
     * This method is called after the user has
     * provided info like name, colour, ip and port
     */
    private void join_game() {

        // Getting the package with ip and port in it.
        PackageClient pack = GUIManager.getClientPackage();
        GUIManager.setGame_callback(new join_callback());

        try {
            client = Session.join(GUIManager.myself, GUIManager.getMyCallback(), pack.getIp(), pack.getPort());
            GUIManager.setNetHandler(client);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class join_callback implements NetworkCallback {

        @Override
        public void onObjectReceived(Encodable obj) {

            // Receiving this object mean that this client has been chosen to be the drawer
            if (obj instanceof IGiveYouThreeWordsToChooseFrom) {
                // Get the user to choose from one of the words.
                // and the button action to send the word.
                // till then
                client.send(new IGottaDrawThisWord(((IGiveYouThreeWordsToChooseFrom) obj).get(0)));
                GUIManager.setDrawing(true);

            } else if (obj instanceof TakeThisAsTheHint) {
                GUIManager.setHint(((TakeThisAsTheHint) obj).getHint());

            } else if (obj instanceof Time) {
                GUIManager.setTime(((Time) obj).getTime());

            } else if (obj instanceof GameEndedAndHereIsTheScore) {
                GameEndedAndHereIsTheScore sc = (GameEndedAndHereIsTheScore) obj;
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
