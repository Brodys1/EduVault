package cs151.ui;

import cs151.application.CommentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class StudentCommentsController {

    @FXML
    private Label studentNameLabel;

    @FXML
    private ListView<String> commentsListView;

    @FXML
    private Button closeButton;

    private String studentName;

    /**
     * Loads and displays all comments for the specified student
     */
    public void loadComments(String studentName) {
        this.studentName = studentName;
        
        if (studentNameLabel != null) {
            studentNameLabel.setText("Comments for: " + studentName);
        }

        // Get all comments for this student from CommentRepository
        ObservableList<String> studentComments = CommentRepository.getAll()
                .stream()
                .filter(comment -> comment.getFullName().equals(studentName))
                .map(comment -> comment.getComment())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (studentComments.isEmpty()) {
            studentComments.add("No comments found for this student.");
        }

        commentsListView.setItems(studentComments);
    }

    @FXML
    private void initialize() {
        if (closeButton != null) {
            closeButton.setOnAction(e -> handleClose());
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
