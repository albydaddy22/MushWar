package Pages;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import Client.ConnessioneAServer;

public class Game extends JPanel {

    private int width = 100;
    private int height = 70;
    private ArrayList<Integer> giocatoriX;
    private ArrayList<Integer> giocatoriY;
    private ArrayList<Integer> funghiX;
    private ArrayList<Integer> funghiY;

    private ConnessioneAServer connection = new ConnessioneAServer("localhost", 1775);
    private int id;
    private boolean up, down, left, right;

    BufferedImage playerTexture;
    BufferedImage fungo0Texture;
    BufferedImage fungo1Texture;
    BufferedImage fungo2Texture;
    BufferedImage fungo3Texture;
    BufferedImage blockTexture;

    public Game(int id) {
        this.id = id;

        try {
            blockTexture = ImageIO.read(new File("Resources/block.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            playerTexture = ImageIO.read(new File("Resources/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fungo0Texture = ImageIO.read(new File("Resources/fungo0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fungo1Texture = ImageIO.read(new File("Resources/fungo1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fungo2Texture = ImageIO.read(new File("Resources/fungo2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fungo3Texture = ImageIO.read(new File("Resources/fungo3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addKeyListener(new KeyAdapter() {
            String req = "MO";

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> connection.risposta(req + "NN" + "|" + id);
                    case KeyEvent.VK_S -> connection.risposta(req + "SS" + "|" + id);
                    case KeyEvent.VK_A -> connection.risposta(req + "WW" + "|" + id);
                    case KeyEvent.VK_D -> connection.risposta(req + "EE" + "|" + id);
                }
            }
        });

        int delay = 240;
        new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String req = "RS" + id;
                String input = connection.risposta(req);
                parseMap(input);
                repaint();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for(int i = 0; i < height - 1; i++) {
            for(int j = 0; j < width - 1; j++) {
                if(checkPlayer(j, i)) {

                }
            }
        }
    }

    public boolean checkPlayer(int x, int y) {
        for(int i = 0; i < giocatoriX.size(); i++) {
            if(x == giocatoriX.get(i) && y == giocatoriY.get(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMushrooms(int x, int y) {
        for(int i = 0; i < funghiX.size(); i++) {
            if(x == funghiX.get(i) && y == funghiY.get(i)) {
                return true;
            }
        }
        return false;
    }

    public void parseMap(String input) {
        giocatoriX.clear();
        giocatoriY.clear();
        funghiX.clear();
        funghiY.clear();

        String[] parti = input.split("\\|\\|");
        if (parti.length != 2) return;

        String[] datiGiocatori = parti[0].split("\\|");
        String[] datiFunghi = parti[1].split("\\|");

        for (int i = 0; i + 2 < datiGiocatori.length; i += 3) {
            try {
                int x = Integer.parseInt(datiGiocatori[i + 1]);
                int y = Integer.parseInt(datiGiocatori[i + 2]);
                giocatoriX.add(x);
                giocatoriY.add(y);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i + 2 < datiFunghi.length; i += 3) {
            try {
                int x = Integer.parseInt(datiFunghi[i + 1]);
                int y = Integer.parseInt(datiFunghi[i + 2]);
                funghiX.add(x);
                funghiY.add(y);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}