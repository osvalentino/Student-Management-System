# Student Management System
I implemented a console-based student management system. The objective is to design a system for students to manage their courses. There will be three main user roles in the application: Admin, Student, and Professor.

In the student management system, 
a) A student can log in to their account, view/add/drop courses, check their course schedule, and view grades. 
b) A professor can view course information they have, and view the student lists for these courses.
c) An admin can view course/student/professor lists, and add/delete courses/students/professors. 

The course information will be in the courseInfo.txt file. There will also be three files containing student/professor/admin information. The student management system will read and parse all of the files. Once all information has been loaded into the system, you’ll be able to log in as a(n) student/professor/administrator to test the system.

Classes:
- Abstract User class, extends Professor class, Student class, and Admin class.
- Course class (represents single course)
- Controller class (controls main logic of system including login, add class, etc)
- FileInfoReader class (reads and parses txt files)

Below are explanations of the pieces of information in each of the four provided data files.
- courseInfo.txt - Courses information file that contains: course ID; course name; lecturer; days; start time; end time; capacity
- studentInfo.txt - Student information file that contains: student ID; student name; student username; password; course ID: course grade (could be multiple)
- profInfo.txt - Professor information file that contains: prof name; prof ID; prof username; password
- adminInfo.txt - Admin information file that contains: admin ID; admin name; admin username; password
