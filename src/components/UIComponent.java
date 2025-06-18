// Bridge Pattern Implementation

// ===============================
// ABSTRACTION INTERFACES
// ===============================

// Main abstraction for UI components
package components;

import java.awt.*;

public interface UIComponent {
    void render(Graphics2D g2, int width, int height);
    void handleStateChange();
}
