package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;

public class LoginScene {

    public static Scene create(Map<String, User> users, Stage stage) {
        Label titleLabel = new Label("Cardinal Finder");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        TextField emailField = new TextField();
        emailField.setPromptText("Student Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();
        Button loginButton = new Button("Login");
        Button signupButton = new Button("Sign Up");

        HBox buttonBox = new HBox(10, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12, titleLabel, emailField, passwordField, buttonBox, messageLabel);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String pass = passwordField.getText().trim();

            User user = users.get(email.toLowerCase());
            if (user != null && user.getPassword().equals(pass)) {
                messageLabel.setText("Welcome, " + user.getFirstName() + "!");
                stage.setScene(CourseSelectionScene.create(user, users, stage));
            } else {
                messageLabel.setText("Invalid email or password.");
            }
        });

        signupButton.setOnAction(e -> stage.setScene(SignupScene.create(users, stage)));

        return new Scene(layout, 350, 260);
    }
}
