package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import roles.*;
import courses.*;

/**
 * Handles reading and parsing of data files for the student management system.
 * Loads course, admin, professor, and student information from text files.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class FileInfoReader {

	/**
	 * List of all courses loaded from file.
	 */
    private ArrayList<Course> courseInfo = new ArrayList<Course>();

    /**
     * List of all admins loaded from file.
     */
    private ArrayList<Admin> adminInfo = new ArrayList<Admin>();

    /**
     * List of all professors loaded from file.
     */
    private ArrayList<Professor> profInfo = new ArrayList<Professor>();

    /**
     * List of all students loaded from file.
     */
    private ArrayList<Student> studentInfo = new ArrayList<Student>();

    /**
     * Reads and parses all data files to populate the registrars.
     * Files are loaded in order: admin, professor, course, student.
     * Professor data must be loaded before courses (for lecturer assignment).
     * Course data must be loaded before students (for enrollment).
     * @param courseTxt Path to course information file
     * @param adminTxt Path to admin information file
     * @param profTxt Path to professor information file
     * @param studentTxt Path to student information file
     */
    public void setUp (String courseTxt, String adminTxt, String profTxt, String studentTxt) {
        // admin Setup
        try{
            File adminFile = new File(adminTxt);
            FileReader fd = new FileReader(adminFile);
            BufferedReader br = new BufferedReader(fd);
            while (true) {
                String line = br.readLine();
				if (line == null) {
					break;
				}
				String [] array = line.trim().split("; ");
				Admin newAdmin = new Admin(array[0], array[1], array[2], array[3]);
				adminInfo.add(newAdmin);
            }
            fd.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        // professor Setup
        try{
            File profFile = new File(profTxt);
            FileReader fd = new FileReader(profFile);
            BufferedReader br = new BufferedReader(fd);
            while (true) {
                String line = br.readLine();
				if (line == null) {
					break;
				}
				String [] array = line.trim().split("; ");
				Professor newProf = new Professor(array[1], array[0], array[2], array[3]);
				profInfo.add(newProf);
            }
            fd.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        // course Setup
        try{
            File courseFile = new File(courseTxt);
            FileReader fd = new FileReader(courseFile);
            BufferedReader br = new BufferedReader(fd);
            while (true) {
                String line = br.readLine();
				if (line == null) {
					break;
				}
				String [] array = line.trim().split("; ");

                // getting professor for course
                Professor lecturer = null;
                for (Professor p : profInfo) {
                    if (p.getName().equals(array[2])) {
                        lecturer = p;
                        break;
                    }
                }

                // with everything we create new course
				Course newCourse = new Course(array[0], array[1], lecturer, array[3], array[4], array[5], Integer.parseInt(array[6]));
				courseInfo.add(newCourse);
            }
            fd.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        // student Setup
        try{
            File studentFile = new File(studentTxt);
            FileReader fd = new FileReader(studentFile);
            BufferedReader br = new BufferedReader(fd);
            while (true) {
                String line = br.readLine();
				if (line == null) {
					break;
				}
				String[] array = line.trim().split("; ");

                // this is list of enrolled courses by student
                // we are splitting up the string by course:grade
                // next we will turn the course string into Course class
                HashMap<Course, String> courseGrade = new HashMap<>();
                
                // Check if student has any courses (array length > 4 and not empty)
                if (array.length > 4 && !array[4].isEmpty()) {
                    String[] courseGradeArray = array[4].split(", ");
                
		            for (String s : courseGradeArray) {
		                String[] courseGradeString = s.split(": ");
		
		                // looking for course
		                for (Course c : courseInfo) {
		                    if (c.getID().equals(courseGradeString[0])) {
		                        courseGrade.put(c, courseGradeString[1]);
		                        break;
		                    }
		                }
		            }
                }
                
				Student newStudent = new Student(array[0], array[1], array[2], array[3], courseGrade);
				studentInfo.add(newStudent);
            }
            fd.close();
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }

    // getters
    
    /**
     * Gets the list of all courses.
     * @return ArrayList of Course objects
     */
    public ArrayList<Course> getCourseInfo(){
        return this.courseInfo;
    }

    /**
     * Gets the list of all admins.
     * @return ArrayList of Admin objects
     */
    public ArrayList<Admin> getAdminInfo (){
        return this.adminInfo;
    }

    /**
     * Gets the list of all professors.
     * @return ArrayList of Professor objects
     */
    public ArrayList<Professor> getProfInfo (){
        return this.profInfo;
    }

    /**
     * Gets the list of all students.
     * @return ArrayList of Student objects
     */
    public ArrayList<Student> getStudentInfo (){
        return this.studentInfo;
    }

}
