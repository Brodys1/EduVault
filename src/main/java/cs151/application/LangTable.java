package cs151.application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Comparator;

public class LangTable extends Application {

    // simple model with getter used by PropertyValueFactory("name")
    public static class Language {
        private final String name;
        public Language(String name) { this.name = name; }
        public String getName() { return name; }
    }

    @Override
    public void start(Stage stage) {
        // table + one column
        TableView<Language> table = new TableView<>();
        TableColumn<Language, String> col = new TableColumn<>("Name");
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setPrefWidth(400);
        table.getColumns().add(col);

        // ✅ load from ~/.eduvault/programming_languages.csv (seeded from resources on first run)
        ObservableList<Language> langs = LanguageRepository.loadAll();

        // sort A→Z (case-insensitive)
        FXCollections.sort(langs, Comparator.comparing(l -> l.getName().toLowerCase()));

        table.setItems(langs);

        // persist on close
        stage.setOnCloseRequest(e -> LanguageRepository.saveAll(table.getItems()));

        // show window
        BorderPane root = new BorderPane(table);
        stage.setTitle("Programming Languages (A to Z)");
        stage.setScene(new Scene(root, 480, 320));
        stage.setMinWidth(420);
        stage.setMinHeight(280);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
