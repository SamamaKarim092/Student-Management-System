package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class HoverButton extends JButton {
    private Color defaultColor = new Color(41, 128, 185);
    private Color hoverColor = new Color(52, 152, 219);
    private Color pressedColor = new Color(26, 82, 118);
    private Color textColor = Color.WHITE;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private int cornerRadius = 10;

    public HoverButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(textColor);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(defaultColor);
        setBorder(new EmptyBorder(10, 15, 10, 15));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set color based on button state
        if (isPressed) {
            g2.setColor(pressedColor);
        } else if (isHovered) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw text
        g2.setColor(getForeground());
        g2.setFont(getFont());

        // Center text
        int textWidth = g2.getFontMetrics().stringWidth(getText());
        int textHeight = g2.getFontMetrics().getHeight();
        g2.drawString(getText(), (getWidth() - textWidth) / 2,
                (getHeight() - textHeight) / 2 + g2.getFontMetrics().getAscent());

        g2.dispose();
    }

    // Getter and setter methods for customization
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
        setBackground(color);
        repaint();
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
        repaint();
    }

    public void setPressedColor(Color color) {
        this.pressedColor = color;
        repaint();
    }

    public void setButtonTextColor(Color color) {
        this.textColor = color;
        setForeground(color);
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
}