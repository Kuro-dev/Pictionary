package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.overlay.factory.GBC;
import org.kurodev.pictionary.overlay.util.EncodableSender;
import org.kurodev.pictionary.overlay.util.PackageClient;
import org.kurodev.pictionary.overlay.util.PackageHost;
import org.kurodev.pictionary.overlay.util.SpinnerMinuteModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIManager {

    static GUIGetInfo info;
    private static GUIBody instance;
    static Participant myself;
    static ArrayList<Participant> participant_list = new ArrayList<>();

    public static void instantiate(Runnable if_hosted, Runnable if_joined) {

        info = new GUIGetInfo();

        info.but_start_host.addActionListener(e -> if_hosted.run());
        info.but_start_join.addActionListener(e -> if_joined.run());
    }

    public static PackageHost getHostPackage() {
        myself = new Participant(info.fie_name.getText(), Integer.toHexString(info.but_choose_colour.getBackground().hashCode()).substring(0, 6), 0);
        instance = new GUIBody(myself.getName());
        addParticipant(myself);
        return new PackageHost(((SpinnerMinuteModel) info.spin_time.getModel()).getTimeAsInt(), info.fie_customwords.getText().split(" "), Integer.parseInt(info.fie_port_h.getText()));
    }

    public static PackageClient getClientPackage() {
        myself = new Participant(info.fie_name.getText(), "" + info.but_choose_colour.getBackground().getRGB(), 0);
        instance = new GUIBody(myself.getName());
        addParticipant(myself);
        return new PackageClient(info.fie_ip.getText(), Integer.parseInt(info.fie_port_j.getText()));
    }

    public static NetworkCallback getMyCallback() {

        return new NetworkCallback() {
            @Override
            public void onObjectReceived(Encodable obj) {
                System.out.println("object received");
                if (obj instanceof Pixel) {
                    Pixel pixel = (Pixel) obj;
                    System.out.println("pixel received" + pixel.toString());
                    instance.draw_event_handle.setColor(new Color(pixel.getArgb()));
                    System.out.println(new Color(pixel.getArgb()).getRed() + " " + new Color(pixel.getArgb()).getGreen() + " " + new Color(pixel.getArgb()).getBlue());
                    instance.draw_event_handle.drawPoint(pixel.getX(), pixel.getY(), pixel.getRadius());

                } else if (obj instanceof Participant) {
                    Participant p = (Participant) obj;
                    addParticipant(p);
                }
            }

            @Override
            public void onConnectionLost(Exception e) {
                e.printStackTrace();
            }
        };

    }

    public static void setOnDrawEvent(EncodableSender sender) {
        instance.draw_event_handle.setSessionToRespond(sender);
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
            System.out.println(Integer.toHexString(new Color(Integer.parseInt(participant.getColour())).hashCode()));
            chat_text += "<BR /><FONT face='Calibri' color=\"#" + Integer.toHexString(new Color(Integer.parseInt(participant.getColour())).hashCode()) + "\"><B>" + name + ":</B> " + message + "</FONT>";
            instance.txt_scp_rht_mid.setText("<HTML><BODY>" + chat_text + "</HTML></BODY>");
        });

    }

    public static void setTime(int min, int sec) {
        instance.lbl_timer_lft_mid.setText(((min + "").length() == 1 ? "0" : "") + min + ":" + ((sec + "").length() == 1 ? "0" : "") + sec);
    }

}
