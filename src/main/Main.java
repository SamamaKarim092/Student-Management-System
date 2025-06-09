package main;

import database.DatabaseConnection;
import view.LoginForm;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        // Test database connection
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Database connection successful!");

            // Start the application
            SwingUtilities.invokeLater(() -> {
                // Choose which screen to start with
                // If you want to bypass login, use Dashboard directly
                // new Dashboard().setVisible(true);

                // If you want to use login screen
                new LoginForm().setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(null,
                    "Could not connect to the database.\nPlease check your database configuration.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}