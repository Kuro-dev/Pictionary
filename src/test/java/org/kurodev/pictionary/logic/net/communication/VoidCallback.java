package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
public class VoidCallback implements NetworkCallback {
    private final List<Encodable> encodables = new ArrayList<>();
    private String s;

    public VoidCallback(String s) {
        this.s = s;
    }

    public List<Encodable> getEncodables() {
        return encodables;
    }

    @Override
    public void onObjectReceived(Encodable obj) {
        System.out.println(s + " received: " + obj);
        encodables.add(obj);
    }

    @Override
    public void onConnectionLost(Exception e) {
        e.printStackTrace();
    }
}
