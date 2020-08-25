package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.img.Pixel;
import org.kurodev.pictionary.overlay.util.EncodableSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class DrawEventHandler implements MouseMotionListener, MouseWheelListener {

    GUIBody root;
    EncodableSender sender = null;

    Graphics g;

    int brush_size = 20;
//    Color brush_color = Color.BLACK;

    BufferedImage image = new BufferedImage(GUIBody.HEIGHT / 2, GUIBody.HEIGHT / 2, BufferedImage.TYPE_INT_ARGB);

    public DrawEventHandler(GUIBody guiBody) {
        root = guiBody;
        g = image.getGraphics();

        for (int i = 0; i < image.getWidth(); i++)
            for (int j = 0; j < image.getHeight(); j++) image.setRGB(i, j, Color.WHITE.getRGB());

        g.setColor(Color.BLACK);
        root.drawing_pane.setIcon(new ImageIcon(image));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        drawPoint(e.getX(), e.getY(), brush_size);
        if (sender != null) sender.send(new Pixel(e.getX(), e.getY(), brush_size, g.getColor().getRGB()));
    }

    public void drawPoint(int x, int y, int size) {
        g.fillOval(x - 13 - size / 2, y - 5 - size / 2, size / 2, size / 2);
        root.drawing_pane.setIcon(new ImageIcon(image));
    }

    public void setColor(Color col) {
        g.setColor(col);
    }

    public void setSessionToRespond(EncodableSender s) {
        if (s == null) throw new NullPointerException("You cant just pass on a null session object.");
        sender = s;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        brush_size += e.getPreciseWheelRotation();
        brush_size = Math.max(10, Math.min(brush_size, 50));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
