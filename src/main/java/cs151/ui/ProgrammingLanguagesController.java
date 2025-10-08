package cs151.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProgrammingLanguagesController {
    @FXML
    private TextField nameField;

    public void handleSave(ActionEvent event) {
        // TODO Print to console for now -> Later save to programming_languages.csv
        System.out.println("Saved: " + nameField.getText());
        nameField.clear();
    }

    public void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setScene(scene);
    }
}
