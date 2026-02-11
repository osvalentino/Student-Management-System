package roles;

import java.util.ArrayList;

import courses.*;

/**
 * Represents a professor in the student management system.
 * Professors can view courses they teach and see student lists.
 * Extends User class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class Professor extends User {
    // instance variables
    // inherits id, name, username, password from User class

    // methods 
    // constructor
	
	/**
     * Constructs a new Professor with the specified parameters.
     * @param id Professor's unique ID
     * @param name Professor's full name
     * @param username Professor's login username
     * @param password Professor's login password
     */
    public Professor(String id, String name, String username, String password) {
        super(id, name, username, password);
    }

    // other methods
    
    /**
     * Gets list of courses that this professor lectures.
     * @param courseInfo Course Registrar
     * @return ArrayList of courses taught by this professor
     */
    public ArrayList<Course> getGivenCourses (ArrayList<Course> courseInfo){
        ArrayList<Course> givenCourses = new ArrayList<Course>(); // initialize arraylist
        for (Course c : courseInfo) {
            if (this == c.getLecturer()) {
                givenCourses.add(c);
            }
        }
        return givenCourses;
    }

    /**
     * Gets list of students enrolled in a specific course.
     * @param c Course to get students from
     * @param studentInfo Student Registrar
     * @return ArrayList of students in the course
     */
    public ArrayList <Student> getStudentsInCourse (Course c, ArrayList<Student> studentInfo){
        return c.getStudents(studentInfo);
    }
}
