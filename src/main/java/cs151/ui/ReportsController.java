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
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ReportsController {

    @FXML
    private CheckBox allowlistCheck;
    @FXML
    private CheckBox denylistCheck;
    @FXML
    private TableView<StudentProfile> reportsTable;

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

    private final ObservableList<StudentProfile> displayedStudents = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Bind columns
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFullName()));
        colStatus.setCellValueFactory(
                c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAcademicStatus()));
        colLanguages
                .setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLanguages()));
        colDatabases
                .setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDatabases()));
        colRole.setCellValueFactory(
                c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPreferredRole()));

        reportsTable.setItems(displayedStudents);

        // Default: show all (both selected)
        allowlistCheck.setSelected(true);
        denylistCheck.setSelected(true);
        updateTable();

        // Auto-update when toggles change
        allowlistCheck.selectedProperty().addListener((obs, o, n) -> updateTable());
        denylistCheck.selectedProperty().addListener((obs, o, n) -> updateTable());

        // Handle double-click on table row
        reportsTable.setRowFactory(tableView -> {
            TableRow<StudentProfile> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2
                        && !row.isEmpty()) {
                    StudentProfile selected = row.getItem();
                    openStudentDetail(selected);
                }
            });
            return row;
        });
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        updateTable();
    }

    private void updateTable() {
        var all = StudentRepository.getAll();
        ObservableList<StudentProfile> filtered = FXCollections.observableArrayList();

        if (allowlistCheck.isSelected() && denylistCheck.isSelected()) {
            filtered.setAll(all); // show all
        } else if (allowlistCheck.isSelected()) {
            filtered.setAll(all.filtered(s -> "Allowlist".equalsIgnoreCase(s.getFlags())));
        } else if (denylistCheck.isSelected()) {
            filtered.setAll(all.filtered(s -> "Denylist".equalsIgnoreCase(s.getFlags())));
        } else {
            filtered.clear(); // none selected
        }

        displayedStudents.setAll(filtered);
    }

    /**
     * Open a new window showing detailed student profile and comments
     */
    private void openStudentDetail(StudentProfile student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/student_detail.fxml"));
            Parent root = loader.load();

            StudentDetailController controller = loader.getController();
            controller.setStudent(student);

            Stage detailStage = new Stage();
            detailStage.setTitle("Student Details - " + student.getFullName());
            detailStage.initModality(Modality.APPLICATION_MODAL);
            detailStage.setScene(new Scene(root));
            detailStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot open student details: " + e.getMessage()).show();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/home.fxml"));
            Parent root = loader.load();
            Scene scene = ((Button) event.getSource()).getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.setTitle("EduVault — Team 31");
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot return to Home.").show();
        }
    }
}