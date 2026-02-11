package roles;
import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;

/**
 * Represents a student in the student management system.
 * Students can view courses, add/drop courses, and view their grades.
 * Extends User class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class Student extends User{
    // instance variables
    // inherits id, name, username, password from User class
	
    /**
     * HashMap for class and class grade.
     * Contains both completed courses (with letter grades) and current courses (with "" grade).
     */
    private HashMap<Course, String> courseGrade;

    // methods
    // constructor
    /**
     * Constructs a new Student with the specified parameters.
     * @param id Student's unique ID
     * @param name Student's full name
     * @param username Student's login username
     * @param password Student's login password
     * @param courseGrade HashMap of courses and grades
     */
    public Student (String id, String name, String username, String password, HashMap<Course,String> courseGrade){
        super(id, name, username, password);
        // Initialize to empty HashMap if null is passed
        if (courseGrade == null) {
            this.courseGrade = new HashMap<Course, String>();
        } else {
            this.courseGrade = courseGrade;
        }
    }

    // setters

    // getters
    // inherits getID, getName, getUsername, getPassword from User class

    /**
     * Gets the course and grade HashMap.
     * @return HashMap of courses and grades
     */
    public HashMap<Course,String> getCourseAndGrade (){
        return this.courseGrade;
    }

    /**
     * Gets list of ALL courses (both completed and current).
     * @return ArrayList of all courses
     */
    public ArrayList<Course> getCourses (){
        return new ArrayList<Course> (this.courseGrade.keySet()); 
    }
    
    /**
     * Gets list of current semester courses only (courses with empty "" grade).
     * Per Piazza #270: "View My Courses" refers to current semester schedule only.
     * @return ArrayList of current semester courses
     */
    public ArrayList<Course> getCurrentCourses() {
        ArrayList<Course> currentCourses = new ArrayList<Course>();
        for (Course c : this.courseGrade.keySet()) {
            String grade = this.courseGrade.get(c);
            // Current semester courses have empty grade
            if (grade == null || grade.isEmpty()) {
                currentCourses.add(c);
            }
        }
        return currentCourses;
    }

    /**
     * Gets list of completed courses only (courses with actual letter grades).
     * Per Piazza #270: "View Grades" shows only completed courses.
     * @return ArrayList of completed courses with grades
     */
    public ArrayList<Course> getCompletedCourses() {
        ArrayList<Course> completedCourses = new ArrayList<Course>();
        for (Course c : this.courseGrade.keySet()) {
            String grade = this.courseGrade.get(c);
            // Completed courses have actual grades (not null, not empty)
            if (grade != null && !grade.isEmpty()) {
                completedCourses.add(c);
            }
        }
        return completedCourses;
    }
    
    /**
     * Gets the grade for a specific course.
     * @param c Course to get grade for
     * @return Grade string (e.g., "A", "B+"), or null if not enrolled
     */
    public String getGrade (Course c){
        return courseGrade.get(c);
    }

    // other methods

    /**
     * Adds course to student's current semester schedule. 
     * Cannot add a course already completed (with grade).
     * Cannot add a course already in current schedule.
     * Cannot add if time conflict with current schedule (not past courses).
     * @param id Course ID
     * @param courseInfo Course Registrar
     */
    public void addCourse(String id, ArrayList<Course> courseInfo){
        // Find the course that is referenced
        for (Course course : courseInfo) {
            if (course.getID().equals(id)) {
                // Check if already completed this course (has grade)
                // Per Piazza #270 Q6: Cannot re-add completed courses
                if (this.getCompletedCourses().contains(course)) {
                    System.out.println("The course you selected is already completed. Cannot re-add.");
                    return;
                }
                
                // Check if already in current schedule
                if (this.getCurrentCourses().contains(course)) {
                    System.out.println("The course you selected is already in your list.");
                    return;
                }

                // Check for time conflict with CURRENT schedule only (not past courses)
                // Per Piazza #270: Time conflict only checks current schedule
                for (Course c : getCurrentCourses()) {
                    if (course.hasTimeConflict(c)) {
                        System.out.println("The course you selected has time conflict with " + c);
                        return;
                    }
                }
                
                // Add course with empty grade (current semester)
                System.out.println("Course added successfully.");
                this.courseGrade.put(course, "");
                return;
            }
        }
        System.out.println("Course does not exist.");
        return;
    }

    /**
     * Drops course from student's current semester schedule.
     * Cannot drop completed courses (those with grades).
     * @param id Course ID
     */
    public void dropCourse(String id){
        // Only check current courses (can't drop completed courses anyway)
        for (Course course : this.getCurrentCourses()) {
            if (course.getID().equals(id)) {
                System.out.println("Course dropped successfully.");
                this.courseGrade.remove(course);
                return;
            }
        }
        
        // Check if it's a completed course
        for (Course course : this.getCompletedCourses()) {
            if (course.getID().equals(id)) {
                System.out.println("Cannot drop a completed course with a grade.");
                return;
            }
        }
        
        // Not found at all
        System.out.println("The course is not in your schedule.");
        return;
    }

    /**
     * Displays the student's current semester schedule.
     * Per Piazza #270 Q5: Shows only current courses, not past completed courses.
     * @param studentRegistrar Student Registrar for course info display
     */
    public void viewCourseSchedule (ArrayList<Student> studentRegistrar){
        System.out.println("The courses in your list:");
        // Only show current semester courses (not completed/past courses)
        for (Course c : getCurrentCourses()) {
            System.out.println(c.viewCourse(studentRegistrar));
        }
    }
}