package courses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import roles.*;

/**
 * Unit tests for the Course class.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
public class CourseTest {

	private Professor prof1;
    private Professor prof2;
    private Course course1;
    private Course course2;
    private Course course3;
    private ArrayList<Student> studentRegistrar;
    private ArrayList<Course> courseRegistrar;

    @BeforeEach
    public void setUp() {
        // Create professors
        prof1 = new Professor("001", "John Smith", "smith", "password");
        prof2 = new Professor("002", "Jane Doe", "doe", "password");

        // Create courses
        course1 = new Course("CIT590", "Programming Languages", prof1, "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Intro to Software", prof1, "MW", "12:00", "13:30", 120);
        course3 = new Course("CIT592", "Math Foundations", prof2, "TR", "10:00", "11:00", 72);

        // Create student registrar
        studentRegistrar = new ArrayList<Student>();
        courseRegistrar = new ArrayList<Course>();
        courseRegistrar.add(course1);
        courseRegistrar.add(course2);
        courseRegistrar.add(course3);
    }

    //constructor tests

    @Test
    public void testConstructor() {
        Course course = new Course("CIS120", "Programming", prof1, "MWF", "09:00", "10:00", 300);
        assertEquals("CIS120", course.getID());
        assertEquals("Programming", course.getName());
        assertEquals(prof1, course.getLecturer());
        assertEquals("MWF", course.getDays());
        assertEquals(300, course.getCapacity());
    }

    @Test
    public void testConstructorWithNullLecturer() {
        Course course = new Course("CIS121", "Programming II", null, "TR", "09:00", "10:30", 125);
        assertNull(course.getLecturer());
    }

    @Test
    public void testConstructorWithNullTimes() {
        // Should not throw exception due to null checks
        Course course = new Course("CIS122", "Test Course", prof1, "MW", null, null, 50);
        assertNotNull(course);
    }

    //setStartTime tests

    @Test
    public void testSetStartTime_ValidTime() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setStartTime("14:30");
        assertEquals("14:30-10:00", course.printTime());
    }

    @Test
    public void testSetStartTime_Midnight() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setStartTime("00:00");
        assertEquals("00:00-10:00", course.printTime());
    }

    @Test
    public void testSetStartTime_WithLeadingZeros() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setStartTime("08:05");
        assertEquals("08:05-10:00", course.printTime());
    }

    @Test
    public void testSetStartTime_Null() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setStartTime(null);
        // Should set to 00:00
        assertEquals("00:00-10:00", course.printTime());
    }

    @Test
    public void testSetStartTime_EndOfDay() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setStartTime("23:59");
        assertEquals("23:59-10:00", course.printTime());
    }

    //setEndTime tests

    @Test
    public void testSetEndTime_ValidTime() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setEndTime("15:45");
        assertEquals("09:00-15:45", course.printTime());
    }

    @Test
    public void testSetEndTime_Midnight() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setEndTime("00:00");
        assertEquals("09:00-00:00", course.printTime());
    }

    @Test
    public void testSetEndTime_WithLeadingZeros() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setEndTime("07:03");
        assertEquals("09:00-07:03", course.printTime());
    }

    @Test
    public void testSetEndTime_Null() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setEndTime(null);
        // Should set to 00:00
        assertEquals("09:00-00:00", course.printTime());
    }

    @Test
    public void testSetEndTime_EndOfDay() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:00", "10:00", 50);
        course.setEndTime("23:59");
        assertEquals("09:00-23:59", course.printTime());
    }

    //hasDayOverlap tests

    @Test
    public void testHasDayOverlap_SameDays() {
        // MW vs MW - should overlap
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "14:00", "15:00", 50);
        assertTrue(c1.hasDayOverlap(c2));
    }

    @Test
    public void testHasDayOverlap_DifferentDays() {
        // MW vs TR - should NOT overlap
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "TR", "10:00", "11:00", 50);
        assertFalse(c1.hasDayOverlap(c2));
    }

    @Test
    public void testHasDayOverlap_PartialOverlap() {
        // MW vs MWF - should overlap (shares M and W)
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MWF", "10:00", "11:00", 50);
        assertTrue(c1.hasDayOverlap(c2));
    }

    @Test
    public void testHasDayOverlap_SingleDayOverlap() {
        // M vs MW - should overlap (shares M)
        Course c1 = new Course("C1", "Test1", prof1, "M", "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "10:00", "11:00", 50);
        assertTrue(c1.hasDayOverlap(c2));
    }

    @Test
    public void testHasDayOverlap_NullDays() {
        Course c1 = new Course("C1", "Test1", prof1, null, "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "10:00", "11:00", 50);
        assertFalse(c1.hasDayOverlap(c2));
    }

    //hasTimeConflict tests

    @Test
    public void testHasTimeConflict_SameTimeSameDays() {
        // Same time, same days - CONFLICT
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:30", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "10:00", "11:30", 50);
        assertTrue(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_SameTimeDifferentDays() {
        // Same time, different days - NO CONFLICT
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:30", 50);
        Course c2 = new Course("C2", "Test2", prof1, "TR", "10:00", "11:30", 50);
        assertFalse(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_OverlappingTimes() {
        // Overlapping times, same days - CONFLICT
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:30", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "11:00", "12:30", 50);
        assertTrue(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_AdjacentTimes() {
        // Adjacent times - these ARE conflicts
        Course c1 = new Course("C1", "Test1", prof1, "MW", "10:00", "11:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "11:00", "12:00", 50);
        assertTrue(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_NonOverlappingTimes() {
        // Non-overlapping times with gap - NO CONFLICT
        Course c1 = new Course("C1", "Test1", prof1, "MW", "09:00", "10:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "14:00", "15:00", 50);
        assertFalse(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_SmallGapBetweenCourses() {
        // Courses with a 1 minute gap - NO CONFLICT (not adjacent)
        Course c1 = new Course("C1", "Test1", prof1, "MW", "09:00", "10:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "10:01", "11:00", 50);
        assertFalse(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_OneContainsOther() {
        // One time range contains the other - CONFLICT
        Course c1 = new Course("C1", "Test1", prof1, "MW", "09:00", "12:00", 50);
        Course c2 = new Course("C2", "Test2", prof1, "MW", "10:00", "11:00", 50);
        assertTrue(c1.hasTimeConflict(c2));
    }

    @Test
    public void testHasTimeConflict_MWvsTR() {
        // This is the critical bug test - MW and TR should NEVER conflict
        Course mwCourse = new Course("C1", "Test1", prof1, "MW", "10:00", "11:30", 50);
        Course trCourse = new Course("C2", "Test2", prof1, "TR", "10:00", "11:30", 50);
        assertFalse(mwCourse.hasTimeConflict(trCourse));
    }

    //isLater tests

    @Test
    public void testIsLater_LaterHour() {
        int[] time1 = {14, 0};
        int[] time2 = {10, 0};
        assertTrue(course1.isLater(time1, time2));
    }

    @Test
    public void testIsLater_EarlierHour() {
        int[] time1 = {9, 0};
        int[] time2 = {10, 0};
        assertFalse(course1.isLater(time1, time2));
    }

    @Test
    public void testIsLater_SameHourLaterMinute() {
        int[] time1 = {10, 30};
        int[] time2 = {10, 0};
        assertTrue(course1.isLater(time1, time2));
    }

    @Test
    public void testIsLater_SameHourEarlierMinute() {
        int[] time1 = {10, 0};
        int[] time2 = {10, 30};
        assertFalse(course1.isLater(time1, time2));
    }

    @Test
    public void testIsLater_SameTime() {
        int[] time1 = {10, 30};
        int[] time2 = {10, 30};
        assertFalse(course1.isLater(time1, time2));
    }

    //getStudents tests

    @Test
    public void testGetStudents_NoStudentsEnrolled() {
        ArrayList<Student> result = course1.getStudents(studentRegistrar);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetStudents_OneStudentEnrolled() {
        // Create student enrolled in course1
        HashMap<Course, String> grades = new HashMap<>();
        grades.put(course1, "A");
        Student student = new Student("001", "Test Student", "test", "pass", grades);
        studentRegistrar.add(student);

        ArrayList<Student> result = course1.getStudents(studentRegistrar);
        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    public void testGetStudents_MultipleStudentsEnrolled() {
        // Create multiple students enrolled in course1
        HashMap<Course, String> grades1 = new HashMap<>();
        grades1.put(course1, "A");
        Student student1 = new Student("001", "Student One", "test1", "pass", grades1);

        HashMap<Course, String> grades2 = new HashMap<>();
        grades2.put(course1, "B");
        Student student2 = new Student("002", "Student Two", "test2", "pass", grades2);

        studentRegistrar.add(student1);
        studentRegistrar.add(student2);

        ArrayList<Student> result = course1.getStudents(studentRegistrar);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetStudents_StudentInDifferentCourse() {
        // Student enrolled in course2, not course1
        HashMap<Course, String> grades = new HashMap<>();
        grades.put(course2, "A");
        Student student = new Student("001", "Test Student", "test", "pass", grades);
        studentRegistrar.add(student);

        ArrayList<Student> result = course1.getStudents(studentRegistrar);
        assertEquals(0, result.size());
    }

    //getClassSize tests

    @Test
    public void testGetClassSize_Empty() {
        assertEquals(0, course1.getClassSize(studentRegistrar));
    }

    @Test
    public void testGetClassSize_WithStudents() {
        HashMap<Course, String> grades = new HashMap<>();
        grades.put(course1, "A");
        Student student = new Student("001", "Test Student", "test", "pass", grades);
        studentRegistrar.add(student);

        assertEquals(1, course1.getClassSize(studentRegistrar));
    }

    //printTime tests

    @Test
    public void testPrintTime_Standard() {
        assertEquals("16:30-18:00", course1.printTime());
    }

    @Test
    public void testPrintTime_WithLeadingZeros() {
        Course course = new Course("TEST", "Test", prof1, "MW", "09:05", "10:00", 50);
        assertEquals("09:05-10:00", course.printTime());
    }

    //viewCourse tests

    @Test
    public void testViewCourse_WithLecturer() {
        String result = course1.viewCourse(studentRegistrar);
        assertTrue(result.contains("CIT590"));
        assertTrue(result.contains("Programming Languages"));
        assertTrue(result.contains("16:30-18:00"));
        assertTrue(result.contains("MW"));
        assertTrue(result.contains("110"));
        assertTrue(result.contains("John Smith"));
    }

    @Test
    public void testViewCourse_NullLecturer() {
        Course course = new Course("TEST", "Test Course", null, "MW", "10:00", "11:00", 50);
        String result = course.viewCourse(studentRegistrar);
        assertTrue(result.contains("TBA"));
    }

    //toString tests

    @Test
    public void testToString() {
        assertEquals("CIT590 Programming Languages", course1.toString());
    }

    @Test
    public void testToString_DifferentCourse() {
        assertEquals("CIT592 Math Foundations", course3.toString());
    }
    
}
