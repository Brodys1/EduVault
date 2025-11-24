package cs151.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CommentDetailController {
    @FXML private Label dateLabel;
    @FXML private TextArea commentArea;

    public void setComment(Comment c) {
        if (c == null) {
            return;
        }
        else {
            // show date
            dateLabel.setText(c.getDate());
            // show full comment (read only)
            commentArea.setText(c.getComment());
        }
    }

    @FXML
    private void initialize() {
        if (commentArea != null) {
            commentArea.setEditable(false);
            commentArea.setWrapText(true);
        }
    }

    @FXML
    private void close() {
        dateLabel.getScene().getWindow().hide();
    }

}
