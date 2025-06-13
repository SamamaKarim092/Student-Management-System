package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton {
    private Color defaultColor = new Color(72, 84, 96);
    private Color hoverColor = new Color(47, 54, 64);
    private Color pressedColor = new Color(26, 82, 118);
    private Color selectedColor = new Color(0, 150, 255);
    private Color textColor = Color.WHITE;

    private boolean isHovered = false;
    private boolean isPressed = false;
    private boolean isSelected = false;

    private int cornerRadius = 15;
    private ImageIcon icon;

    // Constructors
    public HoverButton(String text) {
        this(text, null);
    }

    public HoverButton(String text, ImageIcon icon) {
        super(text);
        this.icon = icon;
        init();
    }

    private void init() {
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

        // Background color based on state
        if (isPressed) {
            g2.setColor(pressedColor);
        } else if (isSelected) {
            g2.setColor(selectedColor);
        } else if (isHovered) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(defaultColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw icon and text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();

        int totalWidth = 0;
        int gap = 10;

        if (icon != null) {
            totalWidth += icon.getIconWidth();
        }
        if (getText() != null && !getText().isEmpty()) {
            totalWidth += fm.stringWidth(getText());
            if (icon != null) totalWidth += gap;
        }

        int x = (getWidth() - totalWidth) / 2;
        int yText = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        // Draw icon
        if (icon != null) {
            int yIcon = (getHeight() - icon.getIconHeight()) / 2;
            g2.drawImage(icon.getImage(), x, yIcon, this);
            x += icon.getIconWidth() + gap;
        }

        // Draw text
        if (getText() != null) {
            g2.drawString(getText(), x, yText);
        }

        g2.dispose();
    }

    // Customization Methods
    public void setDefaultColor(Color color) {
        this.defaultColor = color;
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

    public void setSelectedColor(Color color) {
        this.selectedColor = color;
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

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }

    public void setButtonIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }
}
