package org.kurodev.pictionary.overlay.util;

import org.kurodev.pictionary.logic.net.encoding.Encodable;

@FunctionalInterface
public interface EncodableSender {
    void send(Encodable encodable);
}
