package Pages;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private int id;

    public GameFrame(int id) {
        this.id = id;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        gd.setFullScreenWindow(this);

        setLayout(new BorderLayout());

        JPanel game = new JPanel();

        Leaderboard lb = new Leaderboard(10);
        lb.setPreferredSize(new Dimension(300, getHeight()));

        add(game, BorderLayout.CENTER);
        add(lb, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame(10);
    }
}
