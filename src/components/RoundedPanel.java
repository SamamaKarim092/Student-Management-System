// Updated RoundedPanel class using Bridge Pattern
package components;

import java.awt.*;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel implements UIComponent {
    private PanelStyle panelStyle;
    private int cornerRadius = 15;
    private Color shadowColor = new Color(0, 0, 0, 50);
    private int shadowSize = 5;

    // Constructors
    public RoundedPanel() {
        this(new RoundedPanelStyle());
    }

    public RoundedPanel(int radius) {
        this(radius, new RoundedPanelStyle());
    }

    public RoundedPanel(int radius, Color bgColor) {
        this(radius, bgColor, new RoundedPanelStyle());
    }

    public RoundedPanel(PanelStyle style) {
        super();
        this.panelStyle = style;
        setOpaque(false);
    }

    public RoundedPanel(int radius, PanelStyle style) {
        super();
        this.cornerRadius = radius;
        this.panelStyle = style;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor, PanelStyle style) {
        super();
        this.cornerRadius = radius;
        this.panelStyle = style;
        setBackground(bgColor);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        render(g2, getWidth(), getHeight());
        g2.dispose();
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        // Use the bridge to delegate rendering
        panelStyle.drawPanel(g2, width, height, cornerRadius, getBackground(), shadowColor, shadowSize);
    }

    @Override
    public void handleStateChange() {
        repaint();
    }

    // Bridge pattern methods
    public void setPanelStyle(PanelStyle style) {
        this.panelStyle = style;
        repaint();
    }

    public PanelStyle getPanelStyle() {
        return panelStyle;
    }

    // Existing methods remain the same
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