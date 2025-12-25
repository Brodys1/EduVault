package cs151.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Callback;

public class StudentController implements Initializable {

    @FXML
    protected TextField fullNameField;
    @FXML
    protected Label fullNameError;
    @FXML
    protected ComboBox<String> academicStatusCombo;
    @FXML
    protected Label academicStatusError;
    @FXML
    protected RadioButton employedRadio;
    @FXML
    protected RadioButton notEmployedRadio;
    @FXML
    protected TextField jobDetailsField;
    @FXML
    protected Label jobDetailsError;
    @FXML
    protected ListView<String> programmingLanguagesList;
    @FXML
    protected Label programmingLanguagesError;
    @FXML
    protected ListView<String> databasesList;
    @FXML
    protected Label databasesError;
    @FXML
    protected ComboBox<String> preferredRoleCombo;
    @FXML
    protected Label preferredRoleError;
    @FXML
    protected TextArea commentsArea;
    @FXML
    protected CheckBox allowlistCheck;
    @FXML
    protected CheckBox denylistCheck;
    @FXML
    protected Label flagsError;
    @FXML
    protected Label statusLabel;

    protected ToggleGroup jobStatusGroup;
    protected ObservableList<String> availableLanguages;
    protected ObservableList<String> availableDatabases;
    protected ObservableList<String> selectedLanguages;
    protected ObservableList<String> selectedDatabases;

    // Hard-coded options
    private static final List<String> ACADEMIC_STATUSES = Arrays.asList(
            "Freshman", "Sophomore", "Junior", "Senior", "Graduate");

    private static final List<String> PREFERRED_ROLES = Arrays.asList(
            "Front-End", "Back-End", "Full-Stack", "Data", "Other");

    private static final List<String> DATABASE_OPTIONS = Arrays.asList(
            "MySQL", "Postgres", "MongoDB", "Oracle", "SQLite");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize ToggleGroup for job status
        jobStatusGroup = new ToggleGroup();
        employedRadio.setToggleGroup(jobStatusGroup);
        notEmployedRadio.setToggleGroup(jobStatusGroup);
        notEmployedRadio.setSelected(true);

        // Initialize ComboBoxes
        academicStatusCombo.setItems(FXCollections.observableArrayList(ACADEMIC_STATUSES));
        preferredRoleCombo.setItems(FXCollections.observableArrayList(PREFERRED_ROLES));

        // Load programming languages from CSV
        loadProgrammingLanguages();

        // Initialize selected lists
        selectedLanguages = FXCollections.observableArrayList();
        selectedDatabases = FXCollections.observableArrayList();

        // Initialize database options
        availableDatabases = FXCollections.observableArrayList(DATABASE_OPTIONS);
        databasesList.setItems(availableDatabases);

        // Set up multi-select for programming languages and databases
        setupMultiSelectList(programmingLanguagesList, availableLanguages, selectedLanguages);
        setupMultiSelectList(databasesList, availableDatabases, selectedDatabases);

        jobStatusGroup.selectedToggleProperty().addListener((_, _, newVal) -> {
            if (newVal == employedRadio) {
                jobDetailsField.setDisable(false);
            } else {
                jobDetailsField.setDisable(true);
                jobDetailsField.clear();
            }
        });

        allowlistCheck.selectedProperty().addListener((_, _, isNowSelected) -> {
            if (isNowSelected && denylistCheck.isSelected()) {
                denylistCheck.setSelected(false);
            }
        });

        denylistCheck.selectedProperty().addListener((_, _, isNowSelected) -> {
            if (isNowSelected && allowlistCheck.isSelected()) {
                allowlistCheck.setSelected(false);
            }
        });

