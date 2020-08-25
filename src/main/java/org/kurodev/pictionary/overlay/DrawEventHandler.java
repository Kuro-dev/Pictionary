package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.overlay.GUIBody;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class DrawEventHandler implements MouseMotionListener, MouseWheelListener {

    GUIBody root;

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
        int x = e.getX();
        int y = e.getY();
        drawPoint(e.getX(), e.getY(), brush_size);
    }

    public void drawPoint(int x, int y, int size) {
        g.fillOval(x - 13 - size / 2, y - 5 - size / 2, size / 2, size / 2);
        root.drawing_pane.setIcon(new ImageIcon(image));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        brush_size += e.getPreciseWheelRotation();
        brush_size = Math.max(10, Math.min(brush_size, 50));
    }

    public void setColor(Color col) {
        g.setColor(col);
    }
}
