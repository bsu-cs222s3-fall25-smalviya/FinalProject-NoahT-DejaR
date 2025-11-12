package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;

public class SignupScene {

    public static Scene create(Map<String, User> users, Stage stage) {
        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField emailField = new TextField();
        emailField.setPromptText("BSU Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        Label messageLabel = new Label();
        Button nextButton = new Button("Next");

        VBox layout = new VBox(12, titleLabel, firstNameField, emailField, passwordField, confirmPasswordField, nextButton, messageLabel);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        nextButton.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String email = emailField.getText().trim().toLowerCase();
            String password = passwordField.getText().trim();
            String confirm = confirmPasswordField.getText().trim();

            if (firstName.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                messageLabel.setText("Please fill out all fields.");
                return;
            }

            if (!password.equals(confirm)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            if (users.containsKey(email)) {
                messageLabel.setText("Email already registered.");
                return;
            }

            User newUser = new User(firstName, email, password);
            users.put(email, newUser);

            stage.setScene(CourseSelectionScene.create(newUser, users, stage));
        });

        return new Scene(layout, 400, 350);
    }
}
