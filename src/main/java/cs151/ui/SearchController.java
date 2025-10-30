package cs151.ui;

import cs151.application.StudentRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.*;
import javafx.scene.control.Button;

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
    private TableColumn<StudentProfile, Void> colActions;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFullName()));
        colStatus.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAcademicStatus()));
        colLanguages.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLanguages()));
        colDatabases.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDatabases()));
        colRole.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPreferredRole()));
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(e -> {
                    StudentProfile selected = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/cs151/application/edit_student.fxml"));
                        Scene scene = new Scene(loader.load());

                        // Pass student to edit page
                        cs151.ui.EditStudentController controller = loader.getController();
                        controller.loadStudent(selected);

                        Stage stage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            resultsTable.setItems(StudentRepository.getAll());
        } else {
            ObservableList<StudentProfile> results = StudentRepository.searchStudents(query);
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