// ===============================
// IMPLEMENTATION INTERFACES
// ===============================

// Implementation interface for button styling
package components;

import java.awt.*;
import javax.swing.ImageIcon;

public interface ButtonStyle {
    Color getBackgroundColor(boolean isHovered, boolean isPressed, boolean isSelected);
    void drawBackground(Graphics2D g2, int width, int height, int cornerRadius,
                        boolean isHovered, boolean isPressed, boolean isSelected);
    void drawContent(Graphics2D g2, String text, ImageIcon icon, Font font,
                     Color textColor, int width, int height);
}
