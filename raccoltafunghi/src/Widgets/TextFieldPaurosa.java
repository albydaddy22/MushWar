package Widgets;

import javax.swing.*;
import java.awt.*;

public class TextFieldPaurosa extends JTextField {

    private String placeholder;

    public TextFieldPaurosa(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10)); // padding interno
        setBackground(new Color(222, 222, 224));
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

        super.paintComponent(g);

        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.PLAIN));
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left + 6, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
            g2.dispose();
        }
    }
}
