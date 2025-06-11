package model;

public class Course {
    private int courseId;
    private String courseName;
    private String department;
    private int credits;
    private String description;

    // Default constructor
    public Course() {
    }

    // Parameterized constructor
    public Course(int courseId, String courseName, String department, int credits, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
        this.credits = credits;
        this.description = description;
    }

    // Getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCredits() {
        return credits;
    }

    // Add this method to the Course class
    public boolean isNull() {
        return false; // Regular courses are not null objects
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return courseName;
    }
}