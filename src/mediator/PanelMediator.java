// First, let's create the PanelMediator interface
package mediator;

public interface PanelMediator {
    void studentSelected(int studentId, String studentName);
    void courseSelected(int courseId, String courseName);
    void studentAdded();
    void studentUpdated();
    void studentDeleted();
    void courseAdded();
    void courseUpdated();
    void courseDeleted();
    void refreshGrades();
}

