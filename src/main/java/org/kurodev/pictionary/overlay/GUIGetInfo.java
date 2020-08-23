package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.logic.net.NetworkHandler;
import org.kurodev.pictionary.logic.util.RandomColor;
import org.kurodev.pictionary.overlay.factory.GBC;
import org.kurodev.pictionary.overlay.factory.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIGetInfo {

    JFrame frame;
    GridBagLayout lay_frm;

    JLabel title = new JLabel();

    JPanel content_holder;

    JPanel pan_first;
    GridBagLayout lay_first;

    JLabel lab_name;
    JTextField fie_name;

    JLabel lab_colour;
    JButton but_choose_colour;

    JButton but_join;
    JButton but_host;

    JPanel pan_ijoin;
    GridBagLayout lay_ijoin;

    JLabel lab_ip;
    JTextField fie_ip;

    JLabel lab_port;
    JTextField fie_port;

    JButton but_start_join;

    JPanel pan_ihost;
    GridBagLayout lay_ihost;

    JLabel lab_time;
    JSpinner spin_time;

    JLabel lab_customwords;
    JTextArea fie_customwords;

    // JLabel lab_port;
    // JTextField fie_port;

    JButton but_start_host;

    public GUIGetInfo() {

        initialize();
        resize();
        makefinal();

        frame.setVisible(true);

    }

    private void initialize() {

        frame = new JFrame("TITLE");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(lay_frm = new GridBagLayout());
        frame.getContentPane().setBackground(Color.decode("#11489F"));

        title = new JLabel(ResourceManager.getIcon());

        content_holder = new JPanel();
        content_holder.setBackground(Color.decode("#66DDAA"));

        // FIRST

        {
            pan_first = new JPanel(lay_first = new GridBagLayout());
            pan_first.setBackground(Color.decode("#7FFFD7"));

            lab_name = new JLabel("Please enter your name:");
            fie_name = new JTextField(System.getProperty("user.name"));

            lab_colour = new JLabel("Please choose a display colour:");
            but_choose_colour = new JButton("Choose a colour");
            but_choose_colour.setBackground(Color.decode("#" + RandomColor.getHex()));
            {
                but_choose_colour.addActionListener(e -> {
                    // IMPORTANT: Dont remove this comment block yet.
                    /*
                    Color initialColor = but_choose_colour.getBackground();

                    final JColorChooser pane = new JColorChooser(initialColor != null?
                            initialColor : Color.white);

                    for (AbstractColorChooserPanel ccPanel : pane.getChooserPanels()) {
                        ccPanel.setColorTransparencySelectionEnabled(true);
                    }

                    JDialog dialog = JColorChooser.createDialog(frame, "Choose A Colour", true, pane, null, null);

                    dialog.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            Window w = (Window)e.getComponent();
                            w.dispose();
                        }
                    });

                    dialog.setForeground(Color.decode("#7FFFD7"));
                    for (Component component : dialog.getComponents()) {
                        component.setBackground(Color.decode("#7FFFD7"));
                    }

                    dialog.show();

                    but_choose_colour.setBackground(pane.getColor());*/
                    but_choose_colour.setBackground(JColorChooser.showDialog(frame, "Choose a colour!", but_choose_colour.getBackground()));
                });
            }

            but_join = new JButton("JOIN");
            but_join.addActionListener(e -> {
                content_holder.removeAll();
                content_holder.add(pan_ijoin);
                frame.pack();
            });

            but_host = new JButton("HOST");
            but_host.addActionListener(e -> {
                content_holder.removeAll();
                content_holder.add(pan_ihost);
                frame.pack();
            });

        }

        // IF JOIN

        {
            pan_ijoin = new JPanel(lay_ijoin = new GridBagLayout());
            pan_ijoin.setBackground(Color.decode("#7FFFD7"));

            lab_ip = new JLabel("Please enter an IP:");
            fie_ip = new JTextField();

            lab_port = new JLabel("Please enter a Port:");
            fie_port = new JTextField("" + NetworkHandler.DEFAULT_PORT);

            fie_ip.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                        if (fie_ip.getText().matches("[\\w.]+[:][\\w]+")) {
                            fie_port.setText(fie_ip.getText().substring(fie_ip.getText().indexOf(":") + 1));
                            fie_ip.setText(fie_ip.getText().substring(0, fie_ip.getText().indexOf(":")));
                        }
                }
            });

            but_start_join = new JButton("START");

            but_start_join.addActionListener(e -> {
                
            });

        }

        // IF HOST

        {

            pan_ihost = new JPanel(lay_ihost = new GridBagLayout());
            pan_ihost.setBackground(Color.decode("#7FFFD7"));

            lab_time = new JLabel("Set time given per turn to draw:");
            spin_time = new JSpinner(new SpinnerMinuteModel());

            lab_customwords = new JLabel("Optional: Add custom words to game.");
            fie_customwords = new JTextArea();
            fie_customwords.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                        frame.pack();
                }
            });

            // lab_port = new JLabel("Please enter a Port:");
            // fie_port = new JTextField();

            but_start_host = new JButton("START");

        }

    }

    private void resize() {

        lay_frm.setConstraints(title, new GBC().setInsets(new Insets(20, 10, 10, 10)));
        lay_frm.setConstraints(content_holder, new GBC().setFill(GBC.HORIZONTAL).setGridy(1).setInsets(new Insets(20, 10, 10, 10)));

        lay_first.setConstraints(lab_name, new GBC().setGridy(0).setGridwidth(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(10, 10, 5, 10)));
        lay_first.setConstraints(fie_name, new GBC().setGridy(1).setGridwidth(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 10, 15, 10)));
        lay_first.setConstraints(lab_colour, new GBC().setGridy(2).setGridwidth(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 10, 5, 10)));
        lay_first.setConstraints(but_choose_colour, new GBC().setGridy(3).setGridwidth(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 10, 15, 10)));
        lay_first.setConstraints(but_join, new GBC().setGridy(4).setGridx(0).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 10, 5, 5)).setWeightx(1));
        lay_first.setConstraints(but_host, new GBC().setGridy(4).setGridx(1).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 5, 5, 10)).setWeightx(1));

        lay_ijoin.setConstraints(lab_ip, new GBC().setGridy(0).setFill(GBC.HORIZONTAL).setInsets(new Insets(10, 20, 5, 20)));
        lay_ijoin.setConstraints(fie_ip, new GBC().setGridy(1).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 15, 20)));
        lay_ijoin.setConstraints(lab_port, new GBC().setGridy(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 5, 20)));
        lay_ijoin.setConstraints(fie_port, new GBC().setGridy(3).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 15, 20)));
        lay_ijoin.setConstraints(but_start_join, new GBC().setGridy(4).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 5, 20)));

        lay_ihost.setConstraints(lab_time, new GBC().setGridy(0).setFill(GBC.HORIZONTAL).setInsets(new Insets(10, 20, 5, 20)));
        lay_ihost.setConstraints(spin_time, new GBC().setGridy(1).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 15, 20)));
        lay_ihost.setConstraints(lab_customwords, new GBC().setGridy(2).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 5, 20)));
        lay_ihost.setConstraints(fie_customwords, new GBC().setGridy(3).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 15, 20)));
        lay_ihost.setConstraints(lab_port, new GBC().setGridy(4).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 5, 20)));
        lay_ihost.setConstraints(fie_port, new GBC().setGridy(5).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 15, 20)));
        lay_ihost.setConstraints(but_start_host, new GBC().setGridy(6).setFill(GBC.HORIZONTAL).setInsets(new Insets(0, 20, 5, 20)));

    }

    private void makefinal() {

        frame.add(title);
        frame.add(content_holder);
        content_holder.add(pan_first);

        pan_first.add(lab_name);
        pan_first.add(fie_name);
        pan_first.add(lab_colour);
        pan_first.add(but_choose_colour);
        pan_first.add(but_join);
        pan_first.add(but_host);

        pan_ijoin.add(lab_ip);
        pan_ijoin.add(fie_ip);
        pan_ijoin.add(lab_port);
        pan_ijoin.add(fie_port);
        pan_ijoin.add(but_start_join);

        pan_ihost.add(lab_time);
        pan_ihost.add(spin_time);
        pan_ihost.add(lab_customwords);
        pan_ihost.add(fie_customwords);
        pan_ihost.add(lab_port);
        pan_ihost.add(fie_port);
        pan_ihost.add(but_start_host);

        frame.pack();

    }

}
