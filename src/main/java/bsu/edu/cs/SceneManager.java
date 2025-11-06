package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;
import java.util.stream.Collectors;

public class SceneManager {

    private static final String[] COURSE_LIST = {
            "CS120", "CS121", "CS222", "CS333", "CS345", "CS446",
            "MATH125", "MATH161", "ENG104", "HIST150", "BIO111",
            "CHEM101", "PHYS215", "ECON201", "PSYCH100"
    };

    public static Scene createLoginScene(Map<String, User> users, Stage stage) {
        Label titleLabel = new Label("Cardinal Finder");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign Up");

        HBox buttonBox = new HBox(10, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, titleLabel, usernameField, passwordField, buttonBox, messageLabel);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (users.containsKey(user) && users.get(user).getPassword().equals(pass)) {
                messageLabel.setText("Welcome, " + user + "!");
                stage.setScene(createMainScene(users.get(user), users, stage));
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        signupButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please fill out both fields.");
                return;
            }

            if (users.containsKey(user)) {
                messageLabel.setText("Username already exists.");
            } else {
                User newUser = new User(user, pass);
                users.put(user, newUser);
                messageLabel.setText("Account created for " + user + "!");
            }
        });

        return new Scene(layout, 350, 260);
    }

    public static Scene createMainScene(User currentUser, Map<String, User> users, Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ComboBox<String> courseDropdown = new ComboBox<>();
        courseDropdown.getItems().addAll(COURSE_LIST);
        courseDropdown.setPromptText("Select a course");

        Button addCourseButton = new Button("Add Course");
        Label messageLabel = new Label();
        ListView<String> matchesList = new ListView<>();

        addCourseButton.setOnAction(e -> {
            String selectedCourse = courseDropdown.getValue();
            if (selectedCourse == null || selectedCourse.isEmpty()) {
                messageLabel.setText("Please select a course.");
                return;
            }

            currentUser.addCourse(selectedCourse);
            messageLabel.setText("Added " + selectedCourse + " to your list.");

            var matches = users.values().stream()
                    .filter(u -> !u.getUsername().equals(currentUser.getUsername()))
                    .filter(u -> u.getCourses().stream().anyMatch(c -> currentUser.getCourses().contains(c)))
                    .map(User::getUsername)
                    .collect(Collectors.toList());

            matchesList.getItems().setAll(matches);
            messageLabel.setText(matches.isEmpty()
                    ? "No matches found yet."
                    : "You share courses with " + matches.size() + " student(s).");
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> stage.setScene(createLoginScene(users, stage)));

        HBox courseBox = new HBox(10, courseDropdown, addCourseButton);
        courseBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, welcomeLabel, courseBox, messageLabel, new Label("Matching Students:"), matchesList, logoutButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 400, 400);
    }
}
