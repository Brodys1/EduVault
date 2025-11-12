package cs151.ui;

import cs151.application.CommentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.stream.Collectors;
public class StudentCommentsController {

    @FXML private Label studentNameLabel;

    // Existing list UI
    @FXML private ListView<String> commentsListView;

    // New input controls for #3 (non-persistent add)
    @FXML private TextArea newCommentArea;
    @FXML private Button addCommentButton;

    @FXML private Button closeButton;

    // Backing list for just this student's comments (strings)
    private final ObservableList<String> studentComments = FXCollections.observableArrayList();

    // Name of the student this page is showing
    private String studentName;

    /**
     * Called by the page that opens this dialog.
     */
    public void loadComments(String studentName) {
        this.studentName = studentName;
        if (studentNameLabel != null) {
            studentNameLabel.setText(studentName);
        }

        // Filter the global repository into ONLY this student's comments (as strings)
        var myComments = CommentRepository.getAll()
                .stream()
                .filter(c -> c.getFullName().equalsIgnoreCase(studentName))
                .map(Comment::getComment)
                .collect(Collectors.toList());

        studentComments.setAll(myComments);
        commentsListView.setItems(studentComments);
    }

    @FXML
    private void initialize() {
        // Wire Close button
        if (closeButton != null) {
            closeButton.setOnAction(e -> handleClose());
        }

        // Simple UX: disable Add until something is typed
        if (addCommentButton != null && newCommentArea != null) {
            addCommentButton.setDisable(true);
            newCommentArea.textProperty().addListener((obs, oldV, newV) -> {
                addCommentButton.setDisable(newV == null || newV.trim().isEmpty());
            });
        }
    }
    @FXML
    private void handleAddComment() {
        if (studentName == null || studentName.isBlank()) {
            showError("No student selected.");
            return;
        }
        String text = newCommentArea.getText();
        if (text == null || text.trim().isEmpty()) {
            showInfo("Please type a comment first.");
            return;
        }

        // In-memory only: add a Comment object to the global list (NO save),
        // and update this page's local view.
        CommentRepository.getAll().add(new Comment(studentName, text.trim()));
        studentComments.add(0, text.trim()); // put newest at top
        commentsListView.refresh();

        newCommentArea.clear();
        showInfo("Comment added (in memory only — not saved to file).");
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // Small helpers
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
}
