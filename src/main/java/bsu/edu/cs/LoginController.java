package bsu.edu.cs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    protected void onLogin() {
        System.out.println("Login clicked: " + usernameField.getText());
    }

    @FXML
    protected void onSignUp() {
        System.out.println("Sign Up clicked: " + usernameField.getText());
    }
}
