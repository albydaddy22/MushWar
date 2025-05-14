import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;


public class ButtonPauroso extends JButton implements MouseListener{

    private Color hoverColor, originalColor, textHoverColor, originalTextColor = Color.WHITE;
    private static final int RADIUS = 20;

    public ButtonPauroso(Color color, Color textColor, Color hoverColor, String text) {
        setBackground(color);
        setBorderPainted(false);
        setForeground(textColor);
        this.hoverColor = hoverColor;
        originalColor = color;
        setText(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        addMouseListener(this);
    }

    public ButtonPauroso(Color color, Color borderColor, Color textColor, Color hoverColor, int vafangul, String text) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(borderColor, 1));
        setForeground(textColor);
        this.hoverColor = hoverColor;
        originalColor = color;
        setText(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        addMouseListener(this);
    }

    public ButtonPauroso(Color color, Color borderColor, Color textColor, Color hoverColor, Color textHoverColor, String text) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(borderColor, 2));
        setForeground(textColor);
        this.hoverColor = hoverColor;
        originalColor = color;
        this.textHoverColor = textHoverColor;
        originalTextColor = textColor;
        setText(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        addMouseListener(this);
    }

    public ButtonPauroso(Color color, Color textColor, Color hoverColor, Color textHoverColor, String text) {
        setBackground(color);
        setBorderPainted(false);
        setForeground(textColor);
        this.hoverColor = hoverColor;
        originalColor = color;
        this.textHoverColor = textHoverColor;
        originalTextColor = textColor;
        setText(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);

        if (getBorder() != null && isBorderPainted()) {
            g2.setColor(((LineBorder) getBorder()).getLineColor());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(hoverColor);
        setForeground(textHoverColor);
    }
    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(originalColor);
        setForeground(originalTextColor);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(originalColor);
        setForeground(originalTextColor);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(hoverColor);
        setForeground(textHoverColor);
    }
}
