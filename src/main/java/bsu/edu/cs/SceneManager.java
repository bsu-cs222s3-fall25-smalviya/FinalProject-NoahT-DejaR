package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class SceneManager {

    // Creates the main scene after login
    public static Scene createMainScene(String username, Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label instructions = new Label("This is the main Cardinal Finder page.\nHere you can start matching with other students.");
        instructions.setWrapText(true);

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            // Go back to login scene
            GUI gui = new GUI();
            gui.start(stage);
        });

        VBox layout = new VBox(15, welcomeLabel, instructions, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));

        return new Scene(layout, 400, 250);
    }
}
