package cs151.ui;

import cs151.application.LangTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/student_profiles.fxml"));
        Scene scene = new Scene(loader.load());

        // Create a new Stage for the pop-out
        Stage popupStage = new Stage();
        popupStage.setTitle("All Student Profiles");
        popupStage.setScene(scene);

        // Optional: make it modal (blocks home interaction until closed)
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Optional: sizing
        popupStage.setWidth(650);
        popupStage.setHeight(450);
        popupStage.setResizable(false);

        // Show the pop-out window
        popupStage.show();
    }

}
