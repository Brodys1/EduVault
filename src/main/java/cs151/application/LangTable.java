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
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;

public class LangTable extends Application {

    private static final String CSV_FILE = "data/programming_languages.csv";

    // define Language with its own getName() method
    public static class Language {
        private final String name;

        public Language(String name) {
            this.name = name;
        }
        
        // col will use this method for cell values
        public String getName() {
            return name;
        }
    }

    @Override
    public void start(Stage stage) {
        // make an empty table with one column, "Programming Language(s)"
        TableView<Language> table = new TableView<>();
        TableColumn<Language, String> col = new TableColumn<>("Name");
        
        // col will use getName() to get the value
        col.setCellValueFactory(new PropertyValueFactory<>("name"));
        col.setPrefWidth(400);
        table.getColumns().add(col);

        // load rows from CSV
        ObservableList<Language> langs = loadLangs();

        // sorts the programming languages from A-Z ascending
        Collections.sort(langs, new Comparator<Language>() {
            @Override
            public int compare(Language a, Language b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });

        // put the programmming languages into the table
        table.setItems(langs);

        // show the table window
        BorderPane root = new BorderPane(table);
        stage.setTitle("Programming Languages (A to Z)");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    private ObservableList<Language> loadLangs() {
        // create empty list that will hold the programming languages
        ObservableList<Language> list = FXCollections.observableArrayList();

        // get the path to the .csv file
        Path path = Path.of(CSV_FILE);

        // if file does not exist, just return the empty list.
        if (!Files.exists(path)) {
            System.out.println("File not found: " + CSV_FILE);
            return list;
        }

        // Try to open and read the file
        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;
            boolean header = true;  // Use this to skip the header (the first line)

            // Read one line at a time until we reach the end of the file
            while ((line = reader.readLine()) != null) {

                // If this is the header line, skip it
                if (header) {
                    System.out.println("Skipped first line: " + line);
                    header = false;
                    continue;
                }

                // Add this language to the list
                list.add(new Language(line));
                
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        // Return the list (is either empty or has programming language(s))
        return list;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
