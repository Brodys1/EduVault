package cs151.ui;

import cs151.application.StudentRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SearchController {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<StudentProfile> resultsTable;
    @FXML
    private TableColumn<StudentProfile, String> colName;
    @FXML
    private TableColumn<StudentProfile, String> colStatus;
    @FXML
    private TableColumn<StudentProfile, String> colLanguages;
    @FXML
    private TableColumn<StudentProfile, String> colDatabases;
    @FXML
    private TableColumn<StudentProfile, String> colRole;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFullName()));
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAcademicStatus()));
        colLanguages.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLanguages()));
        colDatabases.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDatabases()));
        colRole.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPreferredRole()));
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            resultsTable.setItems(StudentRepository.getAll());
        } else {
            ObservableList<StudentProfile> results = StudentRepository.search(query);
            resultsTable.setItems(results);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setScene(scene);
    }
}