package cs151.ui;

import cs151.application.StudentRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentProfilesController implements Initializable {

    @FXML
    private TableView<StudentProfile> studentsTable;
    @FXML
    private TableColumn<StudentProfile, String> nameCol;
    @FXML
    private TableColumn<StudentProfile, String> statusCol;
    @FXML
    private TableColumn<StudentProfile, String> employmentCol;
    @FXML
    private TableColumn<StudentProfile, String> jobDetailsCol;
    @FXML
    private TableColumn<StudentProfile, String> langCol;
    @FXML
    private TableColumn<StudentProfile, String> dbCol;
    @FXML
    private TableColumn<StudentProfile, String> roleCol;
    @FXML
    private TableColumn<StudentProfile, String> commentsCol;
    @FXML
    private TableColumn<StudentProfile, String> flagsCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        employmentCol.setCellValueFactory(new PropertyValueFactory<>("employmentStatus"));
        jobDetailsCol.setCellValueFactory(new PropertyValueFactory<>("jobDetails"));
        langCol.setCellValueFactory(new PropertyValueFactory<>("languages"));
        dbCol.setCellValueFactory(new PropertyValueFactory<>("databases"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("preferredRole"));

        if (commentsCol != null) {
            setupCommentsColumn();
        }

        flagsCol.setCellValueFactory(new PropertyValueFactory<>("flags"));

        // Load saved data from repository
        ObservableList<StudentProfile> students = StudentRepository.getAll();
        studentsTable.setItems(students);

    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setTitle("EduVault");
        stage.setScene(scene);
    }

    private void setupCommentsColumn() {
        commentsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewCommentsBtn = new Button("View Comments");

            {
                viewCommentsBtn.setOnAction(e -> {
                    StudentProfile student = getTableView().getItems().get(getIndex());
                    openCommentsPage(student);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, viewCommentsBtn));
                }
            }
        });
    }

    /**
     * Opens a page showing all comments for the selected student
     */
    private void openCommentsPage(StudentProfile student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/student_comments.fxml"));
            Parent root = loader.load();

            StudentCommentsController controller = loader.getController();
            controller.loadComments(student.getFullName());

            Stage dialog = new Stage();
            dialog.setTitle("Comments for " + student.getFullName());
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);

            dialog.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Cannot open student_comments.fxml").show();
        }
    }
}