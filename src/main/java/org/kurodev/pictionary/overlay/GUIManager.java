package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.NetHandler;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.communication.command.tokens.DrawToken;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.overlay.factory.GBC;
import org.kurodev.pictionary.overlay.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GUIManager {

    static GUIGetInfo info;
    private static GUIBody instance;
    public static Participant myself;
    static ArrayList<Participant> participant_list = new ArrayList<>();
    static NetworkCallback game_callback = null;

    static NetHandler message_sender = null;

    public static void instantiate(Runnable if_hosted, Runnable if_joined) {

        info = new GUIGetInfo();

        info.but_start_host.addActionListener(e -> if_hosted.run());
        info.but_start_join.addActionListener(e -> if_joined.run());
    }

    public static PackageHost getHostPackage() {
        info.dispose();

        myself = new Participant(info.fie_name.getText(), info.but_choose_colour.getBackground().getRGB(), 0);
        instance = new GUIBody(myself.getName());
        addParticipant(myself);
        return new PackageHost(((SpinnerMinuteModel) info.spin_time.getModel()).getTimeAsInt(), info.fie_customwords.getText().split(" "), Integer.parseInt(info.fie_port_h.getText()));
    }

    public static PackageClient getClientPackage() {
        info.dispose();

        myself = new Participant(info.fie_name.getText(), info.but_choose_colour.getBackground().getRGB(), 0);
        instance = new GUIBody(myself.getName());
        addParticipant(myself);
        return new PackageClient(info.fie_ip.getText(), Integer.parseInt(info.fie_port_j.getText()));
    }

    public static NetworkCallback getMyCallback() {

        return new NetworkCallback() {
            @Override
            public void onObjectReceived(Encodable obj) {

                if (obj instanceof Pixel) {
                    Pixel pixel = (Pixel) obj;
                    instance.draw_event_handle.setColor(new Color(pixel.getArgb()));
                    instance.draw_event_handle.drawPoint(pixel.getX(), pixel.getY(), pixel.getRadius());

                } else if (obj instanceof Participant) {
                    Participant p = (Participant) obj;
                    addParticipant(p);

                } else if (obj instanceof MessageEncodable) {
                    MessageEncodable msg = (MessageEncodable) obj;
                    sendChat(msg.getName(), msg.getMessage());

                } else if (obj instanceof DrawToken) {
                    instance.draw_event_handle.setEnabled(true);

                }

                if (game_callback != null) game_callback.onObjectReceived(obj);

            }

            @Override
            public void onConnectionLost(Exception e) {
                e.printStackTrace();
            }
        };

    }

    public static void setNetHandler(NetHandler sender) {
        instance.draw_event_handle.setSessionToRespond(sender);
        message_sender = sender;
    }

    static int index = 0;

    public static void addParticipant(Participant participant) {

        participant_list.add(participant);
        JLabel lab = new JLabel(index + 1 + ": ");
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(0).setAnchor(GBC.WEST).setInsets(new Insets(5, 20, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel(participant.getName());
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(1).setAnchor(GBC.WEST).setWeightx(1).setInsets(new Insets(5, 0, 5, 5)));
        instance.pan_lft_mid.add(lab);

        lab = new JLabel("" + participant.getScore());
        instance.lay_pan_lft_mid.setConstraints(lab, new GBC().setGridy(index).setGridx(2).setAnchor(GBC.EAST).setInsets(new Insets(5, 5, 5, 20)));
        instance.pan_lft_mid.add(lab);

        instance.frame.pack();
        index++;

    }

    public static void setScore(String name, int score) {
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

//        System.out.println(name + " " + message);


        if (message_sender != null && name.equals(myself.getName())) {
            message_sender.send(new MessageEncodable(name, message));
            System.out.println("sent: " + name + " " + message);
        }

        participant_list.stream().filter(participant -> participant.getName().equals(name)).findFirst().ifPresent(participant -> {
            chat_text += "<BR /><FONT face='Calibri' color=\"#" + Integer.toHexString(participant.getColour()) + "\"><B>" + name + ":</B> " + message + "</FONT>";
            instance.txt_scp_rht_mid.setText("<HTML><BODY>" + chat_text + "</HTML></BODY>");
        });

    }

    public static void setTime(int min, int sec) {
        instance.lbl_timer_lft_mid.setText(((min + "").length() == 1 ? "0" : "") + min + ":" + ((sec + "").length() == 1 ? "0" : "") + sec);
    }

    public static void setGame_callback(NetworkCallback game_callback) {
        GUIManager.game_callback = game_callback;
    }

    public static void setHint(String hint) {
        instance.lbl_hint_top.setText(hint);
    }

    public static void setTime(int time) {
        setTime(time / 60, time % 60);
    }

    public static void setDrawing(boolean mark) {
        instance.draw_event_handle.setEnabled(mark);
    }

}
