package bsu.edu.cs;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class GUI {

    private final Map<String, User> users = new HashMap<>();
    private final File mockFile = new File("src/main/resources/mock_students.txt");

    public GUI() {
        loadUsers();
    }

    private void loadUsers() {
        if (!mockFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(mockFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromString(line);
                users.put(u.getUsername(), u);
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void start(Stage stage) {
        Scene loginScene = SceneManager.createLoginScene(users, stage);
        stage.setScene(loginScene);
        stage.setTitle("Cardinal Finder");
        stage.show();
    }
}
