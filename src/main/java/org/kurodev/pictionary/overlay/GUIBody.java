package org.kurodev.pictionary.overlay;

import org.kurodev.pictionary.overlay.factory.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class GUIBody {

    public static int WIDTH = 720;
    public static int HEIGHT = 540;
    public static BufferedImage icon;
    String name;

    JFrame frame;
    GridBagLayout lay_frame = new GridBagLayout();

    JPanel pan_top, pan_mid, pan_bot;
    GridBagLayout lay_pan_top, lay_pan_mid, lay_pan_bot;
    JLabel lbl_icon_top;
    JLabel lbl_hint_top;

    JPanel pan_lft_mid, pan_mid_mid, pan_rht_mid;
    GridBagLayout lay_pan_lft_mid, lay_pan_mid_mid, lay_pan_rht_mid;

    JLabel lbl_timer_lft_mid;
    JButton[] buttons;

    JButton drawing_pane;
    DrawEventHandler draw_event_handle;

    JScrollPane pan_scp_rht_mid;
    JEditorPane txt_scp_rht_mid;

    JTextField fie_scp_rht_mid;
    JButton but_scp_rht_mid;

    public GUIBody(String _name) {
        this.name = _name;
        initialize();
        resize();
        makefinal();

        frame.pack();
        frame.setVisible(true);
    }

    private void initialize() {

        frame = new JFrame("TITLE"); // TODO
        lay_frame = new GridBagLayout();
        frame.setLayout(lay_frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pan_top = new JPanel();
        pan_top.setBackground(Color.darkGray);
        lay_pan_top = new GridBagLayout();
        pan_top.setLayout(lay_pan_top);

        lbl_icon_top = new JLabel(getIcon());
        lbl_hint_top = new JLabel("WATERMELON");

        pan_mid = new JPanel(lay_pan_mid = new GridBagLayout());
        pan_mid.setBackground(Color.gray);
        pan_mid.setLayout(lay_pan_mid);

        pan_lft_mid = new JPanel(lay_pan_lft_mid = new GridBagLayout());
        pan_mid_mid = new JPanel(lay_pan_mid_mid = new GridBagLayout());
        pan_mid_mid.setBackground(Color.GREEN);
        pan_rht_mid = new JPanel(lay_pan_rht_mid = new GridBagLayout());

        pan_bot = new JPanel(lay_pan_bot = new GridBagLayout());
        pan_bot.setBackground(Color.lightGray);

        lbl_timer_lft_mid = new JLabel("00:00");
        lbl_timer_lft_mid.setFont(new Font("calibri", Font.BOLD, 45));
        buttons = getButtons(List.of(Color.WHITE, Color.BLUE, Color.ORANGE, Color.BLACK, Color.GREEN, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.PINK, Color.RED, Color.YELLOW));

        drawing_pane = new JButton();
        drawing_pane.setRolloverEnabled(false);
        drawing_pane.setBackground(Color.LIGHT_GRAY);
        drawing_pane.setFocusPainted(false);
        drawing_pane.setBorderPainted(false);
//        drawing_pane.addMouseListener(new DrawEventHandler(this));
        drawing_pane.addMouseMotionListener(draw_event_handle = new DrawEventHandler(this));
        drawing_pane.addMouseWheelListener(draw_event_handle);

        for (JButton button : buttons)
            button.addActionListener(e -> draw_event_handle.setColor(button.getBackground()));

        pan_scp_rht_mid = new JScrollPane(txt_scp_rht_mid = new JEditorPane());
        txt_scp_rht_mid.setEditable(false);
        txt_scp_rht_mid.setContentType("text/html");

        fie_scp_rht_mid = new JTextField();
        but_scp_rht_mid = new JButton();
        but_scp_rht_mid.addActionListener(e -> {
            GUIManager.sendChat(name, fie_scp_rht_mid.getText());
            fie_scp_rht_mid.setText("");
        });
        fie_scp_rht_mid.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    but_scp_rht_mid.doClick();
            }
        });

    }

    private static JButton[] getButtons(List<Color> white) {
        JButton[] buttons = new JButton[white.size() - (white.size() % 2)];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(20, 20));
            buttons[i].setBackground(white.get(i));
        }
        return buttons;
    }

    private void resize() {

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        lay_frame.setConstraints(pan_top, new GBC().setWeightx(1).setWeighty(0.1).setFill(GBC.BOTH));
        lay_frame.setConstraints(pan_mid, new GBC().setGridy(1).setWeightx(1).setWeighty(0.7).setFill(GBC.BOTH));
        lay_frame.setConstraints(pan_bot, new GBC().setGridy(2).setWeightx(1).setWeighty(0.2).setFill(GBC.BOTH));

        frame.pack();

        lay_pan_top.setConstraints(lbl_icon_top, new GBC().setWeightx(1).setInsets(new Insets(0, 20, 0, 0)).setAnchor(GBC.WEST));
        lay_pan_top.setConstraints(lbl_hint_top, new GBC().setGridx(1).setAnchor(GBC.WEST).setInsets(new Insets(0, 0, 0, 100)));

        lay_pan_mid.setConstraints(pan_lft_mid, new GBC().setGridx(0).setWeighty(1).setWeightx(0.2).setFill(GBC.BOTH));

        lay_pan_mid_mid.setConstraints(drawing_pane, new GBC().setIpady(0).setIpadx(0)/*.setIpadx((int) (frame.getHeight()*0.5)).setIpady((int) (frame.getHeight()*0.5))*/);

        lay_pan_mid.setConstraints(pan_mid_mid, new GBC().setGridx(1).setWeighty(1).setWeightx(0.5).setFill(GBC.BOTH));
        lay_pan_mid.setConstraints(pan_rht_mid, new GBC().setGridx(2).setWeighty(1).setWeightx(1.5).setFill(GBC.BOTH));

        lay_pan_bot.setConstraints(lbl_timer_lft_mid, new GBC().setGridheight(2).setInsets(new Insets(0, 0, 0, 20)));
        for (int i = 0; i < buttons.length; i += 2) {
            lay_pan_bot.setConstraints(buttons[i], new GBC().setInsets(new Insets(3, 3, 3, 3)).setIpadx(20).setIpady(20).setGridx(i / 2 + 1).setGridy(0));
            lay_pan_bot.setConstraints(buttons[i + 1], new GBC().setInsets(new Insets(3, 3, 3, 3)).setIpadx(20).setIpady(20).setGridx(i / 2 + 1).setGridy(1));
        }

        lay_pan_rht_mid.setConstraints(pan_scp_rht_mid, new GBC().setGridwidth(2).setWeighty(1).setWeightx(1).setFill(GBC.BOTH));

        lay_pan_rht_mid.setConstraints(fie_scp_rht_mid, new GBC().setGridy(1).setWeightx(1).setFill(GBC.BOTH));
        lay_pan_rht_mid.setConstraints(but_scp_rht_mid, new GBC().setGridy(1).setGridx(1).setFill(GBC.VERTICAL));

    }

    private void makefinal() {

        pan_top.add(lbl_icon_top);
        pan_top.add(lbl_hint_top);

        frame.add(pan_top);

        pan_mid.add(pan_lft_mid);

        pan_mid_mid.add(drawing_pane);
        pan_mid.add(pan_mid_mid);

        pan_rht_mid.add(pan_scp_rht_mid);
        pan_rht_mid.add(fie_scp_rht_mid);
        pan_rht_mid.add(but_scp_rht_mid);

        pan_mid.add(pan_rht_mid);

        frame.add(pan_mid);

        pan_bot.add(lbl_timer_lft_mid);
        for (int i = 0; i < buttons.length; i++)
            pan_bot.add(buttons[i]);

        frame.add(pan_bot);
    }

    private Icon getIcon() {

        if (icon == null) {
            icon = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    int c = (int) (Math.random() * 255);
                    icon.setRGB(i, j, new Color(c, c, c).getRGB());
                }
            }
        }

        return new ImageIcon(icon);

    }

    public void close() {

    }
}
