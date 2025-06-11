package model;

import observer.Observer;
import java.util.ArrayList;
import java.util.List;

public class Grade {
    private int id;
    private int studentId;
    private int courseId;
    private String grade;
    private String semester;
    private int academicYear;

    // Observer pattern implementation
    private List<Observer> observers = new ArrayList<>();

    public Grade() {}

    public Grade(int id, int studentId, int courseId, String grade, String semester, int academicYear) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    // Observer pattern methods
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message, this);
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) {
        String oldGrade = this.grade;
        this.grade = grade;
        // Notify observers when grade changes
        notifyObservers("Grade updated from " + oldGrade + " to " + grade + " for student ID: " + studentId);
    }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public int getAcademicYear() { return academicYear; }
    public void setAcademicYear(int academicYear) { this.academicYear = academicYear; }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", grade='" + grade + '\'' +
                ", semester='" + semester + '\'' +
                ", academicYear=" + academicYear +
                '}';
    }
}