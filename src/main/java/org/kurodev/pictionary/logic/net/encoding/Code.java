package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.logic.net.stream.Message;
import org.kurodev.pictionary.logic.net.communication.Participant;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author kuro
 **/
public enum Code {
    PIXEL(Pixel.class),
    IMAGE(Image.class),
    GAME(Pictionary.class),
    MESSAGE(Message.class),
    PARTICIPANT(Participant.class),
    ;

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

    public static Code get(byte aByte) {
        int searched = Byte.toUnsignedInt(aByte);
        for (Code value : Code.values()) {
            if (value.ordinal() == searched) {
                return value;
            }
        }
        throw new EncodingException("Code not found: " + searched);
    }

    public Encodable construct(byte[] encoded) {
        EasyByteReader stream = new EasyByteReader(encoded);
        try {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 1 && types[0] == EasyByteReader.class) {
                    return (Encodable) constructor.newInstance(stream);
                }
            }
            for (Constructor<?> constructor : clazz.getConstructors()) {
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 0) {
                    Encodable e = (Encodable) constructor.newInstance();
                    e.decode(stream);
                    return e;
                }
            }
            throw new EncodingException("Could not decode " + Arrays.toString(encoded));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EncodingException(e);
        }
    }
}