// src/factory/CourseFactory.java
package factory;

import model.Course;
import java.util.Map;

public class CourseFactory {

    // Factory method to create a complete course
    public static Course createCourse(int courseId, String courseName, String department,
                                      int credits, String description) {
        // Validation
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }

        return new Course(courseId, courseName, department, credits, description);
    }

    // Factory method to create course with minimal information
    public static Course createBasicCourse(String courseName, String department) {
        return createCourse(0, courseName, department, 3, "No description provided");
    }

    // Factory method to create course from Map
    public static Course createCourseFromMap(Map<String, Object> courseData) {
        return createCourse(
                (Integer) courseData.getOrDefault("courseId", 0),
                (String) courseData.get("courseName"),
                (String) courseData.get("department"),
                (Integer) courseData.getOrDefault("credits", 3),
                (String) courseData.getOrDefault("description", "No description provided")
        );
    }

    // Factory methods for different course types
    public static Course createCoreCourse(String courseName, String department, int credits) {
        Course course = createCourse(0, courseName, department, credits, "Core course");
        return course;
    }

    public static Course createElectiveCourse(String courseName, String department, int credits) {
        Course course = createCourse(0, courseName, department, credits, "Elective course");
        return course;
    }

    public static Course createLabCourse(String courseName, String department) {
        Course course = createCourse(0, courseName + " Lab", department, 1, "Laboratory course");
        return course;
    }

    // Predefined course templates
    public static Course createComputerScienceCourse(String courseName) {
        return createCoreCourse(courseName, "Computer Science", 3);
    }

    public static Course createMathematicsCourse(String courseName) {
        return createCoreCourse(courseName, "Mathematics", 3);
    }

    public static Course createEngineeringCourse(String courseName) {
        return createCoreCourse(courseName, "Engineering", 4);
    }
}
