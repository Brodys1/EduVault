package cs151.ui;

import cs151.application.LangTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {

    public void handleDefineLanguages(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("/cs151/application/programming_languages.fxml")));
        stage.setScene(scene);
    }

    private static Stage langTableStage;

    public void handleLangTable(ActionEvent event) throws Exception {
        if (langTableStage != null && langTableStage.isShowing()) {
            langTableStage.toFront();
            return;
        }
        LangTable langTable = new LangTable();
        langTableStage = new Stage();
        langTable.start(langTableStage);
    }

    public void handleDefineStudentProfile(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("/cs151/application/student.fxml")));
        stage.setScene(scene);
    }

    public void handleViewAllStudents(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("/cs151/application/student_profiles.fxml")));
        stage.setTitle("All Student Profiles");
        stage.setScene(scene);
        stage.setResizable(true);
    }

    public void handleSearchStudents(ActionEvent event) {
        try {
            var url = getClass().getResource("/cs151/application/search.fxml");
            if (url == null) {
                throw new RuntimeException("Resource not found: /cs151/application/search.fxml");
            }
            FXMLLoader fxml = new FXMLLoader(url);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(fxml.load()));
            stage.setTitle("Search Student Profiles");
        } catch (Exception ex) {
            ex.printStackTrace();
            new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Failed to open Search page:\n" + ex.getMessage(),
                    javafx.scene.control.ButtonType.OK
            ).showAndWait();
        }
    }
}
