package view;

import model.Grade;
import database.GradeHandler;
import database.DatabaseConnection;
import components.HoverButton;
import components.RoundedPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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
    private JTextField searchField;
    private HoverButton addButton, updateButton, deleteButton, clearButton, searchButton;

    private int selectedGradeId = -1;
    private Vector<Integer> studentIds = new Vector<>();
    private Vector<Integer> courseIds = new Vector<>();

    public GradePanel() {
        setBackground(new Color(240, 242, 245));
        setLayout(new BorderLayout());

        initComponents();
        loadGrades();
    }

    private void initComponents() {
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Grade Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(new Color(52, 73, 94));
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(searchField);

        searchButton = new HoverButton("Search");
        searchButton.setDefaultColor(new Color(52, 152, 219));
        searchButton.addActionListener(e -> searchGrades());
        searchPanel.add(searchButton);

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
        gbc.insets = new Insets(8, 8, 8, 8);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Load dropdowns data
        loadDropdownData();

        // Student
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel studentLabel = new JLabel("Student:");
        studentLabel.setFont(labelFont);
        studentLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(studentLabel, gbc);

        gbc.gridx = 1;
        studentComboBox.setFont(inputFont);
        studentComboBox.setPreferredSize(new Dimension(200, 35));
        studentComboBox.setBackground(Color.WHITE);
        studentComboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(studentComboBox, gbc);

        // Course
        gbc.gridx = 2;
        gbc.gridy = 0;
        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setFont(labelFont);
        courseLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(courseLabel, gbc);

        gbc.gridx = 3;
        courseComboBox.setFont(inputFont);
        courseComboBox.setPreferredSize(new Dimension(200, 35));
        courseComboBox.setBackground(Color.WHITE);
        courseComboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(courseComboBox, gbc);

        // Grade
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel gradeLabel = new JLabel("Grade:");
        gradeLabel.setFont(labelFont);
        gradeLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(gradeLabel, gbc);

        gbc.gridx = 1;
        String[] gradeOptions = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        gradeComboBox = new JComboBox<>(gradeOptions);
        gradeComboBox.setFont(inputFont);
        gradeComboBox.setPreferredSize(new Dimension(200, 35));
        gradeComboBox.setBackground(Color.WHITE);
        gradeComboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(gradeComboBox, gbc);

        // Semester
        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setFont(labelFont);
        semesterLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(semesterLabel, gbc);

        gbc.gridx = 3;
        semesterField = new JTextField();
        semesterField.setFont(inputFont);
        semesterField.setPreferredSize(new Dimension(200, 35));
        semesterField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(semesterField, gbc);

        // Academic Year
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel yearLabel = new JLabel("Academic Year:");
        yearLabel.setFont(labelFont);
        yearLabel.setForeground(new Color(52, 73, 94));
        formPanel.add(yearLabel, gbc);

        gbc.gridx = 1;
        yearField = new JTextField();
        yearField.setFont(inputFont);
        yearField.setPreferredSize(new Dimension(200, 35));
        yearField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(yearField, gbc);

        // Buttons Panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(20, 8, 8, 8);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        addButton = new HoverButton("Add Grade");
        addButton.setDefaultColor(new Color(46, 204, 113));
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.addActionListener(e -> addGrade());
        buttonPanel.add(addButton);

        updateButton = new HoverButton("Update");
        updateButton.setDefaultColor(new Color(52, 152, 219));
        updateButton.setPreferredSize(new Dimension(120, 40));
        updateButton.addActionListener(e -> updateGrade());
        buttonPanel.add(updateButton);

        deleteButton = new HoverButton("Delete");
        deleteButton.setDefaultColor(new Color(231, 76, 60));
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(e -> deleteGrade());
        buttonPanel.add(deleteButton);

        clearButton = new HoverButton("Clear");
        clearButton.setDefaultColor(new Color(149, 165, 166));
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.addActionListener(e -> clearForm());
        buttonPanel.add(clearButton);

        formPanel.add(buttonPanel, gbc);

        contentPanel.add(formPanel, BorderLayout.NORTH);

        // Table panel
        RoundedPanel tablePanel = new RoundedPanel(15, Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table
        String[] columns = {"ID", "Student Name", "Course Name", "Grade", "Semester", "Academic Year"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        gradeTable = new JTable(tableModel);
        gradeTable.setRowHeight(40);
        gradeTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gradeTable.setSelectionBackground(new Color(52, 152, 219, 40));
        gradeTable.setSelectionForeground(new Color(44, 62, 80));
        gradeTable.setGridColor(new Color(220, 221, 225));
        gradeTable.setShowGrid(true);
        gradeTable.setIntercellSpacing(new Dimension(1, 1));

        // Style the header
        JTableHeader header = gradeTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        // Custom cell renderer
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }

                // Center align columns
                if (column == 0 || column == 3 || column == 4 || column == 5) { // ID, Grade, Semester, Year
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);
                }

                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        };

        // Apply renderer to all columns
        for (int i = 0; i < gradeTable.getColumnCount(); i++) {
            gradeTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Set column widths
        gradeTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        gradeTable.getColumnModel().getColumn(0).setMaxWidth(80);
        gradeTable.getColumnModel().getColumn(0).setMinWidth(50);
        gradeTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Student
        gradeTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Course
        gradeTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Grade
        gradeTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Semester
        gradeTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Year

        // Add mouse listener
        gradeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int selectedRow = gradeTable.getSelectedRow();
                if (selectedRow != -1) {
                    populateFormFromTable();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add table info label
        JLabel tableInfoLabel = new JLabel("Click on a row to select and edit grade details");
        tableInfoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        tableInfoLabel.setForeground(new Color(127, 140, 141));
        tableInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.add(tableInfoLabel, BorderLayout.SOUTH);

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Add search on Enter key
        searchField.addActionListener(e -> searchGrades());
    }

    private void loadDropdownData() {
        // Load students for dropdown
        Vector<String> studentNames = new Vector<>();
        studentIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT studentId, firstName, lastName FROM students")) {

            while (rs.next()) {
                studentIds.add(rs.getInt("studentId"));
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                studentNames.add(fullName);
            }

            if (studentNames.isEmpty()) {
                studentNames.add("No students available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        studentComboBox = new JComboBox<>(studentNames);
        if (studentNames.get(0).equals("No students available")) {
            studentComboBox.setEnabled(false);
        }

        // Load courses for dropdown
        Vector<String> courseNames = new Vector<>();
        courseIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT courseId, courseName FROM courses")) {

            while (rs.next()) {
                courseIds.add(rs.getInt("courseId"));
                courseNames.add(rs.getString("courseName"));
            }

            if (courseNames.isEmpty()) {
                courseNames.add("No courses available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching courses: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        courseComboBox = new JComboBox<>(courseNames);
        if (courseNames.get(0).equals("No courses available")) {
            courseComboBox.setEnabled(false);
        }
    }

    private void searchGrades() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadGrades();
            return;
        }

        tableModel.setRowCount(0);
        List<Grade> allGrades = GradeHandler.getAllGrades();

        for (Grade grade : allGrades) {
            String studentName = GradeHandler.getStudentName(grade.getStudentId()).toLowerCase();
            String courseName = GradeHandler.getCourseName(grade.getCourseId()).toLowerCase();
            String gradeValue = grade.getGrade().toLowerCase();
            String semester = grade.getSemester().toLowerCase();

            if (studentName.contains(searchTerm) ||
                    courseName.contains(searchTerm) ||
                    gradeValue.contains(searchTerm) ||
                    semester.contains(searchTerm)) {

                Vector<Object> row = new Vector<>();
                row.add(grade.getId());
                row.add(GradeHandler.getStudentName(grade.getStudentId()));
                row.add(GradeHandler.getCourseName(grade.getCourseId()));
                row.add(grade.getGrade());
                row.add(grade.getSemester());
                row.add(grade.getAcademicYear());
                tableModel.addRow(row);
            }
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No matching grades found.",
                    "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void refreshStudents() {
        Vector<String> studentNames = new Vector<>();
        studentIds = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT studentId, firstName, lastName FROM students")) {

            while (rs.next()) {
                studentIds.add(rs.getInt("studentId"));
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                studentNames.add(fullName);
            }

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
            JOptionPane.showMessageDialog(this, "Error refreshing students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        List<Grade> grades = GradeHandler.getAllGrades();

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
    }

    private void addGrade() {
        try {
            int selectedStudentIndex = studentComboBox.getSelectedIndex();
            int selectedCourseIndex = courseComboBox.getSelectedIndex();
            String gradeValue = (String) gradeComboBox.getSelectedItem();
            String semester = semesterField.getText().trim();
            String yearText = yearField.getText().trim();

            if (selectedStudentIndex == -1 || selectedCourseIndex == -1 ||
                    semester.isEmpty() || yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int year = Integer.parseInt(yearText);

            Grade grade = new Grade();
            grade.setStudentId(studentIds.get(selectedStudentIndex));
            grade.setCourseId(courseIds.get(selectedCourseIndex));
            grade.setGrade(gradeValue);
            grade.setSemester(semester);
            grade.setAcademicYear(year);

            if (GradeHandler.addGrade(grade)) {
                JOptionPane.showMessageDialog(this, "Grade added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add grade",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid academic year",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateGrade() {
        if (selectedGradeId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade to update",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int selectedStudentIndex = studentComboBox.getSelectedIndex();
            int selectedCourseIndex = courseComboBox.getSelectedIndex();
            String gradeValue = (String) gradeComboBox.getSelectedItem();
            String semester = semesterField.getText().trim();
            String yearText = yearField.getText().trim();

            if (selectedStudentIndex == -1 || selectedCourseIndex == -1 ||
                    semester.isEmpty() || yearText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int year = Integer.parseInt(yearText);

            Grade grade = new Grade();
            grade.setId(selectedGradeId);
            grade.setStudentId(studentIds.get(selectedStudentIndex));
            grade.setCourseId(courseIds.get(selectedCourseIndex));
            grade.setGrade(gradeValue);
            grade.setSemester(semester);
            grade.setAcademicYear(year);

            if (GradeHandler.updateGrade(grade)) {
                JOptionPane.showMessageDialog(this, "Grade updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update grade",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid academic year",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGrade() {
        if (selectedGradeId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a grade to delete",
                    "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this grade?\nThis action cannot be undone.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (GradeHandler.deleteGrade(selectedGradeId)) {
                JOptionPane.showMessageDialog(this, "Grade deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete grade",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        if (studentComboBox.getItemCount() > 0 && studentComboBox.isEnabled()) {
            studentComboBox.setSelectedIndex(0);
        }
        if (courseComboBox.getItemCount() > 0 && courseComboBox.isEnabled()) {
            courseComboBox.setSelectedIndex(0);
        }
        if (gradeComboBox.getItemCount() > 0) {
            gradeComboBox.setSelectedIndex(0);
        }
        semesterField.setText("");
        yearField.setText("");
        searchField.setText("");
        selectedGradeId = -1;
        gradeTable.clearSelection();
    }

    private void populateFormFromTable() {
        int row = gradeTable.getSelectedRow();
        if (row != -1) {
            selectedGradeId = (Integer) tableModel.getValueAt(row, 0);

            String studentName = (String) tableModel.getValueAt(row, 1);
            String courseName = (String) tableModel.getValueAt(row, 2);
            String gradeValue = (String) tableModel.getValueAt(row, 3);
            String semester = (String) tableModel.getValueAt(row, 4);
            int year = (Integer) tableModel.getValueAt(row, 5);

            for (int i = 0; i < studentComboBox.getItemCount(); i++) {
                if (studentComboBox.getItemAt(i).equals(studentName)) {
                    studentComboBox.setSelectedIndex(i);
                    break;
                }
            }

            for (int i = 0; i < courseComboBox.getItemCount(); i++) {
                if (courseComboBox.getItemAt(i).equals(courseName)) {
                    courseComboBox.setSelectedIndex(i);
                    break;
                }
            }

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