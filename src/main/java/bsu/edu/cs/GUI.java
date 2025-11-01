package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GUI {

    private Map<String, User> users = new HashMap<>();
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
                users.put(u.getUsername(), u);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (User u : users.values()) {
                writer.write(u.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public void start(Stage stage) {
        stage.setTitle("Cardinal Finder");

        Label titleLabel = new Label("Cardinal Finder");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign Up");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please type both username and password.");
                return;
            }

            if (users.containsKey(user) && users.get(user).getPassword().equals(pass)) {
                stage.setScene(SceneManager.createMainScene(users.get(user).getUsername(), stage));
            } else {
                messageLabel.setText("Incorrect username or password.");
            }
        });

        // Sign-up button logic
        signupButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please type both username and password.");
                return;
            }

            if (users.containsKey(user)) {
                messageLabel.setText("That username already exists.");
            } else {
                User newUser = new User(user, pass);
                users.put(user, newUser);
                saveUsers();
                messageLabel.setText("Account created for " + user + "!");
            }
        });

        HBox buttonBox = new HBox(10, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, titleLabel, usernameField, passwordField, buttonBox, messageLabel);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350, 260);
        stage.setScene(scene);
        stage.show();
    }
}
