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

    public void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false))) {
            for (User u : users.values()) {
                writer.write(u.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void addUser(User newUser) {
        users.put(newUser.getEmail(), newUser);
        saveUsers();
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void start(Stage stage) {
        Scene loginScene = LoginScene.create(users, stage, this);
        stage.setScene(loginScene);
        stage.setTitle("Cardinal Finder");
        stage.show();

        stage.setOnCloseRequest(e -> saveUsers());
    }
}
