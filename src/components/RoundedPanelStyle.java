// Concrete implementation for rounded panel styling
package components;

import java.awt.*;

public class RoundedPanelStyle implements PanelStyle {

    @Override
    public void drawPanel(Graphics2D g2, int width, int height, int cornerRadius,
                          Color backgroundColor, Color shadowColor, int shadowSize) {
        // Draw shadow first
        drawShadow(g2, width, height, cornerRadius, shadowColor, shadowSize);

        // Draw main panel
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width - shadowSize, height - shadowSize,
                cornerRadius, cornerRadius);
    }

    @Override
    public void drawShadow(Graphics2D g2, int width, int height, int cornerRadius,
                           Color shadowColor, int shadowSize) {
        g2.setColor(shadowColor);
        g2.fillRoundRect(shadowSize, shadowSize, width - shadowSize * 2,
                height - shadowSize * 2, cornerRadius, cornerRadius);
    }
}