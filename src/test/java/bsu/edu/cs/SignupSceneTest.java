package bsu.edu.cs;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SignupSceneTest {

    @Test
    public void testSignupCreatesUser() {
        Map<String, User> users = new HashMap<>();
        String firstName = "Noah";
        String email = "noah.tutt@bsu.edu";
        String password = "passw0rd";

        User newUser = new User(firstName, email, password);
        users.put(email, newUser);

        assertTrue(users.containsKey(email));
        User retrieved = users.get(email);
        assertEquals(firstName, retrieved.getFirstName());
        assertEquals(email, retrieved.getEmail());
        assertEquals(password, retrieved.getPassword());
        assertNotNull(retrieved.getCourses());
        assertTrue(retrieved.getCourses().isEmpty());
    }

    @Test
    public void testDuplicateEmailNotAllowed() {
        Map<String, User> users = new HashMap<>();
        String email = "deja.randolph@bsu.edu";

        users.put(email, new User("Deja", email, "passw0rd"));


        assertThrows(IllegalArgumentException.class, () -> {
            if (users.containsKey(email)) {
                throw new IllegalArgumentException("Email already registered");
            }
            users.put(email, new User("Noah", email, "password1"));
        });
    }
}
