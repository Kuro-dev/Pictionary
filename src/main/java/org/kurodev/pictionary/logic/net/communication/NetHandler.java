package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.net.encoding.Encodable;

/**
 * @author kuro
 **/
public interface NetHandler {
    void send(Encodable obj);

    void disconnect();
}
