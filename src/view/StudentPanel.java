package view;

import components.HoverButton;
import components.RoundedPanel;
import database.DatabaseConnection;
import model.Student;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import com.toedter.calendar.JDateChooser;
import mediator.PanelMediator;

public class StudentPanel extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtFirstName, txtLastName, txtEmail, txtPhone, txtAddress;
    private JComboBox<String> cbGender, cbCourse;
    private JDateChooser dateChooser, enrollmentDateChooser;
    private HoverButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTextField txtSearch;
    private JSplitPane splitPane;
    private PanelMediator mediator;

    // Enhanced color scheme
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color SECONDARY_COLOR = new Color(149, 165, 166);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TABLE_HEADER_COLOR = new Color(52, 73, 94);
    private static final Color TABLE_ROW_COLOR = new Color(255, 255, 255);
    private static final Color TABLE_ALT_ROW_COLOR = new Color(248, 249, 250);
    private static final Color TABLE_SELECTION_COLOR = new Color(52, 152, 219, 50);
    private static final Color TABLE_HOVER_COLOR = new Color(52, 152, 219, 30);

    public StudentPanel() {
        setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 800)); // Set preferred size for proper proportions

        initComponents();
        loadStudentData();
    }

    public StudentPanel(PanelMediator mediator) {
        this();
        this.mediator = mediator;
    }

    public void setMediator(PanelMediator mediator) {
        this.mediator = mediator;
    }

    private void initComponents() {
        // Header panel with enhanced styling
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(0, 80)); // Fixed header height

        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Enhanced search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchLabel.setForeground(new Color(44, 53, 53));
        searchPanel.add(searchLabel);

        txtSearch = new JTextField(20);
        txtSearch.setPreferredSize(new Dimension(220, 38));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        searchPanel.add(txtSearch);

        btnSearch = new HoverButton("Search");
        btnSearch.setDefaultColor(PRIMARY_COLOR);
        btnSearch.setPreferredSize(new Dimension(80, 38));
        btnSearch.addActionListener((ActionEvent e) -> {
            searchStudents(txtSearch.getText().trim());
        });
        searchPanel.add(btnSearch);

        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Create split pane for form and table
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 25, 25, 25));
        splitPane.setDividerLocation(0.45); // Form takes 45% of available space
        splitPane.setResizeWeight(0.45); // Form gets 45% when resizing
        splitPane.setDividerSize(8);
        splitPane.setContinuousLayout(true);

        // Create form panel (top half)
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setOpaque(false);
        formContainer.setMinimumSize(new Dimension(0, 300)); // Minimum height for form

        // Enhanced form panel
        RoundedPanel formPanel = new RoundedPanel(20, Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 8, 6, 8); // Reduced vertical spacing

        // Form fields with enhanced styling
        addFormField(formPanel, gbc, "Student ID:", txtId = createStyledTextField(false), 0);
        addFormField(formPanel, gbc, "First Name:", txtFirstName = createStyledTextField(true), 1);
        addFormField(formPanel, gbc, "Last Name:", txtLastName = createStyledTextField(true), 2);
        addFormField(formPanel, gbc, "Gender:", cbGender = createStyledComboBox(new String[]{"Male", "Female", "Other"}), 3);
        addFormField(formPanel, gbc, "Date of Birth:", dateChooser = createStyledDateChooser(), 4);

        addFormField(formPanel, gbc, "Email:", txtEmail = createStyledTextField(true), 0, 2);
        addFormField(formPanel, gbc, "Phone:", txtPhone = createStyledTextField(true), 1, 2);
        addFormField(formPanel, gbc, "Address:", txtAddress = createStyledTextField(true), 2, 2);
        addFormField(formPanel, gbc, "Course:", cbCourse = createStyledComboBox(new String[]{}), 3, 2);
        addFormField(formPanel, gbc, "Enrollment Date:", enrollmentDateChooser = createStyledDateChooser(), 4, 2);

        loadCourses();
        enrollmentDateChooser.setDate(new Date());

        // Enhanced buttons panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(15, 8, 8, 8);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        btnAdd = createStyledButton("Add Student", SUCCESS_COLOR, 130, 38);
        btnAdd.addActionListener((ActionEvent e) -> {
            addStudent();
            if (mediator != null) mediator.studentAdded();
        });
        buttonPanel.add(btnAdd);

        btnUpdate = createStyledButton("Update", PRIMARY_COLOR, 100, 38);
        btnUpdate.addActionListener((ActionEvent e) -> {
            updateStudent();
            if (mediator != null) mediator.studentUpdated();
        });
        buttonPanel.add(btnUpdate);

        btnDelete = createStyledButton("Delete", DANGER_COLOR, 100, 38);
        btnDelete.addActionListener((ActionEvent e) -> {
            deleteStudent();
            if (mediator != null) mediator.studentDeleted();
        });
        buttonPanel.add(btnDelete);

        btnClear = createStyledButton("Clear Form", SECONDARY_COLOR, 120, 38);
        btnClear.addActionListener((ActionEvent e) -> clearForm());
        buttonPanel.add(btnClear);

        formPanel.add(buttonPanel, gbc);
        formContainer.add(formPanel, BorderLayout.CENTER);

        // Create table panel (bottom half)
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setMinimumSize(new Dimension(0, 350)); // Minimum height for table

        // Enhanced table panel
        RoundedPanel tablePanel = new RoundedPanel(20, Color.WHITE);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        // Table title
        JLabel tableTitle = new JLabel("Student Records");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setForeground(new Color(44, 62, 80));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        // Create enhanced table
        createEnhancedTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(0, 300)); // Ensure table takes enough space

        // Enhanced scrollbar styling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tableContainer.add(tablePanel, BorderLayout.CENTER);

        // Add components to split pane
        splitPane.setTopComponent(formContainer);
        splitPane.setBottomComponent(tableContainer);

        add(splitPane, BorderLayout.CENTER);
    }

    private void createEnhancedTable() {
        String[] columns = {"ID", "First Name", "Last Name", "Gender", "Date of Birth",
                "Email", "Phone", "Course", "Enrollment Date"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);

        // Enhanced table appearance - Increased row height for better visibility
        studentTable.setRowHeight(40); // Increased from 45 to ensure 5+ rows visible
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.setSelectionBackground(TABLE_SELECTION_COLOR);
        studentTable.setSelectionForeground(Color.BLACK);
        studentTable.setGridColor(new Color(220, 221, 225));
        studentTable.setShowGrid(true);
        studentTable.setIntercellSpacing(new Dimension(1, 1));
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Enhanced header styling
        JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));
        header.setBorder(BorderFactory.createEmptyBorder());

        // Custom cell renderer for alternating row colors and better formatting
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(TABLE_ROW_COLOR);
                    } else {
                        c.setBackground(TABLE_ALT_ROW_COLOR);
                    }
                }

                // Center align numeric columns
                if (column == 0) { // ID column
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
                return c;
            }
        });

        // Set preferred column widths
        setColumnWidths();

        // Add hover effect
        studentTable.addMouseMotionListener(new MouseAdapter() {
            private int lastRow = -1;

            @Override
            public void mouseMoved(MouseEvent e) {
                int row = studentTable.rowAtPoint(e.getPoint());
                if (row != lastRow) {
                    lastRow = row;
                    studentTable.repaint();
                }
            }
        });

        // Add click listener
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    displayStudentDetails(selectedRow);
                }
            }
        });
    }

    private void setColumnWidths() {
        int[] columnWidths = {60, 120, 120, 80, 120, 180, 120, 150, 130};
        for (int i = 0; i < columnWidths.length && i < studentTable.getColumnCount(); i++) {
            TableColumn column = studentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
            column.setMinWidth(columnWidths[i] - 20);
            column.setMaxWidth(columnWidths[i] + 50);
        }
    }

    private JTextField createStyledTextField(boolean editable) {
        JTextField textField = new JTextField(15);
        textField.setEditable(editable);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 32)); // Slightly smaller height
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(editable ? new Color(189, 195, 199) : new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(6, 12, 6, 12) // Reduced padding
        ));
        if (!editable) {
            textField.setBackground(new Color(248, 249, 250));
        }
        return textField;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setPreferredSize(new Dimension(200, 32)); // Slightly smaller height
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setPreferredSize(new Dimension(200, 32)); // Slightly smaller height
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return dateChooser;
    }

    private HoverButton createStyledButton(String text, Color color, int width, int height) {
        HoverButton button = new HoverButton(text);
        button.setDefaultColor(color);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText,
                              Component component, int row) {
        addFormField(panel, gbc, labelText, component, row, 0);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText,
                              Component component, int row, int columnOffset) {
        gbc.gridx = columnOffset;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Changed to BOLD
        label.setForeground(new Color(51, 57, 57));
        panel.add(label, gbc);

        gbc.gridx = columnOffset + 1;
        panel.add(component, gbc);

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
        tableModel.fireTableDataChanged();
        if (studentTable != null) {
            studentTable.revalidate();
            studentTable.repaint();
        }
    }

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

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchStudents(String keyword) {
        if (keyword.isEmpty()) {
            loadStudentData();
            return;
        }

        tableModel.setRowCount(0);
        ResultSet rs = null;
        try {
            String query = "SELECT s.studentId, s.firstName, s.lastName, s.gender, s.dateOfBirth, " +
                    "s.email, s.phone, c.courseName, s.enrollmentDate " +
                    "FROM Students s JOIN Courses c ON s.courseId = c.courseId " +
                    "WHERE s.firstName LIKE ? OR s.lastName LIKE ? OR s.email LIKE ? OR c.courseName LIKE ?";

            String searchKeyword = "%" + keyword + "%";
            rs = DatabaseConnection.executePreparedQuery(query, searchKeyword, searchKeyword,
                    searchKeyword, searchKeyword);

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
        txtSearch.setText("");
    }

    private boolean validateInputs() {
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()) {
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
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (at least 10 digits).",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}