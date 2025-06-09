package view;

import components.HoverButton;
import components.RoundedPanel;
import database.DatabaseConnection;
import model.Student;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

public class StudentPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtFirstName, txtLastName, txtEmail, txtPhone, txtAddress;
    private JComboBox<String> cbGender, cbCourse;
    private JDateChooser dateChooser, enrollmentDateChooser;
    private HoverButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTextField txtSearch;

    public StudentPanel() {
        setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout());

        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Student Management");
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
            searchStudents(txtSearch.getText().trim());
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
        JLabel lblId = new JLabel("Student ID:");
        formPanel.add(lblId, gbc);

        gbc.gridx = 1;
        txtId = new JTextField(15);
        txtId.setEditable(false);
        formPanel.add(txtId, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblFirstName = new JLabel("First Name:");
        formPanel.add(lblFirstName, gbc);

        gbc.gridx = 1;
        txtFirstName = new JTextField(15);
        formPanel.add(txtFirstName, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblLastName = new JLabel("Last Name:");
        formPanel.add(lblLastName, gbc);

        gbc.gridx = 1;
        txtLastName = new JTextField(15);
        formPanel.add(txtLastName, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblGender = new JLabel("Gender:");
        formPanel.add(lblGender, gbc);

        gbc.gridx = 1;
        String[] genders = {"Male", "Female", "Other"};
        cbGender = new JComboBox<>(genders);
        formPanel.add(cbGender, gbc);

        // Date of Birth
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblDob = new JLabel("Date of Birth:");
        formPanel.add(lblDob, gbc);

        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        formPanel.add(dateChooser, gbc);

        // Email
        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel lblEmail = new JLabel("Email:");
        formPanel.add(lblEmail, gbc);

        gbc.gridx = 3;
        txtEmail = new JTextField(15);
        formPanel.add(txtEmail, gbc);

        // Phone
        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel lblPhone = new JLabel("Phone:");
        formPanel.add(lblPhone, gbc);

        gbc.gridx = 3;
        txtPhone = new JTextField(15);
        formPanel.add(txtPhone, gbc);

        // Address
        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel lblAddress = new JLabel("Address:");
        formPanel.add(lblAddress, gbc);

        gbc.gridx = 3;
        txtAddress = new JTextField(15);
        formPanel.add(txtAddress, gbc);

        // Course
        gbc.gridx = 2;
        gbc.gridy = 3;
        JLabel lblCourse = new JLabel("Course:");
        formPanel.add(lblCourse, gbc);

        gbc.gridx = 3;
        cbCourse = new JComboBox<>();
        loadCourses();
        formPanel.add(cbCourse, gbc);

        // Enrollment Date
        gbc.gridx = 2;
        gbc.gridy = 4;
        JLabel lblEnrollment = new JLabel("Enrollment Date:");
        formPanel.add(lblEnrollment, gbc);

        gbc.gridx = 3;
        enrollmentDateChooser = new JDateChooser();
        enrollmentDateChooser.setDateFormatString("yyyy-MM-dd");
        enrollmentDateChooser.setDate(new Date()); // Set to current date
        formPanel.add(enrollmentDateChooser, gbc);

        // Buttons Panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 5, 5, 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        btnAdd = new HoverButton("Add Student");
        btnAdd.setDefaultColor(new Color(46, 204, 113));
        btnAdd.addActionListener((ActionEvent e) -> {
            addStudent();
        });
        buttonPanel.add(btnAdd);

        btnUpdate = new HoverButton("Update");
        btnUpdate.setDefaultColor(new Color(52, 152, 219));
        btnUpdate.addActionListener((ActionEvent e) -> {
            updateStudent();
        });
        buttonPanel.add(btnUpdate);

        btnDelete = new HoverButton("Delete");
        btnDelete.setDefaultColor(new Color(231, 76, 60));
        btnDelete.addActionListener((ActionEvent e) -> {
            deleteStudent();
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
        String[] columns = {"ID", "First Name", "Last Name", "Gender", "DOB", "Email", "Phone", "Course", "Enrollment Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.setSelectionBackground(new Color(52, 152, 219, 100));
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(new Color(52, 73, 94));
        studentTable.getTableHeader().setForeground(Color.WHITE);

        // Add mouse listener to select row
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    displayStudentDetails(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void loadCourses() {
        cbCourse.removeAllItems();
        ResultSet rs = null;
        try {
            rs = DatabaseConnection.executeQuery("SELECT courseName FROM Courses");
            while (rs.next()) {
                cbCourse.addItem(rs.getString("courseName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    // Update the loadStudentData() method:
    private void loadStudentData() {
        tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
            String query = "SELECT s.studentId, s.firstName, s.lastName, s.gender, s.dateOfBirth, " +
                    "s.email, s.phone, c.courseName, s.enrollmentDate " +
                    "FROM Students s " +
                    "JOIN Courses c ON s.courseId = c.courseId " +
                    "ORDER BY s.studentId";

            rs = DatabaseConnection.executeQuery(query);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("courseName"),
                        rs.getDate("enrollmentDate")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading student data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    // Update the displayStudentDetails() method:
    private void displayStudentDetails(int row) {
        ResultSet rs = null;
        try {
            txtId.setText(tableModel.getValueAt(row, 0).toString());
            txtFirstName.setText(tableModel.getValueAt(row, 1).toString());
            txtLastName.setText(tableModel.getValueAt(row, 2).toString());
            cbGender.setSelectedItem(tableModel.getValueAt(row, 3));

            Date dob = (Date) tableModel.getValueAt(row, 4);
            dateChooser.setDate(dob);

            txtEmail.setText(tableModel.getValueAt(row, 5).toString());
            txtPhone.setText(tableModel.getValueAt(row, 6).toString());

            int studentId = Integer.parseInt(txtId.getText());
            String query = "SELECT address FROM Students WHERE studentId = " + studentId;
            rs = DatabaseConnection.executeQuery(query);
            if (rs.next()) {
                txtAddress.setText(rs.getString("address"));
            }

            cbCourse.setSelectedItem(tableModel.getValueAt(row, 7));

            Date enrollmentDate = (Date) tableModel.getValueAt(row, 8);
            enrollmentDateChooser.setDate(enrollmentDate);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student details: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    // Update the addStudent() method:
    private void addStudent() {
        if (!validateInputs()) {
            return;
        }

        ResultSet rs = null;
        try {
            String courseName = cbCourse.getSelectedItem().toString();
            String courseQuery = "SELECT courseId FROM Courses WHERE courseName = ?";
            rs = DatabaseConnection.executePreparedQuery(courseQuery, courseName);

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Selected course not found.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int courseId = rs.getInt("courseId");
            DatabaseConnection.closeResultSet(rs);
            rs = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format(dateChooser.getDate());
            String enrollmentDate = sdf.format(enrollmentDateChooser.getDate());

            String query = "INSERT INTO Students (firstName, lastName, gender, dateOfBirth, email, " +
                    "phone, address, courseId, enrollmentDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int result = DatabaseConnection.executePreparedUpdate(query,
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    cbGender.getSelectedItem(),
                    dob,
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtAddress.getText(),
                    courseId,
                    enrollmentDate);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Student added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }

    private void updateStudent() {
        if (!validateInputs()) {
            return;
        }

        ResultSet rs = null;
        try {
            int studentId = Integer.parseInt(txtId.getText());
            String courseName = cbCourse.getSelectedItem().toString();
            String courseQuery = "SELECT courseId FROM Courses WHERE courseName = ?";
            rs = DatabaseConnection.executePreparedQuery(courseQuery, courseName);

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Selected course not found.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int courseId = rs.getInt("courseId");
            DatabaseConnection.closeResultSet(rs);
            rs = null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format(dateChooser.getDate());
            String enrollmentDate = sdf.format(enrollmentDateChooser.getDate());

            String query = "UPDATE Students SET firstName = ?, lastName = ?, gender = ?, dateOfBirth = ?, " +
                    "email = ?, phone = ?, address = ?, courseId = ?, enrollmentDate = ? WHERE studentId = ?";

            int result = DatabaseConnection.executePreparedUpdate(query,
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    cbGender.getSelectedItem(),
                    dob,
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtAddress.getText(),
                    courseId,
                    enrollmentDate,
                    studentId);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }


    private void deleteStudent() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                int studentId = Integer.parseInt(txtId.getText());

                String query = "DELETE FROM Students WHERE studentId = ?";
                int result = DatabaseConnection.executePreparedUpdate(query, studentId);

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    loadStudentData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete student.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid student ID format.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } try {
                // your code
            } catch (Exception e) { // ✅ more generic
                e.printStackTrace();
            }

        }
    }

    private void searchStudents(String keyword) {
        tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
            String query = "SELECT s.studentId, s.firstName, s.lastName, s.gender, s.dateOfBirth, " +
                    "s.email, s.phone, c.courseName, s.enrollmentDate " +
                    "FROM Students s JOIN Courses c ON s.courseId = c.courseId " +
                    "WHERE s.firstName LIKE ? OR s.lastName LIKE ? OR s.email LIKE ?";

            String searchKeyword = "%" + keyword + "%";
            rs = DatabaseConnection.executePreparedQuery(query, searchKeyword, searchKeyword, searchKeyword);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("courseName"),
                        rs.getDate("enrollmentDate")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            DatabaseConnection.closeResultSet(rs);
        }
    }


    private void clearForm() {
        txtId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        cbGender.setSelectedIndex(0);
        dateChooser.setDate(null);
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        if (cbCourse.getItemCount() > 0) {
            cbCourse.setSelectedIndex(0);
        }
        enrollmentDateChooser.setDate(new Date());
        studentTable.clearSelection();
    }

    private boolean validateInputs() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date of birth.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (enrollmentDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select an enrollment date.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!txtEmail.getText().matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtPhone.getText().length() < 10) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}