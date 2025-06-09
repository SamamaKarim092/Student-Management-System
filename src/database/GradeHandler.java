package database;

import model.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeHandler {
    public static List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT id, studentId, courseId, grade, semester, academic_year FROM grades";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setId(rs.getInt("id"));
                grade.setStudentId(rs.getInt("studentId"));
                grade.setCourseId(rs.getInt("courseId"));
                grade.setGrade(rs.getString("grade"));
                grade.setSemester(rs.getString("semester"));
                grade.setAcademicYear(rs.getInt("academic_year"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static String getStudentName(int studentId) {
        String query = "SELECT firstName, lastName FROM students WHERE studentId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("firstName") + " " + rs.getString("lastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Student";
    }

    public static String getCourseName(int courseId) {
        String query = "SELECT courseName FROM courses WHERE courseId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("courseName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Unknown Course";
    }

    public static boolean addGrade(Grade grade) {
        String query = "INSERT INTO grades (studentId, courseId, grade, semester, academic_year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setString(3, grade.getGrade());
            pstmt.setString(4, grade.getSemester());
            pstmt.setInt(5, grade.getAcademicYear());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateGrade(Grade grade) {
        String query = "UPDATE grades SET studentId = ?, courseId = ?, grade = ?, semester = ?, academic_year = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setString(3, grade.getGrade());
            pstmt.setString(4, grade.getSemester());
            pstmt.setInt(5, grade.getAcademicYear());
            pstmt.setInt(6, grade.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteGrade(int gradeId) {
        String query = "DELETE FROM grades WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, gradeId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}