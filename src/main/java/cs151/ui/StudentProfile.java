package cs151.ui;

public class StudentProfile {
    private final String fullName;
    private final String academicStatus;
    private final String employment;
    private final String jobDetails;
    private final String languages;
    private final String databases;
    private final String preferredRole;
    private final String comments;
    private final String flags;

    public StudentProfile(String fullName, String academicStatus, String employment, String jobDetails,
                          String languages, String databases, String preferredRole, String comments, String flags) {
        this.fullName = fullName;
        this.academicStatus = academicStatus;
        this.employment = employment;
        this.jobDetails = jobDetails;
        this.languages = languages;
        this.databases = databases;
        this.preferredRole = preferredRole;
        this.comments = comments;
        this.flags = flags;
    }

    public String getFullName()      { return fullName; }
    public String getAcademicStatus(){ return academicStatus; }
    public String getEmployment()    { return employment; }
    public String getJobDetails()    { return jobDetails; }
    public String getLanguages()     { return languages; }
    public String getDatabases()     { return databases; }
    public String getPreferredRole() { return preferredRole; }
    public String getComments()      { return comments; }
    public String getFlags()         { return flags; }
}
