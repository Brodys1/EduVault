package cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public final class LanguageRepository {
    private LanguageRepository() {}
    private static final String RESOURCE_CSV = "/programming_languages.csv"; // in resources
    private static final String DATA_NAME    = "programming_languages.csv";   // in ~/.eduvault

    private static Path dataPath() throws IOException { return AppData.file(DATA_NAME); }

    public static void ensureSeeded() throws IOException {
        Path dst = dataPath();
        if (Files.exists(dst)) return;
        try (InputStream in = LanguageRepository.class.getResourceAsStream(RESOURCE_CSV)) {
            if (in == null) {
                Files.writeString(dst, "Programming Language\n", StandardCharsets.UTF_8);
            } else {
                Files.copy(in, dst, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static ObservableList<LangTable.Language> loadAll() {
        var list = FXCollections.<LangTable.Language>observableArrayList();
        try {
            ensureSeeded();
            List<String> lines = Files.readAllLines(dataPath(), StandardCharsets.UTF_8);
            boolean header = true;
            for (String line : lines) {
                if (header) { header = false; continue; }
                if (!line.isBlank()) list.add(new LangTable.Language(line.trim()));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveAll(ObservableList<LangTable.Language> items) {
        try {
            StringBuilder sb = new StringBuilder("Programming Language\n");
            for (var lang : items) sb.append(lang.getName()).append('\n');
            Files.writeString(
                    dataPath(), sb.toString(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) { e.printStackTrace(); }
    }
}
