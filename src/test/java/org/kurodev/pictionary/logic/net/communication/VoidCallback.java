package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.callbacks.NetworkCallback;
import org.kurodev.pictionary.logic.net.encoding.Encodable;

/**
 * @author kuro
 **/
public class VoidCallback implements NetworkCallback {


    private String s;

    public VoidCallback(String s) {
        this.s = s;
    }

    @Override
    public void onObjectReceived(Encodable obj) {
        System.out.println(s + ":" + obj);
    }

    @Override
    public void onConnectionLost(Exception e) {
        e.printStackTrace();
    }
}
