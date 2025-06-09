package view;

import model.Grade;
import database.GradeHandler;
import database.DatabaseConnection;
import components.RoundedPanel;
import components.HoverButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.Vector;

public class GradePanel extends JPanel {
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> studentComboBox;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> gradeComboBox;
    private JTextField semesterField;
    private JTextField yearField;
    private JButton addButton, updateButton, deleteButton, clearButton;

    private int selectedGradeId = -1;
    private Vector<Integer> studentIds = new Vector<>();
    private Vector<Integer> courseIds = new Vector<>();

    public GradePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        initComponents();
        loadGrades();
    }

    private void initComponents() {
        // Form Panel
        RoundedPanel formPanel = new RoundedPanel(15, Color.WHITE);
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Load students for dropdown
        Vector<String> studentNames = new Vector<>();
        studentIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT studentId, firstName, lastName FROM students")) {
            System.out.println("Executing query: SELECT studentId, firstName, lastName FROM students"); // Debug
            while (rs.next()) {
                studentIds.add(rs.getInt("studentId"));
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                studentNames.add(fullName);
            }
            if (studentNames.isEmpty()) {
                studentComboBox.addItem("No students available");
                studentComboBox.setEnabled(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        studentComboBox = new JComboBox<>(studentNames);

        // Load courses for dropdown
        Vector<String> courseNames = new Vector<>();
        courseIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT courseId, courseName FROM courses")) {
            System.out.println("Executing query: SELECT courseId, courseName FROM courses"); // Debug
            while (rs.next()) {
                courseIds.add(rs.getInt("courseId"));
                courseNames.add(rs.getString("courseName"));
            }
            if (courseNames.isEmpty()) {
                courseComboBox.addItem("No courses available");
                courseComboBox.setEnabled(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching courses: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        courseComboBox = new JComboBox<>(courseNames);

        // Grade dropdown
        String[] gradeOptions = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        gradeComboBox = new JComboBox<>(gradeOptions);

        semesterField = new JTextField(20);
        yearField = new JTextField(20);

        formPanel.add(new JLabel("Student:"));
        formPanel.add(studentComboBox);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseComboBox);
        formPanel.add(new JLabel("Grade:"));
        formPanel.add(gradeComboBox);
        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterField);
        formPanel.add(new JLabel("Academic Year:"));
        formPanel.add(yearField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        addButton = new HoverButton("Add Grade");
        updateButton = new HoverButton("Update");
        deleteButton = new HoverButton("Delete");
        clearButton = new HoverButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        formPanel.add(new JLabel(""));
        formPanel.add(buttonPanel);

        // Table
        String[] columns = {"ID", "Student", "Course", "Grade", "Semester", "Academic Year"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        gradeTable = new JTable(tableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(gradeTable);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Grade Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Content area
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        contentPanel.setOpaque(false);
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add to panel
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(e -> addGrade());
        updateButton.addActionListener(e -> updateGrade());
        deleteButton.addActionListener(e -> deleteGrade());
        clearButton.addActionListener(e -> clearForm());

        gradeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = gradeTable.getSelectedRow();
                System.out.println("Row selected: " + selectedRow); // Debug
                if (selectedRow != -1) {
                    populateFormFromTable();
                }
            }
        });
    }

    // New method to refresh student dropdown
    public void refreshStudents() {
        Vector<String> studentNames = new Vector<>();
        studentIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT studentId, firstName, lastName FROM students")) {
            System.out.println("Executing query: SELECT studentId, firstName, lastName FROM students"); // Debug
            while (rs.next()) {
                studentIds.add(rs.getInt("studentId"));
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                studentNames.add(fullName);
            }
            // Update combo box
            studentComboBox.removeAllItems();
            if (studentNames.isEmpty()) {
                studentComboBox.addItem("No students available");
                studentComboBox.setEnabled(false);
            } else {
                for (String name : studentNames) {
                    studentComboBox.addItem(name);
                }
                studentComboBox.setEnabled(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error refreshing students: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        List<Grade> grades = GradeHandler.getAllGrades();
        System.out.println("Loaded " + grades.size() + " grades"); // Debug

        for (Grade grade : grades) {
            String studentName = GradeHandler.getStudentName(grade.getStudentId());
            String courseName = GradeHandler.getCourseName(grade.getCourseId());

            Vector<Object> row = new Vector<>();
            row.add(grade.getId());
            row.add(studentName);
            row.add(courseName);
            row.add(grade.getGrade());
            row.add(grade.getSemester());
            row.add(grade.getAcademicYear());

            tableModel.addRow(row);
        }
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades found in the database", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addGrade() {
        try {
            int selectedStudentIndex = studentComboBox.getSelectedIndex();
            int selectedCourseIndex = courseComboBox.getSelectedIndex();
            String gradeValue = (String) gradeComboBox.getSelectedItem();
            String semester = semesterField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());

            if (selectedStudentIndex == -1 || selectedCourseIndex == -1 || semester.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Grade grade = new Grade();
            grade.setStudentId(studentIds.get(selectedStudentIndex));
            grade.setCourseId(courseIds.get(selectedCourseIndex));
            grade.setGrade(gradeValue);
            grade.setSemester(semester);
            grade.setAcademicYear(year);

            if (GradeHandler.addGrade(grade)) {
                JOptionPane.showMessageDialog(this, "Grade added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add grade", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid academic year", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateGrade() {
        if (selectedGradeId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade to update", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int selectedStudentIndex = studentComboBox.getSelectedIndex();
            int selectedCourseIndex = courseComboBox.getSelectedIndex();
            String gradeValue = (String) gradeComboBox.getSelectedItem();
            String semester = semesterField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());

            if (selectedStudentIndex == -1 || selectedCourseIndex == -1 || semester.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Grade grade = new Grade();
            grade.setId(selectedGradeId);
            grade.setStudentId(studentIds.get(selectedStudentIndex));
            grade.setCourseId(courseIds.get(selectedCourseIndex));
            grade.setGrade(gradeValue);
            grade.setSemester(semester);
            grade.setAcademicYear(year);

            if (GradeHandler.updateGrade(grade)) {
                JOptionPane.showMessageDialog(this, "Grade updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update grade", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid academic year", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGrade() {
        if (selectedGradeId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this grade?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (GradeHandler.deleteGrade(selectedGradeId)) { // Fixed typo: deleteGradeTOML -> deleteGrade
                JOptionPane.showMessageDialog(this, "Grade deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete grade", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        if (studentComboBox.getItemCount() > 0) {
            studentComboBox.setSelectedIndex(0);
        }
        if (courseComboBox.getItemCount() > 0) {
            courseComboBox.setSelectedIndex(0);
        }
        if (gradeComboBox.getItemCount() > 0) {
            gradeComboBox.setSelectedIndex(0);
        }
        semesterField.setText("");
        yearField.setText("");
        selectedGradeId = -1;
        gradeTable.clearSelection();
    }

    private void populateFormFromTable() {
        int row = gradeTable.getSelectedRow();
        System.out.println("Populating form for row: " + row); // Debug
        if (row != -1) {
            selectedGradeId = (Integer) tableModel.getValueAt(row, 0);
            System.out.println("Selected Grade ID: " + selectedGradeId); // Debug

            String studentName = (String) tableModel.getValueAt(row, 1);
            String courseName = (String) tableModel.getValueAt(row, 2);
            String gradeValue = (String) tableModel.getValueAt(row, 3);
            String semester = (String) tableModel.getValueAt(row, 4);
            int year = (Integer) tableModel.getValueAt(row, 5);

            // Find student in combo box
            for (int i = 0; i < studentComboBox.getItemCount(); i++) {
                if (studentComboBox.getItemAt(i).equals(studentName)) {
                    studentComboBox.setSelectedIndex(i);
                    break;
                }
            }

            // Find course in combo box
            for (int i = 0; i < courseComboBox.getItemCount(); i++) {
                if (courseComboBox.getItemAt(i).equals(courseName)) {
                    courseComboBox.setSelectedIndex(i);
                    break;
                }
            }

            // Set grade value
            for (int i = 0; i < gradeComboBox.getItemCount(); i++) {
                if (gradeComboBox.getItemAt(i).equals(gradeValue)) {
                    gradeComboBox.setSelectedIndex(i);
                    break;
                }
            }

            semesterField.setText(semester);
            yearField.setText(String.valueOf(year));
        }
    }
}