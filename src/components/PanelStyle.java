// Implementation interface for panel styling
package components;

import java.awt.*;

public interface PanelStyle {
    void drawPanel(Graphics2D g2, int width, int height, int cornerRadius,
                   Color backgroundColor, Color shadowColor, int shadowSize);
    void drawShadow(Graphics2D g2, int width, int height, int cornerRadius,
                    Color shadowColor, int shadowSize);
}