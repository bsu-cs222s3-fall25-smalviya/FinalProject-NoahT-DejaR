package bsu.edu.cs;

import java.io.*;
import java.util.Map;

public class UserFileHelper {

    private static final File userFile = new File("src/main/resources/users.txt");

    public static void saveUsersToFile(Map<String, User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User u : users.values()) {
                writer.write(u.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsersFromFile() {
        Map<String, User> users = new java.util.HashMap<>();
        if (!userFile.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromString(line);
                users.put(u.getEmail(), u);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }
}
