// ===============================
// REFINED ABSTRACTIONS
// ===============================

// Updated HoverButton class using Bridge Pattern
package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton implements UIComponent {
    private ButtonStyle buttonStyle;
    private Color textColor = Color.WHITE;
    private boolean isHovered = false;
    private boolean isPressed = false;
    private boolean isSelected = false;
    private int cornerRadius = 15;
    private ImageIcon icon;

    // Constructors
    public HoverButton(String text) {
        this(text, null, new HoverButtonStyle());
    }

    public HoverButton(String text, ImageIcon icon) {
        this(text, icon, new HoverButtonStyle());
    }

    public HoverButton(String text, ButtonStyle style) {
        this(text, null, style);
    }

    public HoverButton(String text, ImageIcon icon, ButtonStyle style) {
        super(text);
        this.icon = icon;
        this.buttonStyle = style;
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(textColor);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 15, 10, 15));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                handleStateChange();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                isPressed = false;
                handleStateChange();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                handleStateChange();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                handleStateChange();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        render(g2, getWidth(), getHeight());
        g2.dispose();
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        // Use the bridge to delegate rendering
        buttonStyle.drawBackground(g2, width, height, cornerRadius, isHovered, isPressed, isSelected);
        buttonStyle.drawContent(g2, getText(), icon, getFont(), getForeground(), width, height);
    }

    @Override
    public void handleStateChange() {
        repaint();
    }

    // Bridge pattern methods
    public void setButtonStyle(ButtonStyle style) {
        this.buttonStyle = style;
        repaint();
    }

    public ButtonStyle getButtonStyle() {
        return buttonStyle;
    }

    // Existing methods - now delegate to the bridge when appropriate
    public void setDefaultColor(Color color) {
        if (buttonStyle instanceof HoverButtonStyle) {
            ((HoverButtonStyle) buttonStyle).setDefaultColor(color);
            repaint();
        }
    }

    public void setHoverColor(Color color) {
        if (buttonStyle instanceof HoverButtonStyle) {
            ((HoverButtonStyle) buttonStyle).setHoverColor(color);
            repaint();
        }
    }

    public void setPressedColor(Color color) {
        if (buttonStyle instanceof HoverButtonStyle) {
            ((HoverButtonStyle) buttonStyle).setPressedColor(color);
            repaint();
        }
    }

    public void setSelectedColor(Color color) {
        if (buttonStyle instanceof HoverButtonStyle) {
            ((HoverButtonStyle) buttonStyle).setSelectedColor(color);
            repaint();
        }
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
