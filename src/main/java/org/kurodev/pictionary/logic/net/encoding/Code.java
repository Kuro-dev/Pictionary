package org.kurodev.pictionary.logic.net.encoding;

import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.communication.command.Command;
import org.kurodev.pictionary.logic.net.communication.command.tokens.*;
import org.kurodev.pictionary.logic.net.encoding.stream.EasyByteReader;
import org.kurodev.pictionary.overlay.util.MessageEncodable;

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
    COMMAND(Command.class),
    MESSAGE(MessageEncodable.class),
    PARTICIPANT(Participant.class),
    //TOKENS,
    DRAW_TOKEN(DrawToken.class),
    TIMEOUT_TOKEN(TimeOutToken.class),
    CORRECT_GUESS(CorrectGuessToken.class),
    TIME_TOKEN(TimeToken.class),
    WORD_SELECT(SelectWordToken.class),
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
            throw new EncodingException(String.format("No Suitable Constructor found for %s(%s):\n%s", this.name(), this.clazz.getSimpleName(), Arrays.toString(encoded)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new EncodingException(e);
        }
    }
}