package cs151.ui;

import cs151.application.StudentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditStudentController {

    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> academicStatusCombo;

    @FXML private RadioButton employedRadio;
    @FXML private RadioButton notEmployedRadio;
    @FXML private TextField jobDetailsField;

    // we will build these lists as “checkbox lists” in code
    @FXML private ListView<CheckBox> languagesList;
    @FXML private ListView<CheckBox> databasesList;

    @FXML private ComboBox<String> preferredRoleCombo;
    @FXML private TextArea commentsArea;
    @FXML private CheckBox whitelistCheck;
    @FXML private CheckBox blacklistCheck;

    // to know if we are editing or creating
    private StudentProfile originalStudent;

    // keep references to checkboxes so we can mark them
    private final Map<String, CheckBox> langCheckMap = new LinkedHashMap<>();
    private final Map<String, CheckBox> dbCheckMap   = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        // 1. academic status choices
        academicStatusCombo.getItems().setAll(
                "Freshman", "Sophomore", "Junior", "Senior", "Graduate"
        );

        // 2. preferred role choices
        preferredRoleCombo.getItems().setAll(
                "Full-Stack", "Front-End", "Back-End", "Data", "Other"
        );

        // 3. make language checkbox list
        buildLanguageList();

        // 4. make database checkbox list
        buildDatabaseList();

        // group employment radios
        ToggleGroup tg = new ToggleGroup();
        employedRadio.setToggleGroup(tg);
        notEmployedRadio.setToggleGroup(tg);
    }

    private void buildLanguageList() {
        // order matters → LinkedHashMap
        String[] langs = {"Java", "Python", "C++"};
        for (String l : langs) {
            CheckBox cb = new CheckBox(l);
            langCheckMap.put(l.toLowerCase(), cb);
            languagesList.getItems().add(cb);
        }
    }

    private void buildDatabaseList() {
        String[] dbs = {"MySQL", "Postgres", "MongoDB", "Oracle", "SQLite"};
        for (String d : dbs) {
            CheckBox cb = new CheckBox(d);
            dbCheckMap.put(d.toLowerCase(), cb);
            databasesList.getItems().add(cb);
        }
    }

    /**
     * Called from SearchController after user clicks Edit.
     */
    public void loadStudent(StudentProfile student) {
        this.originalStudent = student;

        // basic fields
        fullNameField.setText(student.getFullName());
        academicStatusCombo.setValue(student.getAcademicStatus());

        // employment
        String emp = student.getEmploymentStatus();
        if ("Employed".equalsIgnoreCase(emp)) {
            employedRadio.setSelected(true);
        } else {
            notEmployedRadio.setSelected(true);
        }
        jobDetailsField.setText(student.getJobDetails());

        // comments
        commentsArea.setText(student.getComments());

        // flags
        String flags = student.getFlags() == null ? "" : student.getFlags().toLowerCase();
        whitelistCheck.setSelected(flags.contains("whitelist"));
        blacklistCheck.setSelected(flags.contains("blacklist"));

        // preferred role
        if (student.getPreferredRole() != null && !student.getPreferredRole().isBlank()) {
            preferredRoleCombo.setValue(student.getPreferredRole());
        }

        // LANGUAGES: "Python, Java" → check Python + Java
        String langsStr = student.getLanguages();
        if (langsStr != null && !langsStr.isBlank()) {
            for (String part : langsStr.split(",")) {
                String key = part.trim().toLowerCase();
                CheckBox cb = langCheckMap.get(key);
                if (cb != null) cb.setSelected(true);
            }
        }

        // DATABASES:
        String dbsStr = student.getDatabases();
        if (dbsStr != null && !dbsStr.isBlank()) {
            for (String part : dbsStr.split(",")) {
                String key = part.trim().toLowerCase();
                CheckBox cb = dbCheckMap.get(key);
                if (cb != null) cb.setSelected(true);
            }
        }
    }

    @FXML
    private void handleSave() {
        // collect checkboxes → string
        String langs = collectChecked(langCheckMap);
        String dbs   = collectChecked(dbCheckMap);

        String empStatus = employedRadio.isSelected() ? "Employed" : "Not employed";

        StudentProfile edited = new StudentProfile(
                fullNameField.getText().trim(),
                academicStatusCombo.getValue() == null ? "" : academicStatusCombo.getValue(),
                empStatus,
                jobDetailsField.getText() == null ? "" : jobDetailsField.getText().trim(),
                langs,
                dbs,
                preferredRoleCombo.getValue() == null ? "" : preferredRoleCombo.getValue(),
                commentsArea.getText() == null ? "" : commentsArea.getText().trim(),
                buildFlags()
        );

        if (originalStudent == null) {
            // create
            StudentRepository.add(edited);
        } else {
            // overwrite
            StudentRepository.updateStudent(originalStudent, edited);
        }

        closeWindow();
    }

    private String collectChecked(Map<String, CheckBox> map) {
        List<String> out = new ArrayList<>();
        for (CheckBox cb : map.values()) {
            if (cb.isSelected()) {
                out.add(cb.getText());
            }
        }
        return String.join(", ", out);
    }

    private String buildFlags() {
        List<String> f = new ArrayList<>();
        if (whitelistCheck.isSelected()) f.add("Whitelist");
        if (blacklistCheck.isSelected()) f.add("Blacklist");
        return String.join(", ", f);
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage st = (Stage) fullNameField.getScene().getWindow();
        st.close();
    }
}


