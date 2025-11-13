package bsu.edu.cs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseSelectionTest {

    @Test
    void testAddSingleCourse() {
        User user = new User("Noah", "noah.tutt@bsu.edu", "pass", new ArrayList<>());

        String selectedCourse = "CS222";
        user.addCourse(selectedCourse);

        List<String> courses = user.getCourses();
        assertEquals(1, courses.size());
        assertTrue(courses.contains("CS222"));
    }

    @Test
    void testAddMultipleCourses() {
        User user = new User("Deja", "deja.randolph@bsu.edu", "pass", new ArrayList<>());

        String[] selectedCourses = {"CS222", "MATH125", "ENG104"};
        for (String course : selectedCourses) {
            user.addCourse(course);
        }

        List<String> courses = user.getCourses();
        assertEquals(3, courses.size());
        assertTrue(courses.contains("CS222"));
        assertTrue(courses.contains("MATH125"));
        assertTrue(courses.contains("ENG104"));
    }

    @Test
    void testNoDuplicateCourses() {
        User user = new User("Noah", "noah.tutt@bsu.edu", "pass", new ArrayList<>());

        user.addCourse("CS222");
        user.addCourse("CS222");

        List<String> courses = user.getCourses();
        assertEquals(1, courses.size());
    }
}
