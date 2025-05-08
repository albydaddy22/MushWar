package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import Client.ConnessioneAServer;
import Widgets.ButtonPauroso;
import Widgets.TextFieldPaurosa;

public class HomePage extends JFrame {

    private ConnessioneAServer connection = new ConnessioneAServer("localhost", 1775);

    private final Color mc = new Color(218, 138, 58);
    private final Color hc = new Color(230, 154, 80);
    private String id;

    public HomePage() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(new GridLayout(3, 0, 0, 0));

        ImageIcon logononscaled = new ImageIcon(getClass().getResource("../Resources/logo.png"));
        Image logoscaledimage = logononscaled.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon logoscaled = new ImageIcon(logoscaledimage);
        container.add(new JLabel(logoscaled));

        TextFieldPaurosa username = new TextFieldPaurosa("Username");
        username.setPreferredSize(new Dimension(300, 50));
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        usernamePanel.add(username);
        container.add(usernamePanel);

        ButtonPauroso connectbtn = new ButtonPauroso(mc, Color.WHITE, hc, Color.WHITE, "Connect");
        connectbtn.setPreferredSize(new Dimension(200, 50));
        connectbtn.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.add(connectbtn);
        container.add(btnPanel);

        connectbtn.addActionListener(e -> {
            String req = "LG" + username.getText();
            String res = connection.risposta(req);

            if(res.contains("OK")) {
                id = res.substring(res.indexOf('K') + 1, res.length());

                dispose();

                GameFrame g = new GameFrame(id);
            }
            else {
                JOptionPane.showMessageDialog(null, "Utente già registrato", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(container);

        setVisible(true);
    }

    public String getId() {
        return id;
    }
}