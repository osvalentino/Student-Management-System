import java.util.ArrayList;
import java.util.Scanner;

import courses.*;
import files.*;
import roles.*;

/**
 * Main controller class for the Student Management System.
 * Handles user interface, login, and menu navigation.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class Controller {
	/**
	 * File reader for loading data files
	 */
    FileInfoReader fileInfoReader = new FileInfoReader();

    /**
	 * List of all courses in the system
	 */
    private static ArrayList <Course> courseRegistrar = new ArrayList<Course>();

    /**
	 * List of all students in the system
	 */
    private static ArrayList <Student> studentRegistrar = new ArrayList<Student>();

    /**
	 * List of all professors in the system
	 */
    private static ArrayList <Professor> profRegistrar = new ArrayList<Professor>();

    /**
	 * List of all admins in the system 
	 */
    private static ArrayList <Admin> adminRegistrar = new ArrayList<Admin>();
    
    /**
     * Main entry point for the Student Management System.
     * Displays main menu and handles user login.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // initializing controller and setting up
        Controller control = new Controller();
        control.setUp("courseInfo.txt", "adminInfo.txt", "profInfo.txt", "studentInfo.txt");

        // Main Menu
        System.out.println("--------------------------");
        System.out.println("Students Management System");
        System.out.println("--------------------------");
        System.out.println(" 1 -- Login as a student");
        System.out.println(" 2 -- Login as a professor");
        System.out.println(" 3 -- Login as an admin");
        System.out.println(" 4 -- Quit the system");
        int loginOption = scanInt();

        // STUDENT LOGIN
        if (loginOption == 1) {
        	Student user = null;
            String[] loginInfo = login(); // asking for login info
            
            // signing into a student user with username and password
            for (Student s : studentRegistrar) {
                if (s.getUsername().equals(loginInfo[0]) && s.getPassword().equals(loginInfo[1])) {
                    user = s;
                    break;
                }
            }
            
            // Check if login was successful
            if (user != null) {
                studentMenu(user, args);
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
            
        // PROFESSOR LOGIN
        } else if (loginOption == 2) {
            // initializing Professor user
        	Professor user = null;
            String[] loginInfo = login();

            // signing into a professor user with username and password
            for (Professor p : profRegistrar) {
                if (p.getUsername().equals(loginInfo[0]) && p.getPassword().equals(loginInfo[1])) {
                    user = p;
                    break;
                }
            }

            // Check if login was successful
            if (user == null) {
                System.out.println("Login failed. Invalid username or password.");
            } else {
                profMenu(user, args);
            }

        // ADMIN LOGIN
        } else if (loginOption == 3) {
            // initializing Admin user
        	Admin user = null;
            String[] loginInfo = login();

            // signing into a professor user with username and password
            for (Admin a : adminRegistrar) {
                if (a.getUsername().equals(loginInfo[0]) && a.getPassword().equals(loginInfo[1])) {
                    user = a;
                }
            }

            // Check if login was successful
            if (user == null) {
                System.out.println("Login failed. Invalid username or password.");
            } else {
                adminMenu(user, args);
            }
            
        // QUIT SYSTEM
        } else if (loginOption == 4) {
            System.out.println("Goodbye!");
            return;
        }
        main(args);
    }
    
    /**
     * Reads files to create courseRegistrar, studentRegistrar, profRegistrar, and adminRegistrar
     * @param courseInfo Course txt file
     * @param adminInfo Admin txt file
     * @param profInfo Professor txt file
     * @param studentInfo Student txt file
     */
    public void setUp(String courseInfo, String adminInfo, String profInfo, String studentInfo){
        fileInfoReader.setUp(courseInfo, adminInfo, profInfo, studentInfo);

        courseRegistrar = fileInfoReader.getCourseInfo();
        studentRegistrar = fileInfoReader.getStudentInfo();
        profRegistrar = fileInfoReader.getProfInfo();
        adminRegistrar = fileInfoReader.getAdminInfo();
    }

    // UI "MAIN" METHODS
    // This includes login, studentMenu, profMenu, adminMenu

    /**
     * Prompts user for username and password.
     * @return String array of length 2 containing username and password
     */
    public static String[] login(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Please enter your username, or type 'q' to quit: ");
        String username = scan.nextLine();
        
        System.out.print("Please enter your password, or type 'q' to quit: ");
        String password = scan.nextLine();
        
        String[] loginInfo = {username, password};
        return loginInfo;
    }

    /**
     * Menu for student login
     * @param user  Student user
     * @param args  main String[] args
     */
    public static void studentMenu (Student user, String[] args){
        // print Menu and prompt user
        System.out.println("--------------------------------");
        System.out.println("     Welcome, " + user.getName());
        System.out.println("--------------------------------");
        System.out.println(" 1 -- View all courses");
        System.out.println(" 2 -- Add courses to your list");
        System.out.println(" 3 -- View enrolled courses");
        System.out.println(" 4 -- Drop course from your list");
        System.out.println(" 5 -- View grades");
        System.out.println(" 6 -- Return to previous menu");
        int menuOption = scanInt();

        // View all courses
        if (menuOption == 1) {
            viewAllCourses();
            
        // Add courses to your list
        } else if (menuOption == 2) {
            user.viewCourseSchedule(studentRegistrar);
            // while loop to keep inputting new courses to add
            // if they quit it will take them back to student menu
            while (true) {
                System.out.println("\nPlease enter the course id you want to add to your list, eg. 'CIT590'.");
                System.out.print("Or enter 'q' to return to previous menu: ");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equals("q")) {
                    break; // sends you back to student menu
                }
                user.addCourse(input, courseRegistrar);
            }

        // View enrolled courses
        } else if (menuOption == 3) {
            user.viewCourseSchedule(studentRegistrar);

        // Drop course from your list
        } else if (menuOption == 4) {
            user.viewCourseSchedule(studentRegistrar);
            // while loop to keep inputting new courses to drop
            // if they quit it will take them back to student menu
            while (true) {
                System.out.println("\nPlease enter the ID of the course which you want to drop, eg. 'CIT590'.");
                System.out.print("Or enter 'q' to return to previous menu: ");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equals("q")) {
                    break; // sends you back to student menu
                }
                user.dropCourse(input);
            }
            
        // View grades - Only show completed courses with actual grades
        } else if (menuOption == 5) {
        	System.out.println("Here are the courses you already taken, with your grade in a letter format:");
            for (Course c : user.getCompletedCourses()) {
            	System.out.println("Grade of " + c.getID() + " " + c.getName() + ": " + user.getGrade(c));
            }

        // Return to previous menu
        } else if (menuOption == 6) {
            return;
        }
        // when they complete an operation they will be sent back to the student menu
        // this will go on until they quit back to main menu
        studentMenu(user, args);
    }

    /**
     * Menu for professor login
     * @param user Professor user
     * @param args main String[] args
     */
    public static void profMenu (Professor user, String[] args){
        // print Menu and prompt user
        System.out.println("\n------------\nWelcome, " + user.getName() + "\n------------");
        System.out.println("1. View given courses.\n" + 
        "2. View student list of the given course.\n" + 
        "3. Return to previous menu.");
        int menuOption = scanInt();

        // View given courses
        if (menuOption == 1) {
            System.out.println("-------------Your course list-------------");
            for (Course c : user.getGivenCourses(courseRegistrar)) {
            	System.out.println(c.viewCourse(studentRegistrar));
            }
            
        // View student list of the given course
        } else if (menuOption == 2) {
            // print out course list
            System.out.println("-------------Your course list-------------");
            for (Course c : user.getGivenCourses(courseRegistrar)) {
            	System.out.println(c.viewCourse(studentRegistrar));
            }

            // prompting user input for course
            System.out.println("\nPlease enter the course ID, eg. 'CIS519'.");
            System.out.print("Or enter 'q' to return to previous menu: ");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if (input.equals("q")) {
            	profMenu(user, args);
                return;
            }

            // seeing students in a course the professor teaches
            // creating instance of course
            Course c = findCourse(input, courseRegistrar);
            // checking if prof teaches this course
            if (c != null && user.getGivenCourses(courseRegistrar).contains(c)) {
                System.out.println("Students in your course " + c.getID() + " " + c.getName() + ":");
                for (Student s : c.getStudents(studentRegistrar)) {
                    System.out.println(s);
                }
            // if not then message appears
            } else {
                System.out.println("The course does not exist or you do not teach this course.");
            }
            
        // Return to previous menu
        } else if (menuOption == 3) {
            return;
        }
        // when they complete an operation they will be sent back to the prof menu
        // this will go on until they quit back to main menu
        profMenu(user, args);
    }
    

    /**
     * Menu for admin login
     * @param user Admin user
     * @param args main String[] args
     */
    public static void adminMenu (Admin user, String[] args){
        // print Menu and prompt user
        System.out.println("\n------------\nWelcome, " + user.getName() + "\n------------");
        System.out.println("1. View all courses.\n" + 
        "2. Add new courses.\n" + 
        "3. Delete courses.\n" + 
        "4. Add new professor.\n" + 
        "5. Delete professor.\n" + 
        "6. Add new student.\n" + 
        "7. Delete student.\n" +
        "8. Return to previous menu.");
        int menuOption = scanInt();

        // View all courses
        if (menuOption == 1) {
            viewAllCourses();

        // Add new courses
        } else if (menuOption == 2) {
            user.createNewCourse(courseRegistrar, profRegistrar, studentRegistrar);
            
        // Delete courses
        } else if (menuOption == 3) {
            while (true) {
                System.out.println("\nPlease enter the ID of the course which you want to delete, eg. CIT590.");
                System.out.print("Or enter 'q' to return to previous menu: ");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equals("q")) {
                    break; // sends you back to student menu
                }
                user.deleteCourse(input, courseRegistrar); // delete course
            }
            
        // Add new professor
        } else if (menuOption == 4) {
            user.createNewProfessor(profRegistrar);
            
        // Delete professor
        } else if (menuOption == 5) {
            while (true) {
                System.out.println("\nPlease enter the ID of the professor which you want to delete, eg. 001.");
                System.out.print("Or enter 'q' to return to previous menu: ");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equals("q")) {
                    break; // sends you back to student menu
                }
                user.deleteProfessor(input, profRegistrar); // delete professor
            }
            
        // Add new student
        } else if (menuOption == 6) {
            user.createNewStudent(studentRegistrar, courseRegistrar);

        // Delete student
        } else if (menuOption == 7) {
            while (true) {
                System.out.println("\nPlease enter the ID of the student which you want to delete, eg. 001.");
                System.out.print("Or enter 'q' to return to previous menu: ");
                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();
                if (input.equals("q")) {
                    break; // sends you back to student menu
                }
                user.deleteStudent(input, studentRegistrar); // delete student
            }
            
        // Return to previous menu
        } else if (menuOption == 8) {
            return;
        }

        // when they complete an operation they will be sent back to the prof menu
        // this will go on until they quit back to main menu
        adminMenu(user, args);
    }

    // HELPER UI METHODS
    // methods to help with prompting, printing, or other miscellanous needs

    /**
     * Prompts input of integers then parses
     * @return The integer option selected by user
     */
    public static int scanInt(){
        System.out.print("\nPlease select your option, eg. '1': ");
        Scanner scan = new Scanner (System.in);
        int option = Integer.parseInt(scan.next());
        return option;
    }

    /**
     * View all courses in course registrar
     */
    public static void viewAllCourses(){
        for (Course c : courseRegistrar) {
            System.out.println(c.viewCourse(studentRegistrar));
        }
    }
    
    /**
     * Finds course with given course id
     * @param id Course id
     * @param courseRegistrar list of Courses
     * @return Course if found, null otherwise
     */
    public static Course findCourse (String id, ArrayList <Course> courseRegistrar){
        for (Course c : courseRegistrar) {
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return null;
    }
}
