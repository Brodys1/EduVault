package cs151.application;

import cs151.ui.Comment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CommentRepository {
    private static final ObservableList<Comment> comments = FXCollections.observableArrayList();
    private static final String COMMENT_PATH = "data/comments.csv";

    static {
        loadFromFile();
    }

    public static ObservableList<Comment> getAll() {
        return comments;
    }

    public static ObservableList<Comment> searchComments(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAll();
        }
        String lower = query.toLowerCase().trim();
        return comments.stream()
                .filter(comment ->
                    comment.getFullName().toLowerCase().contains(lower) ||
                    comment.getComment().toLowerCase().contains(lower)
                )
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static boolean add(Comment comment ) {
        comments.add(comment);
        sortComments();
        saveToFile();
        return true;
    }

    public static void clear() {
        comments.clear();
        saveToFile();
    }

    private static void loadFromFile() {
        if (!Files.exists(Paths.get(COMMENT_PATH))) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(COMMENT_PATH))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                // CSV split that preserves commas inside quotes, same as StudentRepository
                String[] p = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                for (int i = 0; i < p.length; i++) {
                    p[i] = p[i].replaceAll("^\"|\"$", "").trim();
                }

                if (p.length >= 2) {
                    comments.add(new Comment(
                            p[0].trim(), // FullName
                            p[1].trim()  // Comments
                    ));
                }
            }
            System.out.println("Loaded " + comments.size() + " comment rows from file.");
            sortComments();
        } catch (IOException e) {
            System.err.println("Error reading comments file: " + e.getMessage());
        }
    }

    private static void sortComments() {
        FXCollections.sort(comments, (a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COMMENT_PATH))) {
            writer.println("FullName,Comments");
            for (Comment c : comments) {
                writer.printf("\"%s\",\"%s\"%n",
                        c.getFullName(),
                        c.getComment());
            }
        } catch (IOException e) {
            System.err.println("Error saving comments file: " + e.getMessage());
        }
    }

}
