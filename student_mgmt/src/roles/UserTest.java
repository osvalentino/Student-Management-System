package roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User class.
 * Since User is abstract, tests are performed through concrete subclasses.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class UserTest {

	private Student student;
    private Professor professor;
    private Admin admin;

    @BeforeEach
    void setUp() {
        student = new Student("001", "John Doe", "johnd", "pass123", null);
        professor = new Professor("002", "Jane Smith", "janes", "pass456");
        admin = new Admin("003", "Admin User", "admin", "adminpass");
    }

    //toString tests

    @Test
    void testToString_Student() {
        assertEquals("001 John Doe", student.toString());
    }

    @Test
    void testToString_Professor() {
        assertEquals("002 Jane Smith", professor.toString());
    }

    @Test
    void testToString_Admin() {
        assertEquals("003 Admin User", admin.toString());
    }

    @Test
    void testToString_WithSpecialCharacters() {
        Student s = new Student("S01", "Mary-Jane O'Brien", "mary", "pass", null);
        assertEquals("S01 Mary-Jane O'Brien", s.toString());
    }

    @Test
    void testToString_WithNumbers() {
        Professor p = new Professor("P123", "Prof 2nd Edition", "prof2", "pass");
        assertEquals("P123 Prof 2nd Edition", p.toString());
    }

    //getter tests (via subclasses)

    @Test
    void testGetID_Student() {
        assertEquals("001", student.getID());
    }

    @Test
    void testGetID_Professor() {
        assertEquals("002", professor.getID());
    }

    @Test
    void testGetID_Admin() {
        assertEquals("003", admin.getID());
    }

    @Test
    void testGetName_Student() {
        assertEquals("John Doe", student.getName());
    }

    @Test
    void testGetName_Professor() {
        assertEquals("Jane Smith", professor.getName());
    }

    @Test
    void testGetName_Admin() {
        assertEquals("Admin User", admin.getName());
    }

    @Test
    void testGetUsername_Student() {
        assertEquals("johnd", student.getUsername());
    }

    @Test
    void testGetUsername_Professor() {
        assertEquals("janes", professor.getUsername());
    }

    @Test
    void testGetUsername_Admin() {
        assertEquals("admin", admin.getUsername());
    }

    @Test
    void testGetPassword_Student() {
        assertEquals("pass123", student.getPassword());
    }

    @Test
    void testGetPassword_Professor() {
        assertEquals("pass456", professor.getPassword());
    }

    @Test
    void testGetPassword_Admin() {
        assertEquals("adminpass", admin.getPassword());
    }

    //edge case tests

    @Test
    void testConstructor_WithNullId() {
        Student s = new Student(null, "Test", "test", "pass", null);
        assertNull(s.getID());
    }

    @Test
    void testConstructor_WithNullName() {
        Professor p = new Professor("001", null, "test", "pass");
        assertNull(p.getName());
    }

    @Test
    void testConstructor_WithNullUsername() {
        Admin a = new Admin("001", "Test", null, "pass");
        assertNull(a.getUsername());
    }

    @Test
    void testConstructor_WithNullPassword() {
        Student s = new Student("001", "Test", "test", null, null);
        assertNull(s.getPassword());
    }

    @Test
    void testConstructor_WithEmptyStrings() {
        Professor p = new Professor("", "", "", "");
        assertEquals("", p.getID());
        assertEquals("", p.getName());
        assertEquals("", p.getUsername());
        assertEquals("", p.getPassword());
    }

    @Test
    void testToString_WithEmptyStrings() {
        Admin a = new Admin("", "", "admin", "pass");
        assertEquals(" ", a.toString());
    }

    @Test
    void testToString_WithNullValues() {
        Student s = new Student(null, null, "test", "pass", null);
        assertEquals("null null", s.toString());
    }

}
