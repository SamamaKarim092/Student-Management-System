// ===============================
// CONCRETE IMPLEMENTATIONS
// ===============================

// Concrete implementation for hover button styling
package components;

import java.awt.*;
import javax.swing.ImageIcon;

public class HoverButtonStyle implements ButtonStyle {
    private Color defaultColor = new Color(72, 84, 96);
    private Color hoverColor = new Color(47, 54, 64);
    private Color pressedColor = new Color(26, 82, 118);
    private Color selectedColor = new Color(0, 150, 255);

    @Override
    public Color getBackgroundColor(boolean isHovered, boolean isPressed, boolean isSelected) {
        if (isPressed) return pressedColor;
        if (isSelected) return selectedColor;
        if (isHovered) return hoverColor;
        return defaultColor;
    }

    @Override
    public void drawBackground(Graphics2D g2, int width, int height, int cornerRadius,
                               boolean isHovered, boolean isPressed, boolean isSelected) {
        g2.setColor(getBackgroundColor(isHovered, isPressed, isSelected));
        g2.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
    }

    @Override
    public void drawContent(Graphics2D g2, String text, ImageIcon icon, Font font,
                            Color textColor, int width, int height) {
        g2.setColor(textColor);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();

        int totalWidth = 0;
        int gap = 10;

        if (icon != null) {
            totalWidth += icon.getIconWidth();
        }
        if (text != null && !text.isEmpty()) {
            totalWidth += fm.stringWidth(text);
            if (icon != null) totalWidth += gap;
        }

        int x = (width - totalWidth) / 2;
        int yText = (height - fm.getHeight()) / 2 + fm.getAscent();

        // Draw icon
        if (icon != null) {
            int yIcon = (height - icon.getIconHeight()) / 2;
            g2.drawImage(icon.getImage(), x, yIcon, null);
            x += icon.getIconWidth() + gap;
        }

        // Draw text
        if (text != null) {
            g2.drawString(text, x, yText);
        }
    }

    // Getters and setters for colors
    public void setDefaultColor(Color color) { this.defaultColor = color; }
    public void setHoverColor(Color color) { this.hoverColor = color; }
    public void setPressedColor(Color color) { this.pressedColor = color; }
    public void setSelectedColor(Color color) { this.selectedColor = color; }
    public Color getDefaultColor() { return defaultColor; }
    public Color getHoverColor() { return hoverColor; }
    public Color getPressedColor() { return pressedColor; }
    public Color getSelectedColor() { return selectedColor; }
}