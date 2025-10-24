package cs151.ui;

import java.util.ArrayList;

import cs151.application.StudentRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Search Students Profiles page:
 * - Loads ALL profiles into the TableView on open (tabular format).
 * - Filters using StudentRepository.searchStudents(query).
 * - Reset shows all rows again.
 * - Back returns to Home.
 */
public class SearchController {

    // Top controls
    @FXML private TextField searchField;
    @FXML private Button searchButton;   // optional if wired in FXML
    @FXML private Button clearButton;    // optional if wired in FXML

    // Table + columns
    @FXML private TableView<StudentProfile> resultsTable;
    @FXML private TableColumn<StudentProfile, String> colName;
    @FXML private TableColumn<StudentProfile, String> colStatus;
    @FXML private TableColumn<StudentProfile, String> colLanguages;
    @FXML private TableColumn<StudentProfile, String> colDatabases;
    @FXML private TableColumn<StudentProfile, String> colRole;
    @FXML private TableColumn<StudentProfile, String> colComments; // NEW
    @FXML private TableColumn<StudentProfile, String> colFlags;    // NEW

    @FXML
    public void initialize() {
        // Column bindings (match getters in StudentProfile)
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFullName()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAcademicStatus()));
        colLanguages.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLanguages()));
        colDatabases.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDatabases()));
        colRole.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPreferredRole()));
        colComments.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getComments())); // NEW
        colFlags.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFlags()));       // NEW

        // Show all rows in the TableView on page load
        resultsTable.setItems(StudentRepository.getAll());

        // UX: Enter triggers search; wire buttons if present
        if (searchField != null) searchField.setOnAction(e -> handleSearch());
        if (searchButton != null) searchButton.setOnAction(e -> handleSearch());
        if (clearButton != null) clearButton.setOnAction(e -> handleClear());

        // Allows selection of multiple rows
        resultsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim();
        if (query.isEmpty()) {
            resultsTable.setItems(StudentRepository.getAll());
        } else {
            ObservableList<StudentProfile> results = StudentRepository.searchStudents(query);
            resultsTable.setItems(results);
        }
    }

    @FXML
    private void handleClear() {
        if (searchField != null) searchField.clear();
        resultsTable.setItems(StudentRepository.getAll());
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/cs151/application/home.fxml"));
            Stage stage = (Stage) resultsTable.getScene().getWindow();
            stage.setScene(new Scene(fxml.load()));
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Could not load Home: " + ex.getMessage(),
                    ButtonType.OK).showAndWait();
        }
    }

    @FXML
private void handleDelete() {
    // copy selection to a normal list so it won't change while we remove
    var selected = new ArrayList<>(resultsTable.getSelectionModel().getSelectedItems());

    if (selected.isEmpty()) {
        new Alert(Alert.AlertType.INFORMATION, "Select one or more students first.", ButtonType.OK).showAndWait();
        return;
    }

    boolean confirmed = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete " + selected.size() + " selected student(s)?",
            ButtonType.OK, ButtonType.CANCEL)
        .showAndWait().filter(btn -> btn == ButtonType.OK).isPresent();
    if (!confirmed) return;

    for (StudentProfile s : selected) {
        StudentRepository.remove(s);
    }

    handleSearch();
}
}
