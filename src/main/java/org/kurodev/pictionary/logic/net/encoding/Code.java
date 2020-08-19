package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.timer.Countdown;

/**
 * @author kuro
 **/
public enum Code {
    PIXEL(Pixel.class),
    COUNTDOWN(Countdown.class);

    private final Class<? extends Encodable> clazz;

    Code(Class<? extends Encodable> clazz) {
        this.clazz = clazz;
    }

    public static Code get(Encodable obj) {
        for (Code value : Code.values()) {
            if (obj != null) {
                if (obj.getClass() == value.clazz) {
                    return value;
                }
            }
        }
        throw new EncodingException("Couldn't identify " + obj);
    }
}
