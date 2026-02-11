package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;

/**
 * Unit tests for the Admin class.
 * I/O methods can be skipped per assignment.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class AdminTest {

	private Admin admin;
    private Professor prof1;
    private Professor prof2;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private ArrayList<Course> courseRegistrar;
    private ArrayList<Professor> profRegistrar;
    private ArrayList<Student> studentRegistrar;

    @BeforeEach
    void setUp() {
        // Create admin
        admin = new Admin("A001", "Admin User", "admin", "adminpass");

        // Create professors
        prof1 = new Professor("P001", "John Smith", "smith", "password");
        prof2 = new Professor("P002", "Jane Doe", "doe", "password");

        // Create courses
        course1 = new Course("CIT590", "Programming Languages", prof1, "MW", "16:30", "18:00", 110);
        course2 = new Course("CIT591", "Intro to Software", prof1, "MW", "12:00", "13:30", 120);

        // Create students
        HashMap<Course, String> grades1 = new HashMap<>();
        grades1.put(course1, "A");
        student1 = new Student("S001", "Student One", "student1", "pass", grades1);
        student2 = new Student("S002", "Student Two", "student2", "pass", null);

        // Create registrars
        courseRegistrar = new ArrayList<Course>();
        courseRegistrar.add(course1);
        courseRegistrar.add(course2);

        profRegistrar = new ArrayList<Professor>();
        profRegistrar.add(prof1);
        profRegistrar.add(prof2);

        studentRegistrar = new ArrayList<Student>();
        studentRegistrar.add(student1);
        studentRegistrar.add(student2);
    }

    //constructor tests

    @Test
    void testConstructor_Standard() {
        Admin a = new Admin("A002", "New Admin", "newadmin", "newpass");
        assertEquals("A002", a.getID());
        assertEquals("New Admin", a.getName());
        assertEquals("newadmin", a.getUsername());
        assertEquals("newpass", a.getPassword());
    }

    @Test
    void testConstructor_WithNullValues() {
        Admin a = new Admin(null, null, null, null);
        assertNull(a.getID());
        assertNull(a.getName());
        assertNull(a.getUsername());
        assertNull(a.getPassword());
    }

    //deleteCourse tests

    @Test
    void testDeleteCourse_Success() {
        assertEquals(2, courseRegistrar.size());
        admin.deleteCourse("CIT590", courseRegistrar);
        assertEquals(1, courseRegistrar.size());
        assertFalse(courseRegistrar.contains(course1));
    }

    @Test
    void testDeleteCourse_DoesNotExist() {
        assertEquals(2, courseRegistrar.size());
        admin.deleteCourse("FAKE123", courseRegistrar);
        assertEquals(2, courseRegistrar.size());
    }

    @Test
    void testDeleteCourse_EmptyRegistrar() {
        ArrayList<Course> emptyRegistrar = new ArrayList<>();
        admin.deleteCourse("CIT590", emptyRegistrar);
        assertEquals(0, emptyRegistrar.size());
    }

    @Test
    void testDeleteCourse_DeleteAll() {
        admin.deleteCourse("CIT590", courseRegistrar);
        admin.deleteCourse("CIT591", courseRegistrar);
        assertEquals(0, courseRegistrar.size());
    }

    @Test
    void testDeleteCourse_DeleteSameTwice() {
        admin.deleteCourse("CIT590", courseRegistrar);
        assertEquals(1, courseRegistrar.size());
        admin.deleteCourse("CIT590", courseRegistrar); // Already deleted
        assertEquals(1, courseRegistrar.size());
    }

    //deleteProfessor tests

    @Test
    void testDeleteProfessor_Success() {
        assertEquals(2, profRegistrar.size());
        admin.deleteProfessor("P001", profRegistrar);
        assertEquals(1, profRegistrar.size());
        assertFalse(profRegistrar.contains(prof1));
    }

    @Test
    void testDeleteProfessor_DoesNotExist() {
        assertEquals(2, profRegistrar.size());
        admin.deleteProfessor("FAKE", profRegistrar);
        assertEquals(2, profRegistrar.size());
    }

    @Test
    void testDeleteProfessor_EmptyRegistrar() {
        ArrayList<Professor> emptyRegistrar = new ArrayList<>();
        admin.deleteProfessor("P001", emptyRegistrar);
        assertEquals(0, emptyRegistrar.size());
    }

    @Test
    void testDeleteProfessor_DeleteAll() {
        admin.deleteProfessor("P001", profRegistrar);
        admin.deleteProfessor("P002", profRegistrar);
        assertEquals(0, profRegistrar.size());
    }

    @Test
    void testDeleteProfessor_DeleteSameTwice() {
        admin.deleteProfessor("P001", profRegistrar);
        assertEquals(1, profRegistrar.size());
        admin.deleteProfessor("P001", profRegistrar); // Already deleted
        assertEquals(1, profRegistrar.size());
    }

    //deleteStudent tests

    @Test
    void testDeleteStudent_Success() {
        assertEquals(2, studentRegistrar.size());
        admin.deleteStudent("S001", studentRegistrar);
        assertEquals(1, studentRegistrar.size());
        assertFalse(studentRegistrar.contains(student1));
    }

    @Test
    void testDeleteStudent_DoesNotExist() {
        assertEquals(2, studentRegistrar.size());
        admin.deleteStudent("FAKE", studentRegistrar);
        assertEquals(2, studentRegistrar.size());
    }

    @Test
    void testDeleteStudent_EmptyRegistrar() {
        ArrayList<Student> emptyRegistrar = new ArrayList<>();
        admin.deleteStudent("S001", emptyRegistrar);
        assertEquals(0, emptyRegistrar.size());
    }

    @Test
    void testDeleteStudent_DeleteAll() {
        admin.deleteStudent("S001", studentRegistrar);
        admin.deleteStudent("S002", studentRegistrar);
        assertEquals(0, studentRegistrar.size());
    }

    @Test
    void testDeleteStudent_DeleteSameTwice() {
        admin.deleteStudent("S001", studentRegistrar);
        assertEquals(1, studentRegistrar.size());
        admin.deleteStudent("S001", studentRegistrar); // Already deleted
        assertEquals(1, studentRegistrar.size());
    }

    //inherited toString tests

    @Test
    void testToString() {
        assertEquals("A001 Admin User", admin.toString());
    }

}
