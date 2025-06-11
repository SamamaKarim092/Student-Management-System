package model;

import observer.Observer;
import java.util.Date;

public class Student implements Observer {
    private int studentId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private int courseId;
    private String courseName;
    private Date enrollmentDate;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor
    public Student(int studentId, String firstName, String lastName, String gender,
                   Date dateOfBirth, String email, String phone, String address,
                   int courseId, String courseName, Date enrollmentDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.courseId = courseId;
        this.courseName = courseName;
        this.enrollmentDate = enrollmentDate;
    }

    // Observer pattern implementation
    @Override
    public void update(String message, Object data) {
        if (data instanceof Grade) {
            Grade grade = (Grade) data;
            if (grade.getStudentId() == this.studentId) {
                System.out.println("Student " + getFullName() + " received notification: " + message);
                // Here you could also update student's internal state or trigger UI updates
            }
        }
    }

    // Getters and setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    // Add this method to the Student class
    public boolean isNull() {
        return false; // Regular students are not null objects
    }

    @Override
    public String toString() {
        return "Student [ID=" + studentId + ", Name=" + getFullName() + ", Course=" + courseName + "]";
    }
}