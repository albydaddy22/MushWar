import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Leaderboard extends JPanel {

    private final Color mc = new Color(218, 138, 58);
    private final Color hc = new Color(230, 154, 80);

    private ArrayList<String> usernames;
    private ArrayList<String> points;

    private ConnessioneAServer connection = new ConnessioneAServer("localhost", 1775);
    private String id;

    private JPanel leaderboardPanel;

    public Leaderboard(String id) {
        this.id = id;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(mc);
        add(title, BorderLayout.NORTH);

        usernames = new ArrayList<>();
        points = new ArrayList<>();

        leaderboardPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        JPanel leaderboardWrapper = new JPanel(new BorderLayout());
        leaderboardWrapper.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40)); // top, left, bottom, right
        leaderboardWrapper.add(leaderboardPanel, BorderLayout.CENTER);

        ButtonPauroso exitbtn = new ButtonPauroso(mc, Color.WHITE, hc, Color.WHITE, "Exit");
        exitbtn.setPreferredSize(new Dimension(200, 50));
        exitbtn.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel exitPanel = new JPanel(new BorderLayout());
        exitPanel.setPreferredSize(new Dimension(1, 400));
        exitPanel.add(exitbtn, BorderLayout.NORTH);

        add(leaderboardWrapper, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);

        int delay = 240;
        new Timer(delay, e -> {
            String req = "RS" + id;
            String input = connection.risposta(req);
            parseLeaderboard(input);
            repaint();
        }).start();

        exitbtn.addActionListener(e -> {
            String req = "KO" + id;
            connection.risposta(req);

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame) {
                ((JFrame) window).dispose();
            }

            System.exit(0);
        });
    }

    public void parseLeaderboard(String input) {
        usernames.clear();
        points.clear();
        leaderboardPanel.removeAll();

        String[] dati = input.split("\\|");

        for (int i = 0; i + 1 < dati.length; i += 2) {
            String username = dati[i];
            String punteggio = dati[i + 1];
            usernames.add(username);
            points.add(punteggio);
        }

        for (int i = 0; i < usernames.size(); i++) {
            JLabel userLabel = new JLabel(usernames.get(i));
            userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

            JLabel pointLabel = new JLabel(points.get(i), SwingConstants.RIGHT);
            pointLabel.setForeground(mc);
            pointLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

            leaderboardPanel.add(userLabel);
            leaderboardPanel.add(pointLabel);
        }

        leaderboardPanel.revalidate();
    }
}
