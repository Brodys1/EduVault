# Name of application: *EduVault*

## Who did what:

### Version 0.8
Brody Smith:
- fill in

Chloe Pham:
- Implemented Comment.java & CommentRepository.java
- Changed comments to save in comments.csv instead of students.csv

Xuan Phuong Nguyen
- fill in

Tavishi Bansal
- Modified Comment.java and CommentRepository.java to include a new date field.
- Updated the CSV file format to "FullName, Comment, Date" with quoted values.
- Updated StudentCommentsController.java to:
-   Automatically add today’s date when a new comment is created.
-   Display all comments with their associated date stamps.

### Version 0.7
Brody Smith:
- Managed repo, code review, fixed bugs (Removed "None" flag for null flag input on student creation)
- Redesigned homepage buttons

Chloe Pham:
- Fixed so search page refreshes after "save" with new edits
- Implemented saving of edited student profiles (EditStudentController.java)

Xuan Phuong Nguyen
- Implemented the ability to edit student profiles (SearchController.java)

Tavishi Bansal
- Added "Edit" button that leads to a student editing page (SearchController.java, edit_student.fxml)

### Version 0.6
Brody Smith:
- Implemented Search method
- Trimmed whitespace for duplicate check when adding a student profile

Chloe Pham:
- Implemented deletion of student function for student search page

Xuan Phuong Nguyen
- Made search (name, academic status, programming language, database skill, or role) result appear in tabular format (search.fxml, SearchController.java and HomeController.java)

Tavishi Bansal
- Added “Search Students” Button to Home Screen
- Implemented search.fxml & SearchController.java

### Version 0.5
Brody Smith:
- Implemented StudentController.java & student.fxml
- Fixed bugs and added duplicate check while saving student information

Chloe Pham:
- Implemented to & back button for StudentController.java & student.fxml

Xuan Phuong Nguyen
- Implemented StudentProfilesController.java, StudentProfile.java, StudentRepository.java, and student_profiles.fxml

Tavishi Bansal
- Implemented StudentProfilesController.java, StudentProfile.java, StudentRepository.java, and student_profiles.fxml
- Implemented storage for student profiles (students.csv)

### Version 0.3
Brody Smith:
- Implemented storage for programming languages (programming_languages.csv)

Chloe Pham:
- Implemented LangTable.java

Xuan Phuong Nguyen
- N/A

Tavishi Bansal
- N/A

### Version 0.2
Brody Smith:
- Implemented ProgrammingLanguagesController.java

Chloe Pham:
- Implemented programming_languages.fxml

Xuan Phuong Nguyen
- Implemented HomeController.java

Tavishi Bansal
- Implemented home.fxml

## Any other instruction that users need to know:

Use `mvn javafx:run` to run project
