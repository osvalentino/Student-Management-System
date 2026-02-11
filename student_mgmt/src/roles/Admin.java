package roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import courses.*;

/**
 * Represents an administrator in the student management system.
 * Admins can add/delete courses, professors, and students.
 * Extends User class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class Admin extends User{
    // instance variables
    // inherits id, name, username, password from User class
	
    // methods
    // constructor
	/**
     * Constructs a new Admin with the specified parameters.
     * @param id Admin's unique ID
     * @param name Admin's name
     * @param username Admin's login username
     * @param password Admin's login password
     */
    public Admin (String id, String name, String username, String password){
        super(id, name, username, password);
    }

    // other methods

    /**
     * Creates new course and adds it to courseRegistrar
     * Course id must be unique
     * if Professor does not exist, it will prompt to create professor
     * Professor cannot have time conflict with another course they teach
     * @param courseInfo Course registrar
     * @param profInfo Professor registrar
     * @param studentInfo Student registrar
     */
    public void createNewCourse (ArrayList<Course> courseInfo, ArrayList<Professor> profInfo, ArrayList<Student> studentInfo){
        Scanner scan = new Scanner(System.in);

        // course id
        // we need to check for duplicate ids in course registrar
        // for this reason we use a while loop to reprompt until a unique id is given
        String id = "";
        boolean duplicate = true;
        while (duplicate) {
            System.out.println("Please enter course ID, or type 'q' to quit.");
            id = scan.nextLine();
            if (id.equals("q")) {
                return;
            }
            // checking for duplicate id's
            boolean isDuplicate = false;
            for (Course c : courseInfo) {
                if (c.getID().equals(id)) {
                    System.out.println("The course already exists.");
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
            	break; // ID is unique, exit while loop
            }
        }

        // course name
        System.out.println("Please enter course name, or type 'q' to end.");
        String name = scan.nextLine();
        if (name.equals("q")) {
            return;
        } 

        // course start time
        System.out.println("Please enter course start time, or type 'q' to end.");
        String start = scan.nextLine();
        if (start.equals("q")) {
            return;
        }

        // course end time
        System.out.println("Please enter course end time, or type 'q' to end.");
        String end = scan.nextLine();
        if (end.equals("q")) {
            return;
        }

        // course date
        System.out.println("Please enter course date, or type 'q' to end.");
        String date = scan.nextLine();
        if (date.equals("q")) {
            return;
        }

        // course capacity
        System.out.println("Please enter course capacity, or type 'q' to end.");
        String capacityString = scan.nextLine();
        if (capacityString.equals("q")) {
            return;
        }
        int capacity = Integer.parseInt(capacityString); // parse to int

        // course lecturer
        // we need to initialize a course to make sure there is no conflicting schedule
        // this course will only need to have its lecturer set to it
        // assuming user inputs valid prof, we will add this course to registrar
        Course course = new Course(id, name, null, date, start, end, capacity);

        System.out.println("Please enter course lecturer's id, or type 'q' to end.");
        String lecturer = scan.nextLine();
        if (lecturer.equals("q")) {
            return;
        }
        // find professor from id
        for (Professor p : profInfo) {
            if (p.getID().equals(lecturer)) {   // associated id with professor
                // checking for time conflict
                for (Course c : p.getGivenCourses(courseInfo)) {
                    if (c.hasTimeConflict(course)) {
                        // if there is time conflict we print message
                        course.setLecturer(p);
                        System.out.println("The new added course has time conflict with course: " + c.viewCourse(studentInfo));
                        System.out.println("Unable to add new course: " + course.viewCourse(studentInfo));
                        return;
                    }
                }
                // if professor exists and
                // if there is no time conflict with any course then we have everything we need for a new course
                // after adding course to courseRegistrar we are done
                course.setLecturer(p);
                courseInfo.add(course);
                System.out.println("Successfully added the course: " + course.viewCourse(studentInfo));
                return;
            }
        }

        // if professor does not exists we need to prompt to createNewProfessor
        System.out.println("The professor isn't in the system, please add this professor first.");
        Professor prof = createNewProfessor(profInfo);
        // if they quit out of creating a professor, we also quit from creating a new course
        // this will send you back to the adminMenu
        if (prof == null) {
            return;
        }
        // if valid professor is created we add this course to the course registrar
        course.setLecturer(prof);
        courseInfo.add(course);
        System.out.println("Successfully added the course: " + course.viewCourse(studentInfo));
        return;
    }

    /**
     * Deletes course from registrar if course exists.
     * @param id Course id
     * @param courseInfo Course registrar
     */
    public void deleteCourse (String id, ArrayList<Course> courseInfo){
        for (Course c : courseInfo) {
            if (c.getID().equals(id)) {
                courseInfo.remove(c);
                System.out.println("Course " + c + " deleted successfully.");
                return;
            }
        }
        System.out.println("Course does not exist.");
        return;
    }

    /**
     * Creates new professor and adds them to the professor registrar based on user inputs
     * Will prompt for id, name, username, password
     * id and username cannot be duplicates in system
     * @param profInfo
     */
    public Professor createNewProfessor (ArrayList<Professor> profInfo){
        Scanner scan = new Scanner(System.in);

        // professor id
        // while loop to reprompt for duplicate id's
        String id = "";
        boolean duplicate = true;
        while (duplicate) {
            System.out.println("Please enter the professor's ID, or type 'q' to quit.");
            id = scan.nextLine();
            if (id.equals("q")) {
                return null;
            }
            // checking for duplicate id's
            boolean isDuplicate = false;
            for (Professor p : profInfo) {
                if (p.getID().equals(id)) {
                    System.out.println("The ID already exists.");
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                break;  // ID is unique, exit while loop
            }
        }

        // professor name
        System.out.println("Please enter the professor's name, or type 'q' to quit.");
        String name = scan.nextLine();
        if (name.equals("q")) {
            return null;
        }

        // professor username
        String username = "";
        duplicate = true;
        while (duplicate) {
            System.out.println("Please enter the professor's username, or type 'q' to quit.");
            username = scan.nextLine();
            if (username.equals("q")) {
                return null;
            }
            // checking for duplicate username's
            boolean isDuplicate = false;
            for (Professor p : profInfo) {
                if (p.getUsername().equals(username)) {
                    System.out.println("The username already exists.");
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                break;  // Username is unique, exit while loop
            }
        }

        // professor password
        System.out.println("Please enter the professor's password, or type 'q' to quit.");
        String password = scan.nextLine();
        if (password.equals("q")) {
            return null;
        }

        Professor p = new Professor(id, name, username, password);
        profInfo.add(p);
        System.out.println("Succesfully added new Professor: " + p);
        return p;
    }


    /**
     * Deletes professor from registrar if professor exists.
     * @param id Professor id
     * @param profInfo Professor registrar
     */
    public void deleteProfessor (String id, ArrayList<Professor> profInfo){
        for (Professor p : profInfo) {
            if (p.getID().equals(id)) {
                profInfo.remove(p);
                System.out.println("Professor " + p + " deleted successfully.");
                return;
            }
        }
        System.out.println("Professor does not exist.");
        return;
    }

    /**
     * Creates new professor and adds them to registrar based on user input
     * Will prompt for id, name, username, password and course with grade
     * id and username must be unique
     * @param studentInfo Student registrar
     * @param courseInfo Course registrar
     */
    public void createNewStudent (ArrayList<Student> studentInfo, ArrayList<Course> courseInfo){
        Scanner scan = new Scanner(System.in);

        // student id
        // while loop to reprompt for duplicate id's
        String id = "";
        boolean duplicate = true;
        while (duplicate) {
            System.out.println("Please enter the student's ID, or type 'q' to quit.");
            id = scan.nextLine();
            if (id.equals("q")) {
                return;
            }
            // checking for duplicate id's
            boolean isDuplicate = false;
            for (Student s : studentInfo) {
                if (s.getID().equals(id)) {
                    System.out.println("The ID already exists.");
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                break;  // ID is unique, exit while loop
            }
        }

        // student name
        System.out.println("Please enter the student's name, or type 'q' to quit.");
        String name = scan.nextLine();
        if (name.equals("q")) {
            return;
        }

        // student username
        String username = "";
        duplicate = true;
        while (duplicate) {
            System.out.println("Please enter the student's username, or type 'q' to quit.");
            username = scan.nextLine();
            if (username.equals("q")) {
                return;
            }
            // checking for duplicate username's
            boolean isDuplicate = false;
            for (Student s : studentInfo) {
                if (s.getUsername().equals(username)) {
                    System.out.println("The username you entered is not available.");
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                break;  // Username is unique, exit while loop
            }
        }

        // student password
        System.out.println("Please enter the student's password, or type 'q' to quit.");
        String password = scan.nextLine();
        if (password.equals("q")) {
            return;
        }

        // student courses and grades
        HashMap<Course, String> courseGrade = new HashMap<Course, String>(); // intializing courseGrade map
        String[] input = new String[2];
        while (true) {
            // course input
            System.out.println("Please enter ID of course which this student already took, one at a time, eg. 'CIT590'.");
            System.out.println("Type 'q' to quit, type 'n' to stop adding.");
            input[0] = scan.nextLine();
            if (input[0].equals("q")) {
                return;
            } else if (input[0].equals("n")) { // we can only break out of loop with 'n' or 'q'
                break;
            }
            // grade input
            System.out.println("Please enter the grade, eg. 'A'");
            System.out.println("Type 'q' to quit, type 'n' to stop adding.");
            input[1] = scan.nextLine();
            if (input[1].equals("q")) {
                return;
            } else if (input[1].equals("n")) {
                break;
            }

            // finding course and input into HashMap
            // assuming valid input otherwise put null
            boolean found = false;
            for (Course c : courseInfo) {
                if (c.getID().equals(input[0])) {
                    courseGrade.put(c, input[1]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Course not found in the system.");
            }
    
     }

    // initialize new student and add to registrar
    Student s = new Student(id, name, username, password, courseGrade);
    studentInfo.add(s);
    System.out.println("Succesfully added new student: " + s);
    return;
    }

    /**
     * Deletes student from registrar if student exists.
     * @param id Student id
     * @param studentInfo Student registrar
     */
    public void deleteStudent (String id, ArrayList<Student> studentInfo){
        for (Student s : studentInfo) {
            if (s.getID().equals(id)) {
                studentInfo.remove(s);
                System.out.println("Student " + s + "deleted successfully.");
                return;
            }
        }
        System.out.println("Student does not exist.");
    }

}
