package org.kurodev.pictionary.overlay.factory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ResourceManager {
    private static final int WIDTH = 200;

    public static Icon getIcon() {

        BufferedImage image;
        try {

            image = ImageIO.read(ResourceManager.class.getResource("/img/gameIcon.jpg"));

            return new ImageIcon(image.getScaledInstance(WIDTH, image.getHeight()*WIDTH/image.getHeight(), Image.SCALE_SMOOTH));

        } catch (IOException e) {

            System.out.println(e.getMessage());
            image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

            return new ImageIcon(image);
        }

    }

}
