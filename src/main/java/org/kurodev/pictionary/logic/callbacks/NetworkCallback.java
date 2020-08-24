package org.kurodev.pictionary.logic.callbacks;

import org.kurodev.pictionary.logic.net.encoding.Encodable;

/**
 * @author kuro
 **/
public interface NetworkCallback {

    void onObjectReceived(Encodable obj);

    void onConnectionLost(Exception e);

}
