package org.kurodev.pictionary.logic.net.interfaces;

/**
 * @author kuro
 **/
public interface NetworkCallback {
    void onConnectionEstablished();
    void onConnectionLost(Exception e);

}
