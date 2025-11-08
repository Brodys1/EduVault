package cs151.ui;

public class Comment {
    private String fullName;
    private String comment;

    public Comment(String fullName, String comment) {
        this.fullName = fullName;
        this.comment = comment;
    }
    public String getFullName() { return fullName; }
    public String getComment() { return comment; }
}
