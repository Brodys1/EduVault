# *EduVault*

Cross-platform software for faculty to manage students

## Overview

EduVault is a comprehensive student management system designed to help faculty efficiently track and manage student information. Built with JavaFX, this desktop application provides an intuitive interface for maintaining student profiles, recording comments, tracking programming language proficiencies, and generating reports for allowlisted and denylisted students. The system supports full CRUD operations, advanced search capabilities, and persistent data storage through CSV files.

## Screenshots

![Homepage](screenshots/Homepage.png)

![Define student profiles page](<screenshots/Define Student Profile.png>)

![Search student profiles page](<screenshots/Search Student Profiles.png>)

![Reports page](screenshots/Reports.png)

---

## Contribution History

Team project for CS 151 at SJSU with contributions from:

- [Brody Smith](https://brodysmith.com)
- Chloe Pham
- Xuan Phuong Nguyen
- Tavishi Bansal

### Version 1.0

Brody Smith:

- Enhanced documentation with application screenshots
- Refactored codebase to follow industry-standard naming conventions and improve code maintainability

### Version 0.9

Brody Smith:

- Implemented double click functionality on reports page to view students information
- Created a read-only form layout to display students information
- Bug Fix: Made title bar consistent when going back and forth between views

Chloe Pham:

- Added double click functionality on student detail page to view full comment content
- Implemented CommentDetailController.java & comment_detail.fxml

Xuan Phuong Nguyen

- Added comment table-view to bottom of student detail page

Tavishi Bansal

- Implemented Reports page (reports.fxml, ReportsController.java) to display allowlisted and denylisted students in tabular format.
- Added “View Reports” button on Home page and integrated navigation to reports page.
- Enabled filtering via checkboxes to toggle between Allowlisted, Denylisted, or both student lists.
- Connected report data dynamically to StudentRepository for real-time updates.

### Version 0.8

Brody Smith:

- Added button to view comments in SearchController and StudentProfilesController
- Added template page for viewing comments for a specific student

Chloe Pham:

- Implemented Comment.java & CommentRepository.java
- Changed comments to save in comments.csv instead of students.csv

Xuan Phuong Nguyen

- Implement add comment feature (StudentCommentsController.java and student_comments.fxml)

Tavishi Bansal

- Modified Comment.java and CommentRepository.java to include a new date field.
- Updated the CSV file format to "FullName, Comment, Date" with quoted values.
- Updated StudentCommentsController.java to:
  - Automatically add today’s date when a new comment is created.
  - Display all comments with their associated date stamps.

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
