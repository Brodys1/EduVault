package cs151.application;

import cs151.ui.StudentProfile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static ObservableList<StudentProfile> search(String query) {
        ObservableList<StudentProfile> results = FXCollections.observableArrayList();

        String lowerQuery = query.toLowerCase();

        for (StudentProfile s : students) {
            if (s.getFullName().toLowerCase().contains(lowerQuery)
                    || s.getAcademicStatus().toLowerCase().contains(lowerQuery)
                    || s.getEmploymentStatus().toLowerCase().contains(lowerQuery)
                    || s.getJobDetails().toLowerCase().contains(lowerQuery)
                    || s.getLanguages().toLowerCase().contains(lowerQuery)
                    || s.getDatabases().toLowerCase().contains(lowerQuery)
                    || s.getPreferredRole().toLowerCase().contains(lowerQuery)
                    || s.getComments().toLowerCase().contains(lowerQuery)
                    || s.getFlags().toLowerCase().contains(lowerQuery)) {
                results.add(s);
            }
        }

        return results;
    }

    public static void add(StudentProfile student) {
        students.add(student);
        sortStudents();
        saveToFile();
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
                if (isHeader) { isHeader = false; continue; }
                String[] p = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //String[] p = line.split(",", -1);

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
        FXCollections.sort(students, (a, b) ->
                a.getFullName().compareToIgnoreCase(b.getFullName())
        );
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println("FullName,AcademicStatus,Employment,JobDetails,Languages,Databases,PreferredRole,Comments,Flags");

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
}