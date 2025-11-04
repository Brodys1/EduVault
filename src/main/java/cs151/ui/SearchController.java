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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    // top bar
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton; // in FXML
    @FXML
    private Button clearButton; // "Reset" in FXML
    @FXML
    private Button deleteButton; // "Delete Selected" in FXML
    @FXML
    private Button backButton; // "Back to Home" in FXML

    // table
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
    private TableColumn<StudentProfile, String> colPreferredRole;
    @FXML
    private TableColumn<StudentProfile, String> colComments; // optional column
    @FXML
    private TableColumn<StudentProfile, String> colFlags; // optional column
    @FXML
    private TableColumn<StudentProfile, Void> colActions; // EDIT column

    // local list we show in the table
    private final ObservableList<StudentProfile> results = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // 1) bind columns to StudentProfile
        if (colName != null) {
            colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        }
        if (colStatus != null) {
            colStatus.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        }
        if (colLanguages != null) {
            colLanguages.setCellValueFactory(new PropertyValueFactory<>("languages"));
        }
        if (colDatabases != null) {
            colDatabases.setCellValueFactory(new PropertyValueFactory<>("databases"));
        }
        if (colPreferredRole != null) {
            colPreferredRole.setCellValueFactory(new PropertyValueFactory<>("preferredRole"));
        }
        // these two are on your “old” page
        if (colComments != null) {
            colComments.setCellValueFactory(new PropertyValueFactory<>("comments"));
        }
        if (colFlags != null) {
            colFlags.setCellValueFactory(new PropertyValueFactory<>("flags"));
        }

        // 2) add the Edit button column (your friend’s part)
        if (colActions != null) {
            setupActionsColumn();
        }

        // 3) load ALL when page opens
        results.setAll(StudentRepository.getAll());
        resultsTable.setItems(results);

        // 4) allow multi-select for “Delete Selected”
        resultsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // 5) wire top controls to methods (in case FXML didn’t)
        if (searchField != null) {
            searchField.setOnAction(e -> handleSearch(null));
        }
        if (searchButton != null) {
            searchButton.setOnAction(this::handleSearch);
        }
        if (clearButton != null) {
            clearButton.setOnAction(this::handleClear);
        }
        if (deleteButton != null) {
            deleteButton.setOnAction(this::handleDelete);
        }
        if (backButton != null) {
            backButton.setOnAction(this::handleBack);
        }
    }

    /**
     * Search button / press Enter
     */
    @FXML
    private void handleSearch(ActionEvent event) {
        String q = (searchField != null && searchField.getText() != null)
                ? searchField.getText().trim()
                : "";

        if (q.isEmpty()) {
            // no filter → show all
            results.setAll(StudentRepository.getAll());
        } else {
            results.setAll(StudentRepository.searchStudents(q));
        }
        resultsTable.setItems(results);
    }

    /**
     * Reset button
     */
    @FXML
    private void handleClear(ActionEvent event) {
        if (searchField != null) {
            searchField.clear();
        }
        results.setAll(StudentRepository.getAll());
        resultsTable.setItems(results);
    }

    /**
     * Delete Selected button
     */
    @FXML
    private void handleDelete(ActionEvent event) {
        // copy selection first
        List<StudentProfile> selected = new ArrayList<>(resultsTable.getSelectionModel().getSelectedItems());

        if (selected.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Select one or more students first.").showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.size() + " selected student(s)?",
                ButtonType.OK, ButtonType.CANCEL);
        var result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        for (StudentProfile s : selected) {
            StudentRepository.remove(s);
        }

        // refresh
        handleSearch(null);
    }

    /**
     * Back to Home — but KEEP THE SAME SCENE so fullscreen stays
     */
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/home.fxml"));
            Parent homeRoot = loader.load();

            // REUSE current scene
            Scene scene = resultsTable.getScene();
            scene.setRoot(homeRoot);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Cannot return to Home page.").show();
        }
    }

    /**
     * Builds the "Edit" column
     */
    private void setupActionsColumn() {
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");

            {
                editBtn.setOnAction(e -> {
                    StudentProfile student = getTableView().getItems().get(getIndex());
                    openEditPage(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editBtn));
                }
            }
        });
    }

    /**
     * Opens your edit_student.fxml and calls controller.loadStudent(...)
     * → this is exactly your friend’s requirement
     */
    private void openEditPage(StudentProfile student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/edit_student.fxml"));
            Parent root = loader.load();

            EditStudentController controller = loader.getController();
            controller.loadStudent(student);

            Stage dialog = new Stage();
            dialog.setTitle("Edit Student");
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);

            dialog.showAndWait();
            handleSearch(null);
            resultsTable.refresh();
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Cannot open edit_student.fxml").show();
        }
    }
}
