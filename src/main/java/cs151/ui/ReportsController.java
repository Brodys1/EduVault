package cs151.ui;

import cs151.application.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsController {

    @FXML private RadioButton whitelistRadio;
    @FXML private RadioButton blacklistRadio;
    @FXML private TableView<StudentProfile> reportsTable;

    @FXML private TableColumn<StudentProfile, String> colName;
    @FXML private TableColumn<StudentProfile, String> colStatus;
    @FXML private TableColumn<StudentProfile, String> colLanguages;
    @FXML private TableColumn<StudentProfile, String> colDatabases;
    @FXML private TableColumn<StudentProfile, String> colRole;

    private final ObservableList<StudentProfile> displayedStudents = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind table columns
        colName.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getFullName()));
        colStatus.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getAcademicStatus()));
        colLanguages.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getLanguages()));
        colDatabases.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDatabases()));
        colRole.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getPreferredRole()));

        reportsTable.setItems(displayedStudents);

        // Default: show whitelisted
        whitelistRadio.setSelected(true);
        updateTable("Whitelist");
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        if (whitelistRadio.isSelected()) {
            updateTable("Whitelist");
        } else if (blacklistRadio.isSelected()) {
            updateTable("Blacklist");
        }
    }

    private void updateTable(String flag) {
        displayedStudents.setAll(
                StudentRepository.getAll().filtered(s -> s.getFlags().equalsIgnoreCase(flag))
        );
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/home.fxml"));
            Parent root = loader.load();
            Scene scene = ((Button) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot return to Home.").show();
        }
    }
}