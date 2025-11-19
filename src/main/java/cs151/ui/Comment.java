package cs151.ui;

public class Comment {
    private String fullName;
    private String comment;
    private String date;

    public Comment(String fullName, String comment, String date) {
        this.fullName = fullName;
        this.comment = comment;
        this.date = date;
    }

    //default = empty date
    public Comment(String fullName, String comment) {
        this(fullName, comment, "");
    }

    public String getFullName() { return fullName; }
    public String getComment() { return comment; }
    public String getDate() { return date; }
}
