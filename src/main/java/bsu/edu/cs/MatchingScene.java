package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.List;
import java.util.Map;

public class MatchingScene {

    public static List<String> findMatches(User currentUser, Map<String, User> users) {
        return users.values().stream()
                .filter(u -> !u.getEmail().equals(currentUser.getEmail()))
                .filter(u -> !currentUser.getCourses().isEmpty() &&
                        u.getCourses().stream().anyMatch(c -> currentUser.getCourses().contains(c)))
                .map(User::getFirstName)
                .toList();
    }

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        ListView<String> matchesList = new ListView<>();

        List<String> matches = findMatches(currentUser, users);

        if (matches.isEmpty()) {
            matchesList.getItems().add("No matching students found yet.");
        } else {
            matchesList.getItems().setAll(matches);
        }

        VBox layout = new VBox(12, welcomeLabel, new Label("Matching Students:"), matchesList);
        layout.setPadding(new Insets(25));

        return new Scene(layout, 450, 400);
    }
}
