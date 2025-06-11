//// Implementation of the PanelMediator
//package mediator;
//
//import view.StudentPanel;
//import view.CoursePanel;
//import view.GradePanel;
//
//public class PanelMediatorImpl implements PanelMediator {
//    private StudentPanel studentPanel;
//    private CoursePanel coursePanel;
//    private GradePanel gradePanel;
//
//    public void setStudentPanel(StudentPanel studentPanel) {
//        this.studentPanel = studentPanel;
//    }
//
//    public void setCoursePanel(CoursePanel coursePanel) {
//        this.coursePanel = coursePanel;
//    }
//
//    public void setGradePanel(GradePanel gradePanel) {
//        this.gradePanel = gradePanel;
//    }
//
//    @Override
//    public void studentSelected(int studentId, String studentName) {
//        // Notify grade panel to filter grades by student
//        if (gradePanel != null) {
//            gradePanel.filterGradesByStudent(studentId, studentName);
//        }
//        System.out.println("Student selected: " + studentName + " (ID: " + studentId + ")");
//    }
//
//    @Override
//    public void courseSelected(int courseId, String courseName) {
//        // Notify grade panel to filter grades by course
//        if (gradePanel != null) {
//            gradePanel.filterGradesByCourse(courseId, courseName);
//        }
//        System.out.println("Course selected: " + courseName + " (ID: " + courseId + ")");
//    }
//
//    @Override
//    public void studentAdded() {
//        // Refresh student dropdown in grade panel
//        if (gradePanel != null) {
//            gradePanel.refreshStudents();
//        }
//        System.out.println("Student added - refreshing related panels");
//    }
//
//    @Override
//    public void studentUpdated() {
//        // Refresh student data in grade panel
//        if (gradePanel != null) {
//            gradePanel.refreshStudents();
//            gradePanel.refreshGrades();
//        }
//        System.out.println("Student updated - refreshing related panels");
//    }
//
//    @Override
//    public void studentDeleted() {
//        // Refresh student data and grades
//        if (gradePanel != null) {
//            gradePanel.refreshStudents();
//            gradePanel.refreshGrades();
//        }
//        System.out.println("Student deleted - refreshing related panels");
//    }
//
//    @Override
//    public void courseAdded() {
//        // Refresh course dropdown in grade panel
//        if (gradePanel != null) {
//            gradePanel.refreshCourses();
//        }
//        System.out.println("Course added - refreshing related panels");
//    }
//
//    @Override
//    public void courseUpdated() {
//        // Refresh course data in grade panel
//        if (gradePanel != null) {
//            gradePanel.refreshCourses();
//            gradePanel.refreshGrades();
//        }
//        System.out.println("Course updated - refreshing related panels");
//    }
//
//    @Override
//    public void courseDeleted() {
//        // Refresh course data and grades
//        if (gradePanel != null) {
//            gradePanel.refreshCourses();
//            gradePanel.refreshGrades();
//        }
//        System.out.println("Course deleted - refreshing related panels");
//    }
//
//    @Override
//    public void refreshGrades() {
//        if (gradePanel != null) {
//            gradePanel.refreshGrades();
//        }
//    }
//}
