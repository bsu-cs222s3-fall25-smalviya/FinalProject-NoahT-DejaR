package bsu.edu.cs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class CreateAccountTest {

    @Test
    void testCreateAccount() {
        HashMap<String, String> users = new HashMap<>();
        String username = "Noah.Tutt";
        String password = "passw0rd";

        users.put(username, password);

        assertTrue(users.containsKey(username));
        assertEquals(password, users.get(username));
        assertNotEquals("wrongPassword", users.get(username));
    }

    @Test
    void testDuplicateUsername() {
        Map<String, String> users = new HashMap<>();
        String username = "Deja.Randolph";
        String password1 = "passw0rd";
        String password2 = "1234567890";

        users.put(username, password1);

        if (!users.containsKey(username)) {
            users.put(username, password2);
        }

        assertEquals(password1, users.get(username));
    }

    @Test
    void testLogin() {
        Map<String, String> users = new HashMap<>();
        String username = "Deja.Randolph";
        String password = "passw0rd";

        users.put(username, password);

        boolean loginSuccess = users.containsKey(username) && users.get(username).equals(password);

        assertTrue(loginSuccess);
    }

    @Test
    void testLoginFailure() {
        Map<String, String> users = new HashMap<>();
        String username = "Noah.Tutt";
        String password = "passw0rd";

        users.put(username, password);

        boolean loginFail = users.containsKey(username) && users.get(username).equals("wrongPassword");
        assertFalse(loginFail);

        boolean loginFail2 = users.containsKey("noUser") && users.get("noUser").equals(password);
        assertFalse(loginFail2);
    }
}

