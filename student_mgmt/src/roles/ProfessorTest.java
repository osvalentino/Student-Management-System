package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;

/**
 * Unit tests for the Professor class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class ProfessorTest {

	private Professor prof1;
    private Professor prof2;
    private Course course1;
    private Course course2;
    private Course course3;
    private Student student1;
    private Student student2;
    private ArrayList<Course> courseRegistrar;
    private ArrayList<Student> studentRegistrar;

    @BeforeEach
    void setUp() {
        // Create professors
        prof1 = new Professor("001", "John Smith", "smith", "password");
        prof2 = new Professor("002", "Jane Doe", "doe", "password");

        // Create courses - prof1 teaches course1 and course2, prof2 teaches course3
        course1 = new Course("CIT590", "Programming Languages", prof1, "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Intro to Software", prof1, "MW", "12:00", "13:30", 120);
        course3 = new Course("CIT592", "Math Foundations", prof2, "TR", "10:00", "11:00", 72);

        // Create course registrar
        courseRegistrar = new ArrayList<Course>();
        courseRegistrar.add(course1);
        courseRegistrar.add(course2);
        courseRegistrar.add(course3);

        // Create students enrolled in course1
        HashMap<Course, String> grades1 = new HashMap<>();
        grades1.put(course1, "A");
        student1 = new Student("S001", "Student One", "student1", "pass", grades1);

        HashMap<Course, String> grades2 = new HashMap<>();
        grades2.put(course1, "B");
        grades2.put(course2, "A-");
        student2 = new Student("S002", "Student Two", "student2", "pass", grades2);

        // Create student registrar
        studentRegistrar = new ArrayList<Student>();
        studentRegistrar.add(student1);
        studentRegistrar.add(student2);
    }

    //constructor tests

    @Test
    void testConstructor_Standard() {
        Professor p = new Professor("003", "New Prof", "newprof", "newpass");
        assertEquals("003", p.getID());
        assertEquals("New Prof", p.getName());
        assertEquals("newprof", p.getUsername());
        assertEquals("newpass", p.getPassword());
    }

    @Test
    void testConstructor_WithNullValues() {
        Professor p = new Professor(null, null, null, null);
        assertNull(p.getID());
        assertNull(p.getName());
        assertNull(p.getUsername());
        assertNull(p.getPassword());
    }

    //getGivenCourses tests

    @Test
    void testGetGivenCourses_MultipleCourses() {
        ArrayList<Course> courses = prof1.getGivenCourses(courseRegistrar);
        assertEquals(2, courses.size());
        assertTrue(courses.contains(course1));
        assertTrue(courses.contains(course2));
    }

    @Test
    void testGetGivenCourses_SingleCourse() {
        ArrayList<Course> courses = prof2.getGivenCourses(courseRegistrar);
        assertEquals(1, courses.size());
        assertTrue(courses.contains(course3));
    }

    @Test
    void testGetGivenCourses_NoCourses() {
        Professor newProf = new Professor("003", "No Courses Prof", "noclass", "pass");
        ArrayList<Course> courses = newProf.getGivenCourses(courseRegistrar);
        assertEquals(0, courses.size());
    }

    @Test
    void testGetGivenCourses_EmptyRegistrar() {
        ArrayList<Course> emptyRegistrar = new ArrayList<>();
        ArrayList<Course> courses = prof1.getGivenCourses(emptyRegistrar);
        assertEquals(0, courses.size());
    }

    @Test
    void testGetGivenCourses_DoesNotIncludeOtherProfCourses() {
        ArrayList<Course> prof1Courses = prof1.getGivenCourses(courseRegistrar);
        assertFalse(prof1Courses.contains(course3)); // course3 is taught by prof2
    }

    //getStudentsInCourse tests

    @Test
    void testGetStudentsInCourse_MultipleStudents() {
        ArrayList<Student> students = prof1.getStudentsInCourse(course1, studentRegistrar);
        assertEquals(2, students.size());
        assertTrue(students.contains(student1));
        assertTrue(students.contains(student2));
    }

    @Test
    void testGetStudentsInCourse_SingleStudent() {
        ArrayList<Student> students = prof1.getStudentsInCourse(course2, studentRegistrar);
        assertEquals(1, students.size());
        assertTrue(students.contains(student2));
    }

    @Test
    void testGetStudentsInCourse_NoStudents() {
        ArrayList<Student> students = prof2.getStudentsInCourse(course3, studentRegistrar);
        assertEquals(0, students.size());
    }

    @Test
    void testGetStudentsInCourse_EmptyRegistrar() {
        ArrayList<Student> emptyRegistrar = new ArrayList<>();
        ArrayList<Student> students = prof1.getStudentsInCourse(course1, emptyRegistrar);
        assertEquals(0, students.size());
    }

    @Test
    void testGetStudentsInCourse_CourseNotTaughtByProf() {
        // prof1 calling getStudentsInCourse on course3 (taught by prof2)
        // This should still work - returns students in that course
        ArrayList<Student> students = prof1.getStudentsInCourse(course3, studentRegistrar);
        assertEquals(0, students.size());
    }

    //inherited toString tests

    @Test
    void testToString() {
        assertEquals("001 John Smith", prof1.toString());
    }

    @Test
    void testToString_DifferentProf() {
        assertEquals("002 Jane Doe", prof2.toString());
    }
}
