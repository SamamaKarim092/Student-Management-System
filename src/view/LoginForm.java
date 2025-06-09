package view;

import components.HoverButton;
import components.RoundedPanel;
import database.DatabaseConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
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

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private HoverButton btnLogin;

    public LoginForm() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        initComponents();
        setTitle("Student Management System - Login");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Login panel
        RoundedPanel loginPanel = new RoundedPanel(15, Color.WHITE);
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // System title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitle = new JLabel("Student Management System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginPanel.add(lblTitle, gbc);

        // Login subtitle
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 20, 10);
        JLabel lblSubtitle = new JLabel("Login to your account", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(lblSubtitle, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(txtPassword, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 10, 10);
        btnLogin = new HoverButton("Login");
        btnLogin.setDefaultColor(new Color(41, 128, 185));
        btnLogin.addActionListener((ActionEvent e) -> {
            login();
        });
        loginPanel.add(btnLogin, gbc);

        mainPanel.add(loginPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Add action listener for Enter key
        getRootPane().setDefaultButton(btnLogin);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // For the purpose of this example, we'll use a simple authentication
        // In a real application, you would check against a users table in your database
        if (username.equals("admin") && password.equals("admin")) {
            // Authentication successful
            JOptionPane.showMessageDialog(this, "Login successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Open the main dashboard and close login form
            SwingUtilities.invokeLater(() -> {
                new Dashboard().setVisible(true);
                dispose();
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