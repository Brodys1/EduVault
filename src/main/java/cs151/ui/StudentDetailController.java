package cs151.ui;

import cs151.application.CommentRepository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentDetailController {

    // ---- Student info fields (now Labels) ----
    @FXML private Label fullNameField;
    @FXML private Label academicStatusField;
    @FXML private Label employmentStatusField;
    @FXML private Label jobDetailsField;
    @FXML private Label programmingLanguagesField;
    @FXML private Label databasesField;
    @FXML private Label preferredRoleField;
    @FXML private Label flagsField;

    // ----  Comments table ---- 
    @FXML private TableView<Comment> commentsTable;
    @FXML private TableColumn<Comment, String> colDate;
    @FXML private TableColumn<Comment, String> colComment;

    private StudentProfile student;

    @FXML
    private void initialize() {

        if (colDate != null) {
            colDate.setCellValueFactory(cellData ->
                    new ReadOnlyStringWrapper(
                            cellData.getValue() != null && cellData.getValue().getDate() != null
                                    ? cellData.getValue().getDate()
                                    : ""
                    ));
        }

        if (colComment != null) {
            colComment.setCellValueFactory(cellData ->
                    new ReadOnlyStringWrapper(
                            cellData.getValue() != null && cellData.getValue().getComment() != null
                                    ? cellData.getValue().getComment()
                                    : ""
                    ));

            colComment.setCellFactory(column -> new TableCell<Comment, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }

                    setWrapText(false);
                    setStyle("-fx-text-overrun: ellipsis;");
                }
            });
        }
    }

    public void setStudent(StudentProfile student) {
        this.student = student;
        if (student != null) {
            populateFields();
            loadCommentsForStudent();
        }
    }

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

    private void loadCommentsForStudent() {
        if (commentsTable == null || student == null) {
            return;
        }

        String targetName = student.getFullName() == null
                ? ""
                : student.getFullName().trim();

        System.out.println("DEBUG: loading comments for '" + targetName + "'");

        ObservableList<Comment> filtered = FXCollections.observableArrayList();

        for (Comment c : CommentRepository.getAll()) {
            System.out.println("DEBUG: repo row -> name='" +
                    c.getFullName() + "', date='" + c.getDate() +
                    "', comment='" + c.getComment() + "'");

            if (c.getFullName() != null &&
                    c.getFullName().trim().equalsIgnoreCase(targetName)) {
                filtered.add(c);
            }
        }

        System.out.println("DEBUG: filtered size = " + filtered.size());

        commentsTable.setItems(filtered);
        commentsTable.refresh();
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }
}
