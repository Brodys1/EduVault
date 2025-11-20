package cs151.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentDetailController {

    @FXML private TextField fullNameField;
    @FXML private TextField academicStatusField;
    @FXML private TextField employmentStatusField;
    @FXML private TextField jobDetailsField;
    @FXML private TextField programmingLanguagesField;
    @FXML private TextField databasesField;
    @FXML private TextField preferredRoleField;
    @FXML private TextField flagsField;

    @FXML private TableView<Comment> commentsTable;
    @FXML private TableColumn<Comment, String> colDate;
    @FXML private TableColumn<Comment, String> colComment;

    private StudentProfile student;

    /**
     * Set the student profile to display
     */
    public void setStudent(StudentProfile student) {
        this.student = student;
        if (student != null) {
            populateFields();
        }
    }

    /**
     * Populate all form fields with student data
     */
    private void populateFields() {
        fullNameField.setText(student.getFullName());
        academicStatusField.setText(student.getAcademicStatus());
        employmentStatusField.setText(student.getEmploymentStatus());
        jobDetailsField.setText(student.getJobDetails());
        programmingLanguagesField.setText(student.getLanguages());
        databasesField.setText(student.getDatabases());
        preferredRoleField.setText(student.getPreferredRole());
        flagsField.setText(student.getFlags());
    }


    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }
}
