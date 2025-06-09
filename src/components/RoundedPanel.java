package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 15;
    private Color shadowColor = new Color(0, 0, 0, 50);
    private int shadowSize = 5;

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        setBackground(bgColor);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;

        // Enable antialiasing for smooth edges
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw shadow
        graphics.setColor(shadowColor);
        graphics.fillRoundRect(shadowSize, shadowSize, width - shadowSize * 2,
                height - shadowSize * 2, arcs.width, arcs.height);

        // Draw main panel
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowSize, height - shadowSize,
                arcs.width, arcs.height);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }

    public int getShadowSize() {
        return shadowSize;
    }

    public void setShadowColor(Color color) {
        this.shadowColor = color;
        repaint();
    }

    public Color getShadowColor() {
        return shadowColor;
    }
}