package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.util.Participant;
import org.kurodev.pictionary.overlay.factory.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIManager {

    private static GUIBody instance;

    static ArrayList<Participant> participant_list;

    public static void instantiate(String name) {

        if (instance != null) instance.close();
        instance = new GUIBody(name);

        participant_list = new ArrayList<>();

        addParticipant(name);

    }

    static int index = 0;

    public static void addParticipant(String name) {
        Participant participant = new Participant(name, 0);
        addParticipant(participant);
        participant_list.add(participant);
    }

    public static void addParticipant(Participant participant) {

        JLabel lab = new JLabel(index + 1 + ": ");
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(0).setAnchor(GBC.WEST).setInsets(new Insets(5, 20, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel(participant.getName());
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(1).setAnchor(GBC.WEST).setWeightx(1).setInsets(new Insets(5, 0, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel("" + participant.getScore());
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(2).setAnchor(GBC.EAST).setInsets(new Insets(5, 5, 5, 20)));
        instance.pan_lft_mid.add(lab);

//        instance.pan_lft_mid.repaint();
        instance.frame.pack();
        index++;

    }

    public static void updateScore(String name, int score) {
        for (int i = 0; i < participant_list.size(); i++)
            if (participant_list.get(i).getName().equals(name))
                participant_list.get(i).setScore(score);

        instance.pan_lft_mid.removeAll();

        index = 0;
        for (int i = 0; i < participant_list.size(); i++) {
            addParticipant(participant_list.get(i));
        }
    }

    static String chat_text = "";

    public static void sendChat(String name, String message) {

        participant_list.stream().filter(participant -> participant.getName().equals(name)).findFirst().ifPresent(participant -> {
            chat_text += "<BR /><FONT color=\"" + participant.getColour() + "\">" + name + ": " + message + "</FONT>";
            instance.txt_scp_rht_mid.setText("<HTML><BODY>" + chat_text + "</HTML></BODY>");
        });

    }

    public static void setTime(int min, int sec) {

        instance.lbl_timer_lft_mid.setText(((min + "").length() == 1 ? "0" : "") + min + ":" + ((sec + "").length() == 1 ? "0" : "") + sec);
    }

}
