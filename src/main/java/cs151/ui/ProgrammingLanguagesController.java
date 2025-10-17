package cs151.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import cs151.application.LangTable.Language;
import cs151.application.LanguageRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.util.Comparator;

public class ProgrammingLanguagesController {

    @FXML private TableView<Language> table;
    @FXML private TableColumn<Language, String> nameCol;
    @FXML private TextField newNameField;

    private ObservableList<Language> items;

    @FXML
    public void initialize() {
        // Load data from your LanguageRepository
        items = LanguageRepository.loadAll();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            int index = event.getTablePosition().getRow();
            Language lang = items.get(index);
            String newName = event.getNewValue().trim();
            if (!newName.isEmpty()) {
                items.set(index, new Language(newName));
                onSave();
            }
            table.refresh();
        });

        table.setEditable(true);
        table.setItems(items);
        items.sort(Comparator.comparing(l -> l.getName().toLowerCase()));
    }

    @FXML
    private void onAdd() {
        String newName = newNameField.getText().trim();
        if (!newName.isEmpty()) {
            items.add(new Language(newName));
            newNameField.clear();
            onSave();
        }
    }

    @FXML
    private void onDelete() {
        var selected = table.getSelectionModel().getSelectedItems();
        items.removeAll(selected);
        onSave();
    }

    @FXML
    private void onSave() {
        LanguageRepository.saveAll(items);
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        // get the current window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // load home.fxml and set it to this stage
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml"))
        );
        stage.setScene(scene);
    }
}