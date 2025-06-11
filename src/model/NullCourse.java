package model;

public class NullCourse extends Course {

    public NullCourse() {
        // Initialize with default "null" values
        super();
    }

    @Override
    public int getCourseId() {
        return -1; // Invalid ID to indicate null object
    }

    @Override
    public String getCourseName() {
        return "No Course Selected";
    }

    @Override
    public String getDepartment() {
        return "N/A";
    }

    @Override
    public int getCredits() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "No course description available";
    }

    // Override setters to do nothing (null object shouldn't be modified)
    @Override
    public void setCourseId(int courseId) {
        // Do nothing
    }

    @Override
    public void setCourseName(String courseName) {
        // Do nothing
    }

    @Override
    public void setDepartment(String department) {
        // Do nothing
    }

    @Override
    public void setCredits(int credits) {
        // Do nothing
    }

    @Override
    public void setDescription(String description) {
        // Do nothing
    }

    @Override
    public String toString() {
        return "No Course Selected";
    }

    // Method to check if this is a null object
    public boolean isNull() {
        return true;
    }
}