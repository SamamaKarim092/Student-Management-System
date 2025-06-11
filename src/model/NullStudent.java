package model;

import java.util.Date;

public class NullStudent extends Student {

    public NullStudent() {
        // Initialize with default "null" values
        super();
    }

    @Override
    public int getStudentId() {
        return -1; // Invalid ID to indicate null object
    }

    @Override
    public String getFirstName() {
        return "No";
    }

    @Override
    public String getLastName() {
        return "Student Selected";
    }

    @Override
    public String getFullName() {
        return "No Student Selected";
    }

    @Override
    public String getGender() {
        return "N/A";
    }

    @Override
    public Date getDateOfBirth() {
        return new Date(0); // Default date
    }

    @Override
    public String getEmail() {
        return "no-email@example.com";
    }

    @Override
    public String getPhone() {
        return "N/A";
    }

    @Override
    public String getAddress() {
        return "No Address";
    }

    @Override
    public int getCourseId() {
        return -1;
    }

    @Override
    public String getCourseName() {
        return "No Course";
    }

    @Override
    public Date getEnrollmentDate() {
        return new Date(0);
    }

    @Override
    public void update(String message, Object data) {
        // Null object does nothing on updates
        // This prevents null pointer exceptions
    }

    // Override setters to do nothing (null object shouldn't be modified)
    @Override
    public void setStudentId(int studentId) {
        // Do nothing
    }

    @Override
    public void setFirstName(String firstName) {
        // Do nothing
    }

    @Override
    public void setLastName(String lastName) {
        // Do nothing
    }

    @Override
    public void setGender(String gender) {
        // Do nothing
    }

    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        // Do nothing
    }

    @Override
    public void setEmail(String email) {
        // Do nothing
    }

    @Override
    public void setPhone(String phone) {
        // Do nothing
    }

    @Override
    public void setAddress(String address) {
        // Do nothing
    }

    @Override
    public void setCourseId(int courseId) {
        // Do nothing
    }

    @Override
    public void setCourseName(String courseName) {
        // Do nothing
    }

    @Override
    public void setEnrollmentDate(Date enrollmentDate) {
        // Do nothing
    }

    @Override
    public String toString() {
        return "No Student Selected";
    }

    // Method to check if this is a null object
    public boolean isNull() {
        return true;
    }
}