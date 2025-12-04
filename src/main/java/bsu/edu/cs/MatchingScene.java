package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchingScene {

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {
        Label welcomeLabel = new Label("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        VBox contentBox = new VBox(12);
        contentBox.setPadding(new Insets(25));
        contentBox.getChildren().add(welcomeLabel);

        if (currentUser.getCourses().isEmpty()) {
            contentBox.getChildren().add(new Label("You have not selected any courses."));
        } else {
            for (String course : currentUser.getCourses()) {
                Label courseLabel = new Label(course);
                courseLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");
                contentBox.getChildren().add(courseLabel);

                // Filter users: same course AND overlapping availability
                List<User> matches = users.values().stream()
                        .filter(u -> !u.getEmail().equals(currentUser.getEmail()))
                        .filter(u -> u.getCourses().contains(course))
                        .filter(u -> hasOverlap(u.getStudySchedule(), currentUser.getStudySchedule()))
                        .collect(Collectors.toList());

                if (matches.isEmpty()) {
                    contentBox.getChildren().add(new Label("No matching students for this course and availability."));
                } else {
                    ListView<String> listView = new ListView<>();
                    listView.getItems().setAll(matches.stream().map(User::getFirstName).toList());
                    listView.setPrefHeight(Math.min(matches.size() * 24 + 2, 200));

                    listView.setOnMouseClicked(event -> {
                        String selectedName = listView.getSelectionModel().getSelectedItem();
                        if (selectedName != null) {
                            matches.stream()
                                    .filter(u -> u.getFirstName().equals(selectedName))
                                    .findFirst()
                                    .ifPresent(u -> {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Student Info");
                                        alert.setHeaderText(u.getFirstName());
                                        StringBuilder info = new StringBuilder();
                                        info.append("BSU Email: ").append(u.getEmail()).append("\n");
                                        info.append("Courses: ").append(String.join(", ", u.getCourses())).append("\n");
                                        info.append("Availability:\n");
                                        u.getStudySchedule().forEach((day, times) ->
                                                info.append("  ").append(day).append(": ").append(String.join(", ", times)).append("\n")
                                        );
                                        alert.setContentText(info.toString());
                                        alert.showAndWait();
                                    });
                        }
                    });

                    contentBox.getChildren().add(listView);
                }
            }
        }

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(450);
        scrollPane.setPrefHeight(500);

        return new Scene(scrollPane);
    }

    // Helper method: checks if two schedules have at least one overlapping day & hour
    private static boolean hasOverlap(Map<String, List<String>> schedule1, Map<String, List<String>> schedule2) {
        for (String day : schedule1.keySet()) {
            if (schedule2.containsKey(day)) {
                for (String hour : schedule1.get(day)) {
                    if (schedule2.get(day).contains(hour)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
