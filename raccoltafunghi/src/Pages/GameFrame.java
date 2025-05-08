package Pages;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private String id;

    public GameFrame(String id) {
        this.id = id;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        gd.setFullScreenWindow(this);

        setLayout(new BorderLayout());

        Game game = new Game(id);

        Leaderboard lb = new Leaderboard(id);
        lb.setPreferredSize(new Dimension(300, getHeight()));

        add(game, BorderLayout.CENTER);
        add(lb, BorderLayout.EAST);

        setVisible(true);
    }
}
