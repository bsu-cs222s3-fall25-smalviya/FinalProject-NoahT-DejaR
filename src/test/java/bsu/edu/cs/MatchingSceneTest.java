package bsu.edu.cs;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MatchingSceneTest {

    @Test
    void testFindMatches() {
        User alice = new User("Alice", "alice.sharp@bsu.edu", "pass", Arrays.asList("CS120", "MATH125"));
        User bob = new User("Bob", "bob.livingston@bsu.edu", "pass", Arrays.asList("CS120", "ENG104"));
        User charlie = new User("Charlie", "charlie.goodman@bsu.edu", "pass", Arrays.asList("HIST150", "PSYCH100"));
        User dave = new User("Dave", "dave.buster@bsu.edu", "pass", Collections.emptyList());

        Map<String, User> users = new HashMap<>();
        users.put(alice.getEmail(), alice);
        users.put(bob.getEmail(), bob);
        users.put(charlie.getEmail(), charlie);
        users.put(dave.getEmail(), dave);

        List<String> aliceMatches = MatchingScene.findMatches(alice, users);

        assertEquals(1, aliceMatches.size());
        assertTrue(aliceMatches.contains("Bob"));

        List<String> charlieMatches = MatchingScene.findMatches(charlie, users);
        assertTrue(charlieMatches.isEmpty(), "Charlie has no matches");

        List<String> daveMatches = MatchingScene.findMatches(dave, users);
        assertTrue(daveMatches.isEmpty(), "Dave has no courses, so no matches");
    }
}
