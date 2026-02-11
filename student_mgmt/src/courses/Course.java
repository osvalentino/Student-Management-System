package courses;
import java.util.ArrayList;

import roles.*;

/**
 * Represents a course in the student management system.
 * Contains course information including ID, name, lecturer, schedule, and capacity.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class Course {
	
    // instance variables
    /**
     * Course ID.
     */
    private String id;

    /**
     * Name of course.
     */
    private String name;

    /**
     * Professor that is lecturing the class.
     */
    private Professor lecturer;

    /**
     * Lecture is scheduled to be on these days of the week.
     */
    private String days;

    /**
     * Lecture is scheduled to begin at this time.
     */
    private int[] startTime = new int[2];

    /**
     * Lecture is scheduled to end at this time.
     */
    private int[] endTime = new int[2];

    /**
     * Max capacity of students in class.
     */
    private int capacity;

    // methods
    // constructor
    /**
     * Constructs a new Course with the specified parameters.
     * @param id Course ID
     * @param name Course name
     * @param lecturer Professor teaching the course
     * @param days Days of the week (e.g., MW, TR)
     * @param start Start time in format "HH:MM"
     * @param end End time in format "HH:MM"
     * @param capacity Maximum number of students
     */
    public Course (String id, String name, Professor lecturer, String days, String start, String end, int capacity){
        this.id = id;
        this.name = name;
        this.lecturer = lecturer;
        this.days = days;
        setStartTime(start);
        setEndTime(end);
        this.capacity = capacity;
    }

    // getters
    public String getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDays(){
        return this.days;
    }
    public int getCapacity(){
        return this.capacity;
    }
    public Professor getLecturer(){
        return this.lecturer;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDays(String days) {
        this.days = days;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    /**
     * sets startTime by parsing string into int[2]
     * @param start String in format HH:MM
     */
    public void setStartTime(String start) {
    	// null check
        if (start == null) {
            this.startTime = new int[]{0, 0};
            return;
        }
        String[] startSplit = start.split(":");
        int[] startTime = new int[2];
        for (int i = 0; i < startTime.length; i++) {
            startTime[i] = Integer.parseInt(startSplit[i]);
        }
        this.startTime = startTime;
    }
    
    /**
     * sets endTime by parsing string into int[2]
     * @param end String in format HH:MM
     */
    public void setEndTime(String end) {
    	// null check
    	if (end == null) {
            this.endTime = new int[]{0, 0};
            return;
        }
        String[] endSplit = end.split(":");
        int[] endTime = new int[2];
        for (int i = 0; i < endTime.length; i++) {
            endTime[i] = Integer.parseInt(endSplit[i]);
        }
        this.endTime = endTime;
    }
    
    public void setLecturer(Professor lecturer) {
        this.lecturer = lecturer;
    }

    // other methods
    
    /**
     * Returns list of students in the course
     * @param studentInfo Student Registrar
     * @return ArrayList of students enrolled in this course
     */
    public ArrayList<Student> getStudents(ArrayList<Student> studentInfo){
        // initialize arraylist
        ArrayList<Student> studentList = new ArrayList<Student>();

        // go through all students
        for (Student s : studentInfo) {
            // go through all student courses
            for (Course c: s.getCourses()) {
                // if course is in student's courses then append student to list
                if (this == c) {
                    studentList.add(s);
                }
            }
        }
        // return list of students in course
        return studentList;
    }

    /**
     * Returns number of students in the class
     * @param studentInfo Student Registrar
     * @return Number of enrolled students
     */
    public int getClassSize (ArrayList<Student> studentInfo){
        return this.getStudents(studentInfo).size();
    }
    
    /**
     * Checks if this course shares any days with another course.
     * @param course Course to check against
     * @return true if courses share at least one day
     */
    public boolean hasDayOverlap(Course course) {
        if (this.days == null || course.days == null) {
            return false;
        }
        // Check each character (day) in this course's days
        for (int i = 0; i < this.days.length(); i++) {
            char day = this.days.charAt(i);
            // If the other course contains this day, there's overlap
            if (course.days.indexOf(day) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if course has time conflict with given course.
     * Two courses conflict if they share at least one day AND their times overlap.
     * @param course Course to check against
     * @return true if there is a time conflict, false otherwise
     */
    public boolean hasTimeConflict (Course course) {
    	// check if days overlap: if no shared days, no conflict possible
        if (!hasDayOverlap(course)) {
            return false;
        }
        
        // Convert times to minutes for easier comparison
        int thisStart = this.startTime[0] * 60 + this.startTime[1];
        int thisEnd = this.endTime[0] * 60 + this.endTime[1];
        int otherStart = course.startTime[0] * 60 + course.startTime[1];
        int otherEnd = course.endTime[0] * 60 + course.endTime[1];
        
        // Two time ranges conflict if one starts before or when the other ends 
        // AND ends after or when the other starts
        // adjacent times (endTime == startTime) ARE conflicts
        if (thisStart <= otherEnd && thisEnd >= otherStart) {
            return true;
        }
        
        return false;
    }

    /**
     * Returns true if time1 is later than time2
     * @param time1 First time as [hour, minute]
     * @param time2 Second time as [hour, minute]
     * @return true if time1 is after time2
     */
    public boolean isLater (int[] time1, int[] time2){
        if (time1[0] > time2[0]) { // later hour returns true
            return true;
        } else if ((time1[0] == time2[0]) && (time1[1] > time2[1])) { 
            // same hour, minutes is greater returns true
            return true;
        } else {
            return false; // if hour is before then return false
        }
    }

    /**
     * Returns formatted time string for the course schedule.
     * @return Time string in format "HH:MM-HH:MM"
     */
    public String printTime(){
        return String.format("%02d", startTime[0]) + ":" + 
        	   String.format("%02d", startTime[1]) + "-" +
        	   String.format("%02d", endTime[0]) + ":" + 
        	   String.format("%02d", endTime[1]);
    }

    /**
     * Returns detailed course information string.
     * @param studentInfo Student Registrar for calculating enrollment
     * @return Formatted course information string
     */
    public String viewCourse (ArrayList<Student> studentInfo){
    	//added null check for lecturer
        String lecturerName = (lecturer != null) ? lecturer.getName() : "TBA";
    	return id + "|" + name + ", " + printTime() + " on " + days +
                ", with course capacity: " + capacity +
                ", students: " + getClassSize(studentInfo) +
                ", lecturer: Professor " + lecturerName;
    }

    /**
     * Returns string representation of the course.
     * @return Course ID and name
     */
    public String toString (){
        return this.id + " " + this.name;
    }
}
