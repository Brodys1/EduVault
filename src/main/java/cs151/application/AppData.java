package cs151.application;

import java.io.IOException;
import java.nio.file.*;

public final class AppData {
    private AppData() {}
    public static Path dir() throws IOException {
        Path p = Path.of(System.getProperty("user.home"), ".eduvault");
        if (!Files.exists(p)) Files.createDirectories(p);
        return p;
    }
    public static Path file(String name) throws IOException {
        return dir().resolve(name);
    }
}
