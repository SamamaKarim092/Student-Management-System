package view;

import components.HoverButton;
import components.RoundedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Grade;
import observer.Observer;

public class Dashboard extends JFrame implements Observer {
    private JPanel mainContent;
    private CardLayout cardLayout;
    private StudentPanel studentPanel;
    private CoursePanel coursePanel;
    private JPanel dashboardPanel;
    private JPanel gradePanel;
    private JLabel studentsCountLabel;
    private JLabel coursesCountLabel;
    private JLabel enrollmentsCountLabel;

    // Observer pattern implementation
    @Override
    public void update(String message, Object data) {
        if (data instanceof Grade) {
            System.out.println("Dashboard received notification: " + message);
            // Update dashboard statistics
            updateDashboardStats();
        }
    }

    private void updateDashboardStats() {
        // This method would refresh the dashboard counters
        // You could call your database to get updated counts
        SwingUtilities.invokeLater(() -> {
            // Refresh the dashboard display
            repaint();
        });
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 242, 245));
        panel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Dashboard cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardsPanel.setOpaque(false);

        // Students card
        RoundedPanel studentsCard = new RoundedPanel(15, Color.WHITE);
        studentsCard.setLayout(new BorderLayout());
        studentsCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        studentsCountLabel = new JLabel("248"); // Store reference
        studentsCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        studentsCountLabel.setForeground(new Color(41, 128, 185));

        JLabel studentsLabel = new JLabel("Total Students");
        studentsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel studentTextPanel = new JPanel();
        studentTextPanel.setLayout(new BoxLayout(studentTextPanel, BoxLayout.Y_AXIS));
        studentTextPanel.setOpaque(false);
        studentTextPanel.add(studentsCountLabel);
        studentTextPanel.add(studentsLabel);

        studentsCard.add(studentTextPanel, BorderLayout.CENTER);

        // Courses card
        RoundedPanel coursesCard = new RoundedPanel(15, Color.WHITE);
        coursesCard.setLayout(new BorderLayout());
        coursesCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        coursesCountLabel = new JLabel("42"); // Store reference
        coursesCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        coursesCountLabel.setForeground(new Color(46, 204, 113));

        JLabel coursesLabel = new JLabel("Total Courses");
        coursesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel courseTextPanel = new JPanel();
        courseTextPanel.setLayout(new BoxLayout(courseTextPanel, BoxLayout.Y_AXIS));
        courseTextPanel.setOpaque(false);
        courseTextPanel.add(coursesCountLabel);
        courseTextPanel.add(coursesLabel);

        coursesCard.add(courseTextPanel, BorderLayout.CENTER);

        // Active enrollments card
        RoundedPanel enrollmentsCard = new RoundedPanel(15, Color.WHITE);
        enrollmentsCard.setLayout(new BorderLayout());
        enrollmentsCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        enrollmentsCountLabel = new JLabel("189"); // Store reference
        enrollmentsCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        enrollmentsCountLabel.setForeground(new Color(243, 156, 18));

        JLabel enrollmentsLabel = new JLabel("Active Enrollments");
        enrollmentsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel enrollmentTextPanel = new JPanel();
        enrollmentTextPanel.setLayout(new BoxLayout(enrollmentTextPanel, BoxLayout.Y_AXIS));
        enrollmentTextPanel.setOpaque(false);
        enrollmentTextPanel.add(enrollmentsCountLabel);
        enrollmentTextPanel.add(enrollmentsLabel);

        enrollmentsCard.add(enrollmentTextPanel, BorderLayout.CENTER);

        cardsPanel.add(studentsCard);
        cardsPanel.add(coursesCard);
        cardsPanel.add(enrollmentsCard);

        panel.add(cardsPanel, BorderLayout.CENTER);

        return panel;
    }

    public Dashboard() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        initComponents();
        setTitle("Student Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Ensure GradePanel class exists and is properly defined
        gradePanel = new GradePanel();

        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Create main content area with card layout
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Create panels
        dashboardPanel = createDashboardPanel();
        studentPanel = new StudentPanel();
        coursePanel = new CoursePanel();

        // Add panels to card layout
        mainContent.add(dashboardPanel, "Dashboard");
        mainContent.add(studentPanel, "Students");
        mainContent.add(coursePanel, "Courses");
        mainContent.add(gradePanel, "Grades");

        add(mainContent, BorderLayout.CENTER);

        // Show dashboard by default
        cardLayout.show(mainContent, "Dashboard");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(47, 54, 64));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BorderLayout());

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(0, 100));
        logoPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel("SMS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        JLabel subtitleLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        logoPanel.add(subtitleLabel, BorderLayout.SOUTH);

        sidebar.add(logoPanel, BorderLayout.NORTH);

        // Menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Dashboard button
        HoverButton dashboardBtn = createMenuButton("Dashboard");
        dashboardBtn.setDefaultColor(new Color(47, 54, 64));
        dashboardBtn.setHoverColor(new Color(72, 84, 96));
        dashboardBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(mainContent, "Dashboard");
        });

        // Students button
        HoverButton studentsBtn = createMenuButton("Students");
        studentsBtn.setDefaultColor(new Color(47, 54, 64));
        studentsBtn.setHoverColor(new Color(72, 84, 96));
        studentsBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(mainContent, "Students");
        });

        // Courses button
        HoverButton coursesBtn = createMenuButton("Courses");
        coursesBtn.setDefaultColor(new Color(47, 54, 64));
        coursesBtn.setHoverColor(new Color(72, 84, 96));
        coursesBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(mainContent, "Courses");
        });

        // Grades button
        HoverButton gradesBtn = createMenuButton("Grades");
        gradesBtn.setDefaultColor(new Color(47, 54, 64));
        gradesBtn.setHoverColor(new Color(72, 84, 96));
        gradesBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(mainContent, "Grades");
        });

        // Exit button
        HoverButton exitBtn = createMenuButton("Exit");
        exitBtn.setDefaultColor(new Color(47, 54, 64));
        exitBtn.setHoverColor(new Color(214, 48, 49));
        exitBtn.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        menuPanel.add(dashboardBtn);
        menuPanel.add(studentsBtn);
        menuPanel.add(coursesBtn);
        menuPanel.add(gradesBtn);
        menuPanel.add(javax.swing.Box.createVerticalGlue());
        menuPanel.add(exitBtn);

        sidebar.add(menuPanel, BorderLayout.CENTER);

        return sidebar;
    }

    private HoverButton createMenuButton(String text) {
        HoverButton button = new HoverButton(text);
        button.setAlignmentX(0.5f);
        button.setMaximumSize(new Dimension(220, 50));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}