package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.HashMap;
import java.util.Map;

public class GUI {

    private Map<String, String> users = new HashMap<>();

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
        messageLabel.setStyle("-fx-text-fill: #444;");

        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please type both username and password.");
                return;
            }

            if (users.containsKey(user)) {
                if (users.get(user).equals(pass)) {
                    messageLabel.setText("Welcome back, " + user + "! :)");
                } else {
                    messageLabel.setText("Incorrect password.");
                }
            } else {
                messageLabel.setText("User not found! Please sign up first.");
            }
        });

        signupButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                messageLabel.setText("Please type both username and password.");
                return;
            }

            if (users.containsKey(user)) {
                messageLabel.setText("That username already exists. Sorry :(");
            } else {
                users.put(user, pass);
                messageLabel.setText("Account created for " + user + " :)");
                System.out.println("Registered users: " + users.keySet());
            }
        });

        HBox buttonBox = new HBox(10, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, buttonBox, messageLabel);

        Scene scene = new Scene(layout, 350, 260);
        stage.setScene(scene);
        stage.show();
    }
}
