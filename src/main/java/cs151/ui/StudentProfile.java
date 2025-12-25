package cs151.ui;

public class StudentProfile {
    private String fullName;
    private String academicStatus;
    private String employmentStatus;
    private String jobDetails;
    private String languages;
    private String databases;
    private String preferredRole;
    private String comments;
    private String flags; // “Allowlist”, “Denylist”, or “None”

    public StudentProfile(
            String fullName,
            String academicStatus,
            String employmentStatus,
            String jobDetails,
            String languages,
            String databases,
            String preferredRole,
            // String comments,
            String flags) {
        this.fullName = fullName;
        this.academicStatus = academicStatus;
        this.employmentStatus = employmentStatus;
        this.jobDetails = jobDetails;
        this.languages = languages;
        this.databases = databases;
        this.preferredRole = preferredRole;
        // this.comments = comments;
        this.flags = flags;
    }

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public String getJobDetails() {
        return jobDetails;
    }

    public String getLanguages() {
        return languages;
    }

    public String getDatabases() {
        return databases;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    // public String getComments() { return comments; }
    public String getFlags() {
        return flags;
    }
}