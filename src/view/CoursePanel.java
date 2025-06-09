package view;

import components.HoverButton;
import components.RoundedPanel;
import database.DatabaseConnection;
import model.Course;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class CoursePanel extends JPanel {

    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtCourseName, txtDepartment, txtCredits;
    private JTextArea txtDescription;
    private HoverButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTextField txtSearch;

    public CoursePanel() {
        setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout());

        initComponents();
        loadCourseData();
    }

    private void initComponents() {
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Course Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(200, 35));
        searchPanel.add(txtSearch);

        btnSearch = new HoverButton("Search");
        btnSearch.setDefaultColor(new Color(52, 152, 219));
        btnSearch.addActionListener((ActionEvent e) -> {
            searchCourses(txtSearch.getText().trim());
        });
        searchPanel.add(btnSearch);

        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        contentPanel.setOpaque(false);

        // Form panel
        RoundedPanel formPanel = new RoundedPanel(15, Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblId = new JLabel("Course ID:");
        formPanel.add(lblId, gbc);

        gbc.gridx = 1;
        txtId = new JTextField(15);
        txtId.setEditable(false);
        formPanel.add(txtId, gbc);

        // Course Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblCourseName = new JLabel("Course Name:");
        formPanel.add(lblCourseName, gbc);

        gbc.gridx = 1;
        txtCourseName = new JTextField(15);
        formPanel.add(txtCourseName, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblDepartment = new JLabel("Department:");
        formPanel.add(lblDepartment, gbc);

        gbc.gridx = 1;
        txtDepartment = new JTextField(15);
        formPanel.add(txtDepartment, gbc);

        // Credits
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblCredits = new JLabel("Credits:");
        formPanel.add(lblCredits, gbc);

        gbc.gridx = 1;
        txtCredits = new JTextField(15);
        formPanel.add(txtCredits, gbc);

        // Description
        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel lblDescription = new JLabel("Description:");
        formPanel.add(lblDescription, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(txtDescription);
        formPanel.add(descScrollPane, gbc);
        gbc.gridheight = 1;

        // Buttons Panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 5, 5, 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        btnAdd = new HoverButton("Add Course");
        btnAdd.setDefaultColor(new Color(46, 204, 113));
        btnAdd.addActionListener((ActionEvent e) -> {
            addCourse();
        });
        buttonPanel.add(btnAdd);

        btnUpdate = new HoverButton("Update");
        btnUpdate.setDefaultColor(new Color(52, 152, 219));
        btnUpdate.addActionListener((ActionEvent e) -> {
            updateCourse();
        });
        buttonPanel.add(btnUpdate);

        btnDelete = new HoverButton("Delete");
        btnDelete.setDefaultColor(new Color(231, 76, 60));
        btnDelete.addActionListener((ActionEvent e) -> {
            deleteCourse();
        });
        buttonPanel.add(btnDelete);

        btnClear = new HoverButton("Clear");
        btnClear.setDefaultColor(new Color(149, 165, 166));
        btnClear.addActionListener((ActionEvent e) -> {
            clearForm();
        });
        buttonPanel.add(btnClear);

        formPanel.add(buttonPanel, gbc);

        contentPanel.add(formPanel, BorderLayout.NORTH);

        // Table panel
        RoundedPanel tablePanel = new RoundedPanel(15, Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table
        String[] columns = {"ID", "Course Name", "Department", "Credits", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(30);
        courseTable.setSelectionBackground(new Color(52, 152, 219, 100));
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseTable.getTableHeader().setBackground(new Color(52, 73, 94));
        courseTable.getTableHeader().setForeground(Color.WHITE);

        // Add mouse listener to select row
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow != -1) {
                    displayCourseDetails(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(courseTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }



    private void loadCourseData() {
        // Clear the table
        tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
            String query = "SELECT courseId, courseName, department, credits, description FROM Courses ORDER BY courseId";
            rs = DatabaseConnection.executeQuery(query);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("department"),
                        rs.getInt("credits"),
                        rs.getString("description")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading course data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    private void displayCourseDetails(int row) {
        txtId.setText(tableModel.getValueAt(row, 0).toString());
        txtCourseName.setText(tableModel.getValueAt(row, 1).toString());
        txtDepartment.setText(tableModel.getValueAt(row, 2).toString());
        txtCredits.setText(tableModel.getValueAt(row, 3).toString());
        txtDescription.setText(tableModel.getValueAt(row, 4).toString());
    }

    private void addCourse() {
        if (!validateInputs()) {
            return;
        }

        try {
            String query = "INSERT INTO Courses (courseName, department, credits, description) VALUES (?, ?, ?, ?)";

            int credits = Integer.parseInt(txtCredits.getText());

            int result = DatabaseConnection.executePreparedUpdate(query,
                    txtCourseName.getText(),
                    txtDepartment.getText(),
                    credits,
                    txtDescription.getText());

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Course added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadCourseData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add course.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a numeric value.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourse() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a course to update.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validateInputs()) {
            return;
        }

        try {
            int courseId = Integer.parseInt(txtId.getText());
            int credits = Integer.parseInt(txtCredits.getText());

            String query = "UPDATE Courses SET courseName = ?, department = ?, credits = ?, description = ? WHERE courseId = ?";

            int result = DatabaseConnection.executePreparedUpdate(query,
                    txtCourseName.getText(),
                    txtDepartment.getText(),
                    credits,
                    txtDescription.getText(),
                    courseId);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Course updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadCourseData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update course.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a numeric value.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update the deleteCourse() method:
    private void deleteCourse() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this course?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            ResultSet rs = null;
            try {
                int courseId = Integer.parseInt(txtId.getText());

                // Check if course is being used by any student
                String checkQuery = "SELECT COUNT(*) AS count FROM Students WHERE courseId = ?";
                rs = DatabaseConnection.executePreparedQuery(checkQuery, courseId);

                if (rs.next() && rs.getInt("count") > 0) {
                    JOptionPane.showMessageDialog(this,
                            "Cannot delete this course because it is assigned to students.",
                            "Delete Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DatabaseConnection.closeResultSet(rs);
                rs = null;

                String query = "DELETE FROM Courses WHERE courseId = ?";
                int result = DatabaseConnection.executePreparedUpdate(query, courseId);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Course deleted successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadCourseData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete course.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                DatabaseConnection.closeResultSet(rs);
            }
        }
    }




    // Update the searchCourses() method:
    private void searchCourses(String searchTerm) {
        tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
            String query = "SELECT courseId, courseName, department, credits, description " +
                    "FROM Courses " +
                    "WHERE courseName LIKE ? OR department LIKE ? OR description LIKE ? " +
                    "ORDER BY courseId";

            String searchPattern = "%" + searchTerm + "%";
            rs = DatabaseConnection.executePreparedQuery(query,
                    searchPattern,
                    searchPattern,
                    searchPattern);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("department"),
                        rs.getInt("credits"),
                        rs.getString("description")
                };
                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No matching courses found.",
                        "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for courses: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtCourseName.setText("");
        txtDepartment.setText("");
        txtCredits.setText("");
        txtDescription.setText("");
        courseTable.clearSelection();
    }

    private boolean validateInputs() {
        if (txtCourseName.getText().isEmpty() || txtDepartment.getText().isEmpty() ||
                txtCredits.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int credits = Integer.parseInt(txtCredits.getText());
            if (credits <= 0) {
                JOptionPane.showMessageDialog(this, "Credits must be a positive number.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a numeric value.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}

