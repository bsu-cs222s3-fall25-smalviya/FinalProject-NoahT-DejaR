package bsu.edu.cs;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {

    private final Map<String, User> users = new HashMap<>();
    private final File userFile = new File("src/main/resources/users.txt");

    public GUI() {
        loadUsers();
    }

    /** Loads existing users from the file into the users map */
    private void loadUsers() {
        if (!userFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromString(line);
                if (u != null) users.put(u.getEmail(), u);
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    /** Saves all users to the file safely using a temporary file */
    public void saveUsers() {
        File tempFile = new File(userFile.getAbsolutePath() + ".tmp");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (User u : users.values()) {
                writer.write(u.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            return;
        }

        // Replace original file only if writing succeeded
        if (!tempFile.renameTo(userFile)) {
            System.out.println("Warning: Could not replace original users.txt. Check file permissions.");
        }
    }

    /** Adds a new user to the map, does NOT save immediately */
    public void addUser(User newUser) {
        users.put(newUser.getEmail(), newUser);
        // call saveUsers() manually when ready to write to disk
    }

    public void start(Stage stage) {
        Scene loginScene = LoginScene.create(users, stage);
        stage.setScene(loginScene);
        stage.setTitle("Cardinal Finder");
        stage.show();

        stage.setOnCloseRequest(e -> saveUsers()); // saves only on close
    }

    /** Get the users map for other classes to access */
    public Map<String, User> getUsers() {
        return users;
    }
}
