package cs151.ui;

import cs151.application.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditStudentController extends StudentController {

    private StudentProfile currentStudent;

    public void loadStudent(StudentProfile student) {
        this.currentStudent = student;

        // Prefill fields
        fullNameField.setText(student.getFullName());
        academicStatusCombo.setValue(student.getAcademicStatus());
        if ("Employed".equals(student.getEmploymentStatus())) {
            employedRadio.setSelected(true);
            jobDetailsField.setText(student.getJobDetails());
        } else {
            notEmployedRadio.setSelected(true);
        }

        preferredRoleCombo.setValue(student.getPreferredRole());
        commentsArea.setText(student.getComments());
        whitelistCheck.setSelected("Whitelist".equals(student.getFlags()));
        blacklistCheck.setSelected("Blacklist".equals(student.getFlags()));
    }

    @FXML
    private void saveEditedProfile() {
        if (!validateForm()) {
            return;
        }

        // Remove old entry
        StudentRepository.getAll().remove(currentStudent);

        // Save new updated entry
        StudentProfile updated = new StudentProfile(
                fullNameField.getText().trim(),
                academicStatusCombo.getValue(),
                employedRadio.isSelected() ? "Employed" : "Not Employed",
                jobDetailsField.getText().trim(),
                String.join(", ", selectedLanguages),
                String.join(", ", selectedDatabases),
                preferredRoleCombo.getValue(),
                commentsArea.getText().trim(),
                whitelistCheck.isSelected() ? "Whitelist" :
                        blacklistCheck.isSelected() ? "Blacklist" : "None"
        );

        StudentRepository.add(updated);

        statusLabel.setText("Profile updated successfully!");
        statusLabel.setStyle("-fx-text-fill: blue;");
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/search.fxml")));
        stage.setScene(scene);
    }
}