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

    private ArrayList<Integer> giocatoriX = new ArrayList<>();
    private ArrayList<Integer> giocatoriY = new ArrayList<>();
    private ArrayList<Integer> funghiX = new ArrayList<>();
    private ArrayList<Integer> funghiY = new ArrayList<>();
    private ArrayList<Integer> funghi = new ArrayList<>();

    private ConnessioneAServer connection = new ConnessioneAServer("localhost", 1775);
    private String id;

    private BufferedImage playerTexture;
    private BufferedImage fungo0Texture;
    private BufferedImage fungo1Texture;
    private BufferedImage fungo2Texture;
    private BufferedImage fungo3Texture;
    private BufferedImage blockTexture;

    public Game(String id) {
        this.id = id;

        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(width * 14, height * 14));

        // Caricamento immagini
        playerTexture = loadImage("../Resources/player.png");
        fungo0Texture = loadImage("../Resources/fungo0.png");
        fungo1Texture = loadImage("../Resources/fungo1.png");
        fungo2Texture = loadImage("../Resources/fungo2.png");
        fungo3Texture = loadImage("../Resources/fungo3.png");
        blockTexture = loadImage("../Resources/block.png");

        // Gestione input tastiera
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

        // Timer per aggiornare lo stato del gioco
        int delay = 240;
        new Timer(delay, e -> {
            String req = "RS" + id;
            String input = connection.risposta(req);
            parseMap(input);
            repaint();
        }).start();
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int y = 0; y < height; y++) {
            int px = 0;
            for (int x = 0; x < width; x++) {
                if (checkPlayer(x, y)) {
                    g2.drawImage(playerTexture, px, y * 14, 14, 14, null);
                } else if (checkMushrooms(x, y)) {
                    int index = getFungoIndexAt(x, y);
                    if (index != -1) {
                        switch (funghi.get(index)) {
                            case 0 -> g2.drawImage(fungo0Texture, px, y * 14, 14, 14, null);
                            case 1 -> g2.drawImage(fungo1Texture, px, y * 14, 14, 14, null);
                            case 2 -> g2.drawImage(fungo2Texture, px, y * 14, 14, 14, null);
                            case 3 -> g2.drawImage(fungo3Texture, px, y * 14, 14, 14, null);
                        }
                    }
                } else {
                    g2.drawImage(blockTexture, px, y * 14, 14, 14, null);
                }
                px += 14;
            }
        }
    }

    private boolean checkPlayer(int x, int y) {
        for (int i = 0; i < giocatoriX.size(); i++) {
            if (x == giocatoriX.get(i) && y == giocatoriY.get(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMushrooms(int x, int y) {
        for (int i = 0; i < funghiX.size(); i++) {
            if (x == funghiX.get(i) && y == funghiY.get(i)) {
                return true;
            }
        }
        return false;
    }

    private int getFungoIndexAt(int x, int y) {
        for (int i = 0; i < funghiX.size(); i++) {
            if (x == funghiX.get(i) && y == funghiY.get(i)) {
                return i;
            }
        }
        return -1;
    }

    public void parseMap(String input) {
        giocatoriX.clear();
        giocatoriY.clear();
        funghiX.clear();
        funghiY.clear();
        funghi.clear();

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
                int valore = Integer.parseInt(datiFunghi[i]);
                int x = Integer.parseInt(datiFunghi[i + 1]);
                int y = Integer.parseInt(datiFunghi[i + 2]);
                funghi.add(valore);
                funghiX.add(x);
                funghiY.add(y);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
