package cs151.application;

import cs151.ui.StudentProfile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores all student profiles in memory.
 * Later you could extend this to save/load from a file.
 */
public class StudentRepository {
    private static final ObservableList<StudentProfile> students = FXCollections.observableArrayList();

    public static ObservableList<StudentProfile> getAll() {
        return students;
    }

    public static void add(StudentProfile student) {
        students.add(student);
    }

    public static void clear() {
        students.clear();
    }
}