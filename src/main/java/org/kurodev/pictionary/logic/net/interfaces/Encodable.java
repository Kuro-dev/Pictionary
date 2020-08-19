package org.kurodev.pictionary.logic.net.interfaces;

/**
 * @author kuro
 **/
public interface Encodable {
    default String delimiter() {
        return new String(new char[]{(char) 0x6, (char) 0x10, (char) 0x15});
    }

    void decode(byte[] bytes);

    byte[] encode();
}
