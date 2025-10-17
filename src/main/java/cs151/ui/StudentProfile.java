package cs151.ui;

public class StudentProfile {
    private String fullName;
    private String academicStatus;
    private String preferredRole;
    private String languages;

    public StudentProfile(String fullName, String academicStatus, String preferredRole, String languages) {
        this.fullName = fullName;
        this.academicStatus = academicStatus;
        this.preferredRole = preferredRole;
        this.languages = languages;
    }

    public String getFullName() { return fullName; }
    public String getAcademicStatus() { return academicStatus; }
    public String getPreferredRole() { return preferredRole; }
    public String getLanguages() { return languages; }
}
