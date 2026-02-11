import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import courses.Course;
import roles.Professor;

/**
 * Unit tests for the Controller class.
 * Note: Most methods use Scanner for user input and are not tested here (I/O methods can be skipped per assignment).
 * The findCourse method is tested as it does not require I/O.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class ControllerTest {

	private Professor prof1;
    private Course course1;
    private Course course2;
    private Course course3;
    private ArrayList<Course> courseRegistrar;

    @BeforeEach
    public void setUp() {
        // Create professor
        prof1 = new Professor("001", "John Smith", "smith", "password");

        // Create courses
        course1 = new Course("CIT590", "Programming Languages", prof1, "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Intro to Software", prof1, "MW", "12:00", "13:30", 120);
        course3 = new Course("CIT592", "Math Foundations", prof1, "TR", "10:00", "11:00", 72);

        // Create course registrar
        courseRegistrar = new ArrayList<Course>();
        courseRegistrar.add(course1);
        courseRegistrar.add(course2);
        courseRegistrar.add(course3);
    }

    //findCourse tests

    @Test
    public void testFindCourse_ExistingCourse() {
        Course result = Controller.findCourse("CIT590", courseRegistrar);
        assertNotNull(result);
        assertEquals(course1, result);
        assertEquals("CIT590", result.getID());
    }

    @Test
    public void testFindCourse_SecondCourse() {
        Course result = Controller.findCourse("CIT591", courseRegistrar);
        assertNotNull(result);
        assertEquals(course2, result);
        assertEquals("CIT591", result.getID());
    }

    @Test
    public void testFindCourse_LastCourse() {
        Course result = Controller.findCourse("CIT592", courseRegistrar);
        assertNotNull(result);
        assertEquals(course3, result);
        assertEquals("CIT592", result.getID());
    }

    @Test
    public void testFindCourse_NonExistingCourse() {
        Course result = Controller.findCourse("FAKE123", courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_EmptyRegistrar() {
        ArrayList<Course> emptyRegistrar = new ArrayList<Course>();
        Course result = Controller.findCourse("CIT590", emptyRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_NullId() {
        // If id is null, getID().equals(null) will return false (not throw exception)
        // but null.equals(something) would throw NPE. The implementation uses c.getID().equals(id)
        // so passing null id will just return null (no match found)
        Course result = Controller.findCourse(null, courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_EmptyStringId() {
        Course result = Controller.findCourse("", courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_CaseSensitive() {
        // IDs are case-sensitive, so "cit590" should not match "CIT590"
        Course result = Controller.findCourse("cit590", courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_WithWhitespace() {
        // ID with whitespace should not match
        Course result = Controller.findCourse(" CIT590", courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_PartialMatch() {
        // Partial ID should not match
        Course result = Controller.findCourse("CIT59", courseRegistrar);
        assertNull(result);
    }

    @Test
    public void testFindCourse_ReturnsCorrectCourseObject() {
        Course result = Controller.findCourse("CIT590", courseRegistrar);
        assertNotNull(result);
        assertEquals("Programming Languages", result.getName());
        assertEquals(prof1, result.getLecturer());
        assertEquals("MW", result.getDays());
        assertEquals(110, result.getCapacity());
    }

}
