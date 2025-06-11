// src/factory/ModelFactory.java (Abstract Factory)
package factory;

import model.Student;
import model.Course;
import model.Grade;
import java.util.Date;
import java.util.Map;

public class ModelFactory {

    // Enum for model types
    public enum ModelType {
        STUDENT, COURSE, GRADE
    }

    // Generic factory method
    public static Object createModel(ModelType type, Map<String, Object> data) {
        switch (type) {
            case STUDENT:
                return StudentFactory.createStudentFromMap(data);
            case COURSE:
                return CourseFactory.createCourseFromMap(data);
            case GRADE:
                return GradeFactory.createGradeFromMap(data);
            default:
                throw new IllegalArgumentException("Unknown model type: " + type);
        }
    }

    // Convenience methods
    public static Student createStudent(Map<String, Object> data) {
        return StudentFactory.createStudentFromMap(data);
    }

    public static Course createCourse(Map<String, Object> data) {
        return CourseFactory.createCourseFromMap(data);
    }

    public static Grade createGrade(Map<String, Object> data) {
        return GradeFactory.createGradeFromMap(data);
    }
}