        clearErrorLabels();
    }

    private void loadProgrammingLanguages() {
        availableLanguages = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/programming_languages.csv"))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (!line.trim().isEmpty()) {
                    availableLanguages.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading programming languages: " + e.getMessage());
            // Fallback to default languages
            availableLanguages.addAll(Arrays.asList("Java", "Python", "C++", "JavaScript", "C#"));
        }
    }

    private void setupMultiSelectList(ListView<String> listView, ObservableList<String> availableItems,
            ObservableList<String> selectedItems) {
        listView.setItems(availableItems);
        listView.setCellFactory(
                CheckBoxListCell.forListView(new Callback<String, javafx.beans.value.ObservableValue<Boolean>>() {
                    @Override
                    public javafx.beans.value.ObservableValue<Boolean> call(String item) {
                        javafx.beans.property.BooleanProperty selected = new javafx.beans.property.SimpleBooleanProperty(
                                selectedItems.contains(item));

                        selectedItems.addListener((javafx.collections.ListChangeListener<String>) _ -> {
                            selected.set(selectedItems.contains(item));
                        });

                        selected.addListener((_, _, isNowSelected) -> {
                            if (isNowSelected) {
                                if (!selectedItems.contains(item)) {
                                    selectedItems.add(item);
                                }
                            } else {
                                selectedItems.remove(item);
                            }
                        });
                        return selected;
                    }
                }));
    }

    // @FXML
    // private void addComment() {
    // String comment = commentsArea.getText().trim();
    // if (!comment.isEmpty())
    // // TODO Change this to make multiple entries instead
    // commentsArea.setText(comment + "\n\n");
    // commentsArea.positionCaret(commentsArea.getText().length());

    // }

    @FXML
    private void saveStudentProfile() {
        clearErrorLabels();

        if (!validateForm()) {
            return;
        }

        String fullName = fullNameField.getText().trim();
        String academicStatus = academicStatusCombo.getSelectionModel().getSelectedItem();
        String employmentStatus = employedRadio.isSelected() ? "Employed" : "Not Employed";
        String jobDetails = jobDetailsField.getText().trim();
        String languages = String.join(", ", selectedLanguages);
        String databases = String.join(", ", selectedDatabases);
        String preferredRole = preferredRoleCombo.getSelectionModel().getSelectedItem();
        String comments = commentsArea.getText().trim();
        String flags = allowlistCheck.isSelected() ? "Allowlist"
                : denylistCheck.isSelected() ? "Denylist"
                        : "";

        boolean success = cs151.application.StudentRepository.add(
                new StudentProfile(fullName, academicStatus, employmentStatus, jobDetails,
                        languages, databases, preferredRole, flags));

        if (success) {
            cs151.application.CommentRepository.add(new cs151.ui.Comment(fullName, comments));
            statusLabel.setText("Profile saved successfully!");
            statusLabel.setStyle("-fx-text-fill: blue;");
            clearForm();
        } else {
            statusLabel.setText("Error: A student with this name already exists!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void clearForm() {
        fullNameField.clear();
        academicStatusCombo.getSelectionModel().clearSelection();
        notEmployedRadio.setSelected(true);
        jobDetailsField.clear();
        selectedLanguages.clear();
        selectedDatabases.clear();
        preferredRoleCombo.getSelectionModel().clearSelection();
        commentsArea.clear();
        allowlistCheck.setSelected(false);
        denylistCheck.setSelected(false);
        clearErrorLabels();
        statusLabel.setText("");
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setTitle("EduVault");
        stage.setScene(scene);
    }

    protected boolean validateForm() {
        boolean isValid = true;

        if (fullNameField.getText().trim().isEmpty()) {
            fullNameError.setText("Full name is required");
            isValid = false;
        }

        if (academicStatusCombo.getSelectionModel().getSelectedItem() == null) {
            academicStatusError.setText("Academic status is required");
            isValid = false;
        }

        if (employedRadio.isSelected() && jobDetailsField.getText().trim().isEmpty()) {
            jobDetailsError.setText("Job details are required when employed");
            isValid = false;
        }

        if (selectedLanguages.isEmpty()) {
            programmingLanguagesError.setText("At least one programming language is required");
            isValid = false;
        }

        if (selectedDatabases.isEmpty()) {
            databasesError.setText("At least one database is required");
            isValid = false;
        }

        if (preferredRoleCombo.getSelectionModel().getSelectedItem() == null) {
            preferredRoleError.setText("Preferred role is required");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrorLabels() {
        fullNameError.setText("");
        academicStatusError.setText("");
        jobDetailsError.setText("");
        programmingLanguagesError.setText("");
        databasesError.setText("");
        preferredRoleError.setText("");
        flagsError.setText("");
    }
}
