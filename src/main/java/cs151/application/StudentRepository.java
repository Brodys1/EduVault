package cs151.application;

import cs151.ui.StudentProfile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class StudentRepository {

    private static final ObservableList<StudentProfile> students = FXCollections.observableArrayList();
    private static final String FILE_PATH = "students.csv";

    // Load data when the class is first used
    static {
        loadFromFile();
    }

    public static ObservableList<StudentProfile> getAll() {
        return students;
    }

    public static ObservableList<StudentProfile> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAll();
        }
        String lowerQuery = query.toLowerCase().trim();
        return students.stream()
                .filter(student ->
                        student.getFullName().toLowerCase().contains(lowerQuery) ||
                                student.getAcademicStatus().toLowerCase().contains(lowerQuery) ||
                                student.getLanguages().toLowerCase().contains(lowerQuery) ||
                                student.getDatabases().toLowerCase().contains(lowerQuery) ||
                                student.getPreferredRole().toLowerCase().contains(lowerQuery)
                )
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    public static boolean add(StudentProfile student) {
        // check if a student with the same name already exists
        if (nameExists(student.getFullName().replaceAll("\\s+", " ").trim())) {
            return false;
        }

        students.add(student);
        sortStudents();
        saveToFile();
        return true;
    }

    /**
     * NEW: overwrite an existing student.
     * We match them by object (the one the search table gave us).
     */
    public static void updateStudent(StudentProfile original, StudentProfile edited) {
        if (original == null || edited == null) return;

        int idx = students.indexOf(original);
        if (idx >= 0) {
            students.set(idx, edited);
        } else {
            // fallback – if for some reason we didn't find it, just add it
            students.add(edited);
        }

        sortStudents();
        saveToFile();
    }

    /**
     * Check if a student with the given name already exists in the repository
     */
    public static boolean nameExists(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        String trimmedName = fullName.trim();
        return students.stream()
                .anyMatch(student -> student.getFullName().trim().equalsIgnoreCase(trimmedName));
    }

    public static void clear() {
        students.clear();
        saveToFile();
    }

    /** Load all students from CSV file */
    private static void loadFromFile() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] p = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                for (int i = 0; i < p.length; i++) {
                    p[i] = p[i].replaceAll("^\"|\"$", "").trim();
                }

                if (p.length >= 9) {
                    students.add(new StudentProfile(
                            p[0].trim(), // full name
                            p[1].trim(), // academic status
                            p[2].trim(), // employment
                            p[3].trim(), // job details
                            p[4].trim(), // languages
                            p[5].trim(), // databases
                            p[6].trim(), // role
                            p[7].trim(), // comments
                            p[8].trim()  // flags
                    ));
                }
            }
            System.out.println("Loaded " + students.size() + " students from file.");
            sortStudents();
        } catch (IOException e) {
            System.err.println("Error reading students file: " + e.getMessage());
        }
    }

    private static void sortStudents() {
        FXCollections.sort(students, (a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println(
                    "FullName,AcademicStatus,Employment,JobDetails,Languages,Databases,PreferredRole,Comments,Flags");

            for (StudentProfile s : students) {
                writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                        s.getFullName(),
                        s.getAcademicStatus(),
                        s.getEmploymentStatus(),
                        s.getJobDetails(),
                        s.getLanguages(),
                        s.getDatabases(),
                        s.getPreferredRole(),
                        s.getComments(),
                        s.getFlags());
            }
        } catch (IOException e) {
            System.err.println("Error saving students file: " + e.getMessage());
        }
    }

    public static void remove(StudentProfile student) {
        if (student == null) return;
        students.remove(student);
        saveToFile();
    }
}
