// src/factory/GradeFactory.java
package factory;

import model.Grade;
import java.util.Map;

public class GradeFactory {

    // Factory method to create a complete grade
    public static Grade createGrade(int id, int studentId, int courseId,
                                    String grade, String semester, int academicYear) {
        // Validation
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0");
        }
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be greater than 0");
        }
        if (grade == null || !isValidGrade(grade)) {
            throw new IllegalArgumentException("Invalid grade format");
        }
        if (academicYear <= 0) {
            throw new IllegalArgumentException("Academic year must be greater than 0");
        }

        return new Grade(id, studentId, courseId, grade, semester, academicYear);
    }

    // Factory method to create grade with current year
    public static Grade createCurrentYearGrade(int studentId, int courseId, String grade, String semester) {
        int currentYear = java.time.Year.now().getValue();
        return createGrade(0, studentId, courseId, grade, semester, currentYear);
    }

    // Factory method to create grade from Map
    public static Grade createGradeFromMap(Map<String, Object> gradeData) {
        return createGrade(
                (Integer) gradeData.getOrDefault("id", 0),
                (Integer) gradeData.get("studentId"),
                (Integer) gradeData.get("courseId"),
                (String) gradeData.get("grade"),
                (String) gradeData.get("semester"),
                (Integer) gradeData.get("academicYear")
        );
    }

    // Factory methods for different grade types
    public static Grade createPassingGrade(int studentId, int courseId, String semester, int academicYear) {
        return createGrade(0, studentId, courseId, "C", semester, academicYear);
    }

    public static Grade createExcellentGrade(int studentId, int courseId, String semester, int academicYear) {
        return createGrade(0, studentId, courseId, "A", semester, academicYear);
    }

    // Utility method for grade validation
    private static boolean isValidGrade(String grade) {
        String[] validGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"};
        for (String validGrade : validGrades) {
            if (validGrade.equals(grade)) {
                return true;
            }
        }
        return false;
    }
}
