package org.kurodev.pictionary.logic.net.communication;

import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteWriter;

/**
 * @author kuro
 **/
public class Client implements Encodable {
    private final String username;

    public Client(String username) {

        this.username = username;
    }

    @Override
    public void decode(EasyByteReader data) {

    }

    @Override
    public void encode(EasyByteWriter out) {

    }
}
