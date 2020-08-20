package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author kuro
 **/
public enum Code {
    PIXEL(Pixel.class),
    IMAGE(Image.class),
    GAME(Pictionary.class);

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
        int o = Byte.toUnsignedInt(aByte);
        for (Code value : Code.values()) {
            if (value.ordinal() == o) {
                return value;
            }
        }
        throw new RuntimeException("Code not found: " + o);
    }

    public Encodable construct(byte[] encoded) {
        EasyByteStream stream = new EasyByteStream(encoded);
        try {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 1 && types[0] == byte[].class) {
                    //System.out.printf("Invoking byte constructor for %s.class\n", clazz.getSimpleName());
                    return (Encodable) constructor.newInstance(stream);
                }
            }
            for (Constructor<?> constructor : clazz.getConstructors()) {
                Class<?>[] types = constructor.getParameterTypes();
                if (types.length == 0) {
                    //System.out.printf("Invoking default constructor for %s.class\n", clazz.getSimpleName());
                    Encodable e = (Encodable) constructor.newInstance();
                    e.decode(stream);
                    return e;
                }
            }
            throw new RuntimeException("Could not decode " + Arrays.toString(encoded));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
