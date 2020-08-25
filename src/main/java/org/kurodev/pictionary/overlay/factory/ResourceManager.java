package org.kurodev.pictionary.overlay.factory;

import javax.swing.*;
import java.net.URL;

public class ResourceManager {
    private static final int WIDTH = 200, HEIGHT = 200;
    private static final URL IMG = ResourceManager.class.getResource("/img/gameIcon.jpg");
    //TODO, Please make resizable to the given width and height up above.
    public static Icon getIcon() {
        return new ImageIcon(IMG);
    }

}
