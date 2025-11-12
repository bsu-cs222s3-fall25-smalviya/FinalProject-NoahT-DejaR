package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.Map;
import java.util.stream.Collectors;

public class MatchingScene {

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        ListView<String> matchesList = new ListView<>();

        var matches = users.values().stream()
                .filter(u -> !u.getEmail().equals(currentUser.getEmail()))
                .filter(u -> u.getCourses().stream().anyMatch(c -> currentUser.getCourses().contains(c)))
                .map(User::getFirstName)
                .collect(Collectors.toList());

        matchesList.getItems().setAll(matches);

        VBox layout = new VBox(12, welcomeLabel, new Label("Matching Students:"), matchesList);
        layout.setPadding(new Insets(25));

        return new Scene(layout, 400, 400);
    }
}
