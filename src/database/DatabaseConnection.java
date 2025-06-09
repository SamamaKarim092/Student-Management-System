package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String DB_URL ="jdbc:sqlserver://localhost:1433;databaseName=StudentDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "12345";

    private static Connection conn = null;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connected to database successfully!");
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "SQL Server JDBC Driver not found. Please add the driver to your project.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "SQL Server JDBC Driver not found", e);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.SEVERE, "Database connection error", e);
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing the connection", e);
        }
    }

    // Helper method to execute queries with proper resource management
    public static ResultSet executeQuery(String query) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            System.out.println("Executing query: " + query); // Log the query
            return ps.executeQuery();
            // Note: NOT using try-with-resources to avoid auto-closing
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query", e);
            JOptionPane.showMessageDialog(null, "Error executing query: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Helper method to execute updates (INSERT, UPDATE, DELETE)
    public static int executeUpdate(String query) {
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement(query);
            System.out.println("Executing update: " + query); // Log the update
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing update", e);
            JOptionPane.showMessageDialog(null, "Error executing update: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing statement", e);
            }
        }
    }

    // Method to execute prepared statement with parameters
    public static ResultSet executePreparedQuery(String query, Object... params) {
        try {
            PreparedStatement ps = getConnection().prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            System.out.println("Executing prepared query: " + query); // Log the query
            return ps.executeQuery();
            // Note: NOT using try-with-resources to avoid auto-closing
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing prepared query", e);
            JOptionPane.showMessageDialog(null, "Error executing prepared query: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Method to execute prepared update (INSERT, UPDATE, DELETE) with parameters
    public static int executePreparedUpdate(String query, Object... params) {
        PreparedStatement ps = null;
        try {
            ps = getConnection().prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            System.out.println("Executing prepared update: " + query); // Log the update
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing prepared update", e);
            JOptionPane.showMessageDialog(null, "Error executing prepared update: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing statement", e);
            }
        }
    }

    // Helper method to close ResultSet
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing ResultSet", e);
        }
    }
}