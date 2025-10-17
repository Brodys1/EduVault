package cs151.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Comparator;
import java.util.List;

public class StudentProfilesController {

    @FXML private TableView<StudentProfile> table;
    @FXML private TableColumn<StudentProfile, String> nameCol;
    @FXML private TableColumn<StudentProfile, String> statusCol;
    @FXML private TableColumn<StudentProfile, String> roleCol;
    @FXML private TableColumn<StudentProfile, String> languagesCol;

    private ObservableList<StudentProfile> students;

    @FXML
    public void initialize() {
        // Load data from repository
        students = cs151.application.StudentRepository.getAll();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("preferredRole"));
        languagesCol.setCellValueFactory(new PropertyValueFactory<>("languages"));

        // Sort alphabetically (case-insensitive)
        students.sort((a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));

        table.setItems(students);
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setScene(scene);
    }
}