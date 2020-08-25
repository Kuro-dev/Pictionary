package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.overlay.factory.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIManager {

    static ArrayList<Participant> participant_list;
    private static GUIBody instance;

    public static void instantiate(String name) {

        if (instance != null) instance.close();
        instance = new GUIBody(name);

        participant_list = new ArrayList<>();

        addParticipant("Hey");
        addParticipant("May");
        addParticipant("ugtfdsadi");
        addParticipant("JGfkhgsv");
        addParticipant("sdugvbksjd");

    }

    public static void addParticipant(String name) {

        if (name.length() > 9) name = name.substring(0, 9);

        Participant participant = new Participant(name, (int) (2000 * Math.random()));
        participant_list.add(participant);

        JLabel lab = new JLabel(participant_list.size() + ": ");
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(participant_list.size() - 1).setGridx(0).setAnchor(GBC.WEST).setInsets(new Insets(5, 20, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel(participant.getName());
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(participant_list.size() - 1).setGridx(1).setAnchor(GBC.WEST).setWeightx(1).setInsets(new Insets(5, 0, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel("" + participant.score);
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(participant_list.size() - 1).setGridx(2).setAnchor(GBC.EAST).setInsets(new Insets(5, 5, 5, 20)));
        instance.pan_lft_mid.add(lab);

//        instance.pan_lft_mid.repaint();
        instance.frame.pack();

    }


    public static void sendChat(String name, String text) {
        //TODO reimplement that
    }

    public static void updateScore(String name, int i) {
        //TODO reimplement that
    }
}
