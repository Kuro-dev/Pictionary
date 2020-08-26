package org.kurodev.pictionary.logic.callbacks;

import org.kurodev.pictionary.logic.Pictionary;
import org.kurodev.pictionary.logic.img.Image;
import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.logic.net.communication.Participant;
import org.kurodev.pictionary.logic.net.encoding.Encodable;
import org.kurodev.pictionary.logic.net.stream.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kuro
 **/
public interface SimpleNetworkCallback extends NetworkCallback {
    @Override
    default void onObjectReceived(Encodable obj) {
        try {
            Method m = this.getClass().getDeclaredMethod("onObjectReceived", obj.getClass());
            m.invoke(this, obj.getClass().cast(obj));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    void onObjectReceived(Pixel obj);

    void onObjectReceived(Pictionary obj);

    void onObjectReceived(Participant obj);

    void onObjectReceived(Command obj);

    void onObjectReceived(Image obj);
}
