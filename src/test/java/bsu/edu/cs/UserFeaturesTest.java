package bsu.edu.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserFeaturesTest {

    private User testUser;
    private Map<String, User> users;

    @BeforeEach
    public void setUp() {
        testUser = new User(
                "John",
                "john.doe@bsu.edu",
                "pass",
                new ArrayList<>(),
                new HashMap<>()
        );

        users = new HashMap<>();
        users.put(testUser.getEmail(), testUser);
    }

    @Test
    public void testAddAndRemoveCourse() {
        testUser.addCourse("CS120");
        assertTrue(testUser.getCourses().contains("CS120"));

        testUser.getCourses().remove("CS120");
        assertFalse(testUser.getCourses().contains("CS120"));
    }

    @Test
    public void testAvailabilityToggle() {
        String key = "Monday-08:00";
        testUser.getAvailability().put(key, false);

        boolean current = testUser.getAvailability().get(key);
        testUser.getAvailability().put(key, !current);

        assertTrue(testUser.getAvailability().get(key));
    }

    @Test
    public void testLogoutResetsScene() {
        assertTrue(users.containsKey(testUser.getEmail()));

        users.remove(testUser.getEmail());

        assertFalse(users.containsKey(testUser.getEmail()));
    }

    @Test
    public void testSetMultipleAvailabilitySlots() {
        String slot1 = "Tuesday-10:00";
        String slot2 = "Tuesday-11:00";

        testUser.getAvailability().put(slot1, true);
        testUser.getAvailability().put(slot2, false);

        assertTrue(testUser.getAvailability().get(slot1));
        assertFalse(testUser.getAvailability().get(slot2));

        testUser.getAvailability().put(slot1, false);
        testUser.getAvailability().put(slot2, true);

        assertFalse(testUser.getAvailability().get(slot1));
        assertTrue(testUser.getAvailability().get(slot2));
    }
}
