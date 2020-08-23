package org.kurodev.pictionary.overlay.factory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ResourceManager {

    public static Icon getIcon() {

        BufferedImage image = null;

        // Attempt to read image using InageIO.read from resources.

        if (image == null) {
            image = new BufferedImage(150, 60, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < 150; i++) {
                for (int j = 0; j < 60; j++) {
                    int c = (int) (Math.random() * 255);
                    image.setRGB(i, j, new Color(c, c, c).getRGB());
                }
            }
        }

        return new ImageIcon(image);

    }

}
