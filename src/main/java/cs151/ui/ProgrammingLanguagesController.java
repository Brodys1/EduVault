package cs151.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ProgrammingLanguagesController {
    @FXML
    private TextField nameField;

    @FXML
    private Label messageLabel;

    private static final String CSV_FILE = "data/programming_languages.csv";

    public void handleSave(ActionEvent event) {
        String languageName = nameField.getText().trim();
        System.out.println("Save button clicked for: '" + languageName + "'");

        // Check if input is empty
        if (languageName.isEmpty()) {
            System.out.println("Empty input detected");
            showMessage("Blank entry prohibited", true);
            return;
        }

        // Check for duplicates
        if (isDuplicate(languageName)) {
            System.out.println("Duplicate detected, showing message");
            showMessage("Language already exists!", true);
            return;
        }

        // Save to CSV
        try {
            System.out.println("Saving to CSV: " + languageName);
            saveToCSV(languageName);
            showMessage("Saved successfully!", false);
            nameField.clear();
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
            showMessage("Error: " + e.getMessage(), true);
        }
    }

    private boolean isDuplicate(String languageName) {
        try {
            if (!Files.exists(Paths.get(CSV_FILE))) {
                System.out.println("CSV file does not exist, no duplicates");
                return false;
            }

            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            System.out.println("CSV file has " + lines.size() + " lines");
            System.out.println("Checking for duplicate: " + languageName);

            // Skip the header row (first line) when checking for duplicates
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                System.out.println("Comparing with line " + i + ": '" + line + "'");
                if (line.equalsIgnoreCase(languageName)) {
                    System.out.println("Found duplicate!");
                    return true;
                }
            }
            System.out.println("No duplicate found");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return false;
    }

    private void saveToCSV(String languageName) throws IOException {
        // Create file if it doesn't exist and add header
        if (!Files.exists(Paths.get(CSV_FILE))) {
            Files.write(Paths.get(CSV_FILE), "Programming Language\n".getBytes());
        }

        // Append the new language
        Files.write(Paths.get(CSV_FILE), (languageName + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    private void showMessage(String message, boolean isError) {
        System.out.println("Showing message: '" + message + "' (error: " + isError + ")");
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setStyle("-fx-text-fill: red;");
        } else {
            messageLabel.setStyle("-fx-text-fill: green;");
        }

        // Clear message after 3 seconds using a separate thread
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> {
                    messageLabel.setText("");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/cs151/application/home.fxml")));
        stage.setTitle("EduVault — Team 31");
        stage.setScene(scene);
    }
}
