// src/factory/StudentFactory.java
package factory;

import model.Student;
import java.util.Date;
import java.util.Map;

public class StudentFactory {

    // Factory method to create a complete student
    public static Student createStudent(int studentId, String firstName, String lastName,
                                        String gender, Date dateOfBirth, String email,
                                        String phone, String address, int courseId,
                                        String courseName, Date enrollmentDate) {
        // Validation
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return new Student(studentId, firstName, lastName, gender, dateOfBirth,
                email, phone, address, courseId, courseName, enrollmentDate);
    }

    // Factory method to create student with minimal required information
    public static Student createBasicStudent(String firstName, String lastName, String email) {
        return createStudent(0, firstName, lastName, null, null, email,
                null, null, 0, null, new Date());
    }

    // Factory method to create student from Map (useful for form data)
    public static Student createStudentFromMap(Map<String, Object> studentData) {
        return createStudent(
                (Integer) studentData.getOrDefault("studentId", 0),
                (String) studentData.get("firstName"),
                (String) studentData.get("lastName"),
                (String) studentData.get("gender"),
                (Date) studentData.get("dateOfBirth"),
                (String) studentData.get("email"),
                (String) studentData.get("phone"),
                (String) studentData.get("address"),
                (Integer) studentData.getOrDefault("courseId", 0),
                (String) studentData.get("courseName"),
                (Date) studentData.getOrDefault("enrollmentDate", new Date())
        );
    }

    // Factory method for different student types
    public static Student createGraduateStudent(String firstName, String lastName, String email,
                                                int courseId, String courseName) {
        Student student = createBasicStudent(firstName, lastName, email);
        student.setCourseId(courseId);
        student.setCourseName("Graduate - " + courseName);
        return student;
    }

    public static Student createUndergraduateStudent(String firstName, String lastName, String email,
                                                     int courseId, String courseName) {
        Student student = createBasicStudent(firstName, lastName, email);
        student.setCourseId(courseId);
        student.setCourseName("Undergraduate - " + courseName);
        return student;
    }

    // Utility method for email validation
    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
