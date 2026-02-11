package files;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import courses.Course;
import roles.*;

/**
 * Unit tests for the FileInfoReader class.
 * Note: The setUp() method performs file I/O and can be skipped per assignment.
 * These tests verify the getter methods return properly initialized lists.
 * @author Jason Lu, Osvaldo Valentino Aceves
 */
class FileInfoReaderTest {

	private FileInfoReader fileInfoReader;

    @BeforeEach
    public void setUp() {
        fileInfoReader = new FileInfoReader();
    }

    // Test that ArrayLists are properly initialized (before setUp is called)

    @Test
    public void testGetCourseInfo_InitiallyEmpty() {
        ArrayList<Course> courses = fileInfoReader.getCourseInfo();
        assertNotNull(courses);
        assertEquals(0, courses.size());
    }

    @Test
    public void testGetAdminInfo_InitiallyEmpty() {
        ArrayList<Admin> admins = fileInfoReader.getAdminInfo();
        assertNotNull(admins);
        assertEquals(0, admins.size());
    }

    @Test
    public void testGetProfInfo_InitiallyEmpty() {
        ArrayList<Professor> profs = fileInfoReader.getProfInfo();
        assertNotNull(profs);
        assertEquals(0, profs.size());
    }

    @Test
    public void testGetStudentInfo_InitiallyEmpty() {
        ArrayList<Student> students = fileInfoReader.getStudentInfo();
        assertNotNull(students);
        assertEquals(0, students.size());
    }

    // Return type tests

    @Test
    public void testGetCourseInfo_ReturnsArrayList() {
        assertTrue(fileInfoReader.getCourseInfo() instanceof ArrayList);
    }

    @Test
    public void testGetAdminInfo_ReturnsArrayList() {
        assertTrue(fileInfoReader.getAdminInfo() instanceof ArrayList);
    }

    @Test
    public void testGetProfInfo_ReturnsArrayList() {
        assertTrue(fileInfoReader.getProfInfo() instanceof ArrayList);
    }

    @Test
    public void testGetStudentInfo_ReturnsArrayList() {
        assertTrue(fileInfoReader.getStudentInfo() instanceof ArrayList);
    }

    // Multiple calls return same list

    @Test
    public void testGetCourseInfo_ReturnsSameList() {
        ArrayList<Course> list1 = fileInfoReader.getCourseInfo();
        ArrayList<Course> list2 = fileInfoReader.getCourseInfo();
        assertSame(list1, list2);
    }

    @Test
    public void testGetAdminInfo_ReturnsSameList() {
        ArrayList<Admin> list1 = fileInfoReader.getAdminInfo();
        ArrayList<Admin> list2 = fileInfoReader.getAdminInfo();
        assertSame(list1, list2);
    }

    @Test
    public void testGetProfInfo_ReturnsSameList() {
        ArrayList<Professor> list1 = fileInfoReader.getProfInfo();
        ArrayList<Professor> list2 = fileInfoReader.getProfInfo();
        assertSame(list1, list2);
    }

    @Test
    public void testGetStudentInfo_ReturnsSameList() {
        ArrayList<Student> list1 = fileInfoReader.getStudentInfo();
        ArrayList<Student> list2 = fileInfoReader.getStudentInfo();
        assertSame(list1, list2);
    }

}
