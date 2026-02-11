package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;

/**
 * Unit tests for the Student class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class StudentTest {

	private Professor prof1;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private Student student;
    private ArrayList<Course> courseRegistrar;
    private ArrayList<Student> studentRegistrar;

    @BeforeEach
    void setUp() {
        // Create professor
        prof1 = new Professor("001", "John Smith", "smith", "password");

        // Create courses
        course1 = new Course("CIT590", "Programming Languages", prof1, "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Intro to Software", prof1, "MW", "12:00", "13:30", 120);
        course3 = new Course("CIT592", "Math Foundations", prof1, "TR", "10:00", "11:00", 72);
        course4 = new Course("CIT593", "Computer Systems", prof1, "MW", "17:00", "18:30", 72); // conflicts with course1
        course5 = new Course("CIS191", "Linux Skills", prof1, "F", "13:30", "15:00", 15);

        // Create course registrar
        courseRegistrar = new ArrayList<Course>();
        courseRegistrar.add(course1);
        courseRegistrar.add(course2);
        courseRegistrar.add(course3);
        courseRegistrar.add(course4);
        courseRegistrar.add(course5);

        // Create student registrar
        studentRegistrar = new ArrayList<Student>();

        // Create student with completed course (course5 with grade "A") - simulates data from studentInfo.txt
        HashMap<Course, String> grades = new HashMap<>();
        grades.put(course5, "A");  // Completed course with grade
        student = new Student("001", "Test Student", "test", "pass", grades);
        studentRegistrar.add(student);
    }

    //constructor tests

    @Test
    void testConstructor_Standard() {
        HashMap<Course, String> grades = new HashMap<>();
        grades.put(course1, "B+");
        Student s = new Student("002", "Jane Doe", "janed", "password", grades);
        
        assertEquals("002", s.getID());
        assertEquals("Jane Doe", s.getName());
        assertEquals("janed", s.getUsername());
        assertEquals("password", s.getPassword());
        assertEquals(1, s.getCourses().size());
    }

    @Test
    void testConstructor_NullCourseGrade() {
        Student s = new Student("003", "No Courses", "nocourse", "pass", null);
        assertNotNull(s.getCourseAndGrade());
        assertEquals(0, s.getCourses().size());
    }

    @Test
    void testConstructor_EmptyCourseGrade() {
        HashMap<Course, String> emptyGrades = new HashMap<>();
        Student s = new Student("004", "Empty Courses", "empty", "pass", emptyGrades);
        assertEquals(0, s.getCourses().size());
    }

    //getCourses tests

    @Test
    void testGetCourses_ReturnsAll() {
        // Add a current course
        student.addCourse("CIT590", courseRegistrar);
        // getCourses should return both completed and current
        ArrayList<Course> allCourses = student.getCourses();
        assertEquals(2, allCourses.size()); // 1 completed + 1 current
    }

    //getCurrentCourses tests

    @Test
    void testGetCurrentCourses_Empty() {
        // Student only has completed course, no current courses
        ArrayList<Course> currentCourses = student.getCurrentCourses();
        assertEquals(0, currentCourses.size());
    }

    @Test
    void testGetCurrentCourses_AfterAddingCourse() {
        student.addCourse("CIT590", courseRegistrar);
        ArrayList<Course> currentCourses = student.getCurrentCourses();
        assertEquals(1, currentCourses.size());
        assertTrue(currentCourses.contains(course1));
    }

    @Test
    void testGetCurrentCourses_DoesNotIncludeCompleted() {
        student.addCourse("CIT590", courseRegistrar);
        ArrayList<Course> currentCourses = student.getCurrentCourses();
        // Should NOT contain course5 (completed with grade "A")
        assertFalse(currentCourses.contains(course5));
    }

    //getCompletedCourses tests

    @Test
    void testGetCompletedCourses_HasCompleted() {
        ArrayList<Course> completedCourses = student.getCompletedCourses();
        assertEquals(1, completedCourses.size());
        assertTrue(completedCourses.contains(course5));
    }

    @Test
    void testGetCompletedCourses_DoesNotIncludeCurrent() {
        student.addCourse("CIT590", courseRegistrar);
        ArrayList<Course> completedCourses = student.getCompletedCourses();
        // Should NOT contain course1 (just added, no grade)
        assertFalse(completedCourses.contains(course1));
        // Should still contain course5
        assertTrue(completedCourses.contains(course5));
    }

    @Test
    void testGetCompletedCourses_EmptyIfNoGrades() {
        Student newStudent = new Student("002", "New", "new", "pass", null);
        assertEquals(0, newStudent.getCompletedCourses().size());
    }

    //getGrade tests

    @Test
    void testGetGrade_CompletedCourse() {
        assertEquals("A", student.getGrade(course5));
    }

    @Test
    void testGetGrade_CurrentCourse() {
        student.addCourse("CIT590", courseRegistrar);
        assertEquals("", student.getGrade(course1));
    }

    @Test
    void testGetGrade_NotEnrolled() {
        assertNull(student.getGrade(course1));
    }

    //addCourse tests

    @Test
    void testAddCourse_Success() {
        student.addCourse("CIT590", courseRegistrar);
        assertTrue(student.getCurrentCourses().contains(course1));
    }

    @Test
    void testAddCourse_AlreadyInCurrentSchedule() {
        student.addCourse("CIT590", courseRegistrar);
        int sizeBefore = student.getCurrentCourses().size();
        student.addCourse("CIT590", courseRegistrar); // Try to add again
        assertEquals(sizeBefore, student.getCurrentCourses().size());
    }

    @Test
    void testAddCourse_CannotReAddCompletedCourse() {
        // Cannot re-add completed courses
        int sizeBefore = student.getCourses().size();
        student.addCourse("CIS191", courseRegistrar); // course5 is already completed with grade "A"
        assertEquals(sizeBefore, student.getCourses().size());
    }

    @Test
    void testAddCourse_TimeConflictWithCurrentCourse() {
        student.addCourse("CIT590", courseRegistrar); // MW 16:30-18:00
        int sizeBefore = student.getCurrentCourses().size();
        student.addCourse("CIT593", courseRegistrar); // MW 17:00-18:30 - conflicts
        assertEquals(sizeBefore, student.getCurrentCourses().size());
    }

    @Test
    void testAddCourse_CourseDoesNotExist() {
        int sizeBefore = student.getCourses().size();
        student.addCourse("FAKE123", courseRegistrar);
        assertEquals(sizeBefore, student.getCourses().size());
    }

    @Test
    void testAddCourse_NoConflictDifferentDays() {
        student.addCourse("CIT590", courseRegistrar); // MW
        student.addCourse("CIT592", courseRegistrar); // TR - different days
        assertEquals(2, student.getCurrentCourses().size());
    }

    //dropCourse tests

    @Test
    void testDropCourse_Success() {
        student.addCourse("CIT590", courseRegistrar);
        assertTrue(student.getCurrentCourses().contains(course1));
        student.dropCourse("CIT590");
        assertFalse(student.getCurrentCourses().contains(course1));
    }

    @Test
    void testDropCourse_CannotDropCompletedCourse() {
        // Cannot drop completed courses
        int sizeBefore = student.getCompletedCourses().size();
        student.dropCourse("CIS191"); // course5 has grade "A"
        assertEquals(sizeBefore, student.getCompletedCourses().size());
    }

    @Test
    void testDropCourse_NotInSchedule() {
        int sizeBefore = student.getCourses().size();
        student.dropCourse("CIT590"); // Not enrolled
        assertEquals(sizeBefore, student.getCourses().size());
    }

    //viewCourseSchedule tests

    @Test
    void testViewCourseSchedule_ShowsOnlyCurrentCourses() {
        // Should show only current semester courses
        // Student has completed course5, should not appear in schedule view
        // This test just ensures no exception is thrown
        assertDoesNotThrow(() -> student.viewCourseSchedule(studentRegistrar));
    }

    @Test
    void testViewCourseSchedule_EmptyForNewStudent() {
        // If student logs in first time, course list should be empty
        // (even if they have completed courses from file)
        Student s = new Student("002", "New", "new", "pass", null);
        assertEquals(0, s.getCurrentCourses().size());
    }

    //inherited toString tests

    @Test
    void testToString() {
        assertEquals("001 Test Student", student.toString());
    }

}
