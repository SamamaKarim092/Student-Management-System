package view;

import components.HoverButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private HoverButton btnLogin;

    // Define a modern color palette
    private static final Color PRIMARY_COLOR = new Color(69, 123, 157);
    private static final Color SECONDARY_COLOR = new Color(241, 250, 238);
    private static final Color TEXT_COLOR = new Color(29, 53, 87);
    private static final Color PLACEHOLDER_COLOR = new Color(168, 185, 203);

    public LoginForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        setTitle("Student Management System - Login");
        setSize(800, 600); // Increased size for the new layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createLeftPanel(), BorderLayout.WEST);
        mainPanel.add(createRightPanel(), BorderLayout.CENTER);

        add(mainPanel);

        // Set the login button as the default for the Enter key
        getRootPane().setDefaultButton(btnLogin);
    }

    /**
     * Creates the decorative left panel with a gradient and branding.
     */
    private JPanel createLeftPanel() {
        // A custom panel with a gradient background
        @SuppressWarnings("serial")
        JPanel leftPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(29, 53, 87), 0, getHeight(), new Color(69, 123, 157));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setPreferredSize(new Dimension(350, 600));
        leftPanel.setLayout(new GridBagLayout());

        // Add a large icon (you should replace this with your own)
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/education_icon.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

        // Add title and subtitle
        JLabel title = new JLabel("Student Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Your Gateway to Success");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(220, 220, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        leftPanel.add(iconLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        leftPanel.add(title, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        leftPanel.add(subtitle, gbc);


        return leftPanel;
    }

    /**
     * Creates the right panel with the login form fields.
     */
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(SECONDARY_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Login Title
        JLabel lblTitle = new JLabel("Welcome Back!");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(lblTitle, gbc);

        JLabel lblSubtitle = new JLabel("Please login to access your account.");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(TEXT_COLOR.darker());
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 30, 10);
        rightPanel.add(lblSubtitle, gbc);

        // Username Field
        txtUsername = createPlaceholderTextField("Enter your username");
        JPanel userPanel = createIconTextFieldPanel(txtUsername, "/resources/user_icon.png");
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 10, 15, 10);
        rightPanel.add(userPanel, gbc);

        // Password Field
        txtPassword = createPlaceholderPasswordField("Enter your password");
        JPanel passPanel = createIconTextFieldPanel(txtPassword, "/resources/lock_icon.png");
        gbc.gridy = 3;
        rightPanel.add(passPanel, gbc);

        // Login Button
        btnLogin = new HoverButton("Login Securely");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setDefaultColor(PRIMARY_COLOR);
        btnLogin.setHoverColor(PRIMARY_COLOR.darker());
        btnLogin.setPreferredSize(new Dimension(100, 50)); // Taller button
        btnLogin.addActionListener((ActionEvent e) -> login());
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 10, 10, 10);
        rightPanel.add(btnLogin, gbc);

        return rightPanel;
    }

    /**
     * A helper method to create a JTextField with placeholder text.
     */
    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setText(placeholder);
        textField.setForeground(PLACEHOLDER_COLOR);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setOpaque(false);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });

        return textField;
    }

    /**
     * A helper method to create a JPasswordField with placeholder text.
     */
    private JPasswordField createPlaceholderPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setText(placeholder);
        passwordField.setEchoChar((char) 0); // Show placeholder text
        passwordField.setForeground(PLACEHOLDER_COLOR);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setOpaque(false);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String password = new String(passwordField.getPassword());
                if (password.equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setEchoChar('\u2022'); // Standard echo char
                    passwordField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });

        return passwordField;
    }

    /**
     * Creates a panel that combines an icon and a text field with a bottom border.
     */
    private JPanel createIconTextFieldPanel(JTextField textField, String iconPath) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false); // Make it transparent to show parent's background
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PLACEHOLDER_COLOR.darker())); // Bottom border

        ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);

        // Change border color on focus
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR));
            }

            @Override
            public void focusLost(FocusEvent e) {
                panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PLACEHOLDER_COLOR.darker()));
            }
        });

        return panel;
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        // Basic validation
        if (username.isEmpty() || password.isEmpty() || username.equals("Enter your username") || password.equals("Enter your password")) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Authentication logic (replace with your database check)
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Login successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // On successful login, open the dashboard
            SwingUtilities.invokeLater(() -> {
                new Dashboard().setVisible(true); // Your dashboard class
                dispose(); // Close login form
            });

        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}