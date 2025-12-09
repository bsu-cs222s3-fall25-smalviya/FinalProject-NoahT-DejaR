package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchingScene {

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {

        Label titleLabel = new Label("Welcome, " + currentUser.getFirstName() + "! Let's find you a study buddy!");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #990000;");
        titleLabel.setWrapText(true);

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.TOP_CENTER);

        for (String course : currentUser.getCourses()) {

            Label courseLabel = new Label(course);
            courseLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #990000;");

            List<User> matchedUsers = users.values().stream()
                    .filter(u -> !u.getEmail().equals(currentUser.getEmail()))
                    .filter(u -> u.getCourses().contains(course))
                    .filter(u -> {
                        // Check if at least one shared availability
                        return u.getAvailability().keySet().stream()
                                .anyMatch(slot -> currentUser.getAvailability().getOrDefault(slot, false)
                                        && u.getAvailability().getOrDefault(slot, false));
                    })
                    .collect(Collectors.toList());

            ListView<String> listView = new ListView<>();
            listView.setMaxHeight(120);

            listView.getItems().setAll(
                    matchedUsers.stream()
                            .map(User::getFirstName)
                            .collect(Collectors.toList())
            );

            listView.setOnMouseClicked(e -> {
                String selectedName = listView.getSelectionModel().getSelectedItem();
                if (selectedName == null) return;

                User selectedUser = matchedUsers.stream()
                        .filter(u -> u.getFirstName().equals(selectedName))
                        .findFirst()
                        .orElse(null);

                if (selectedUser != null) {
                    // Show shared availability along with email
                    List<String> sharedSlots = selectedUser.getAvailability().keySet().stream()
                            .filter(slot -> currentUser.getAvailability().getOrDefault(slot, false)
                                    && selectedUser.getAvailability().getOrDefault(slot, false))
                            .collect(Collectors.toList());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Student Contact Info");
                    alert.setHeaderText(selectedUser.getFirstName());
                    alert.setContentText("Email: " + selectedUser.getEmail()
                            + "\nShared Availability: " + String.join(", ", sharedSlots));
                    alert.showAndWait();
                }
            });

            VBox section = new VBox(5, courseLabel, listView);
            section.setPadding(new Insets(10));
            section.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");
            section.setMaxWidth(300);

            mainBox.getChildren().add(section);
        }

        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);

        // Buttons
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setMinWidth(150);
        editProfileButton.setOnAction(e -> stage.setScene(EditProfileScene.create(currentUser, users, stage)));

        Button logoutButton = new Button("Logout");
        logoutButton.setMinWidth(150);
        logoutButton.setOnAction(e -> stage.setScene(LoginScene.create(users, stage)));

        HBox buttonBox = new HBox(20, editProfileButton, logoutButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox wrapper = new VBox(15, titleLabel, scrollPane, buttonBox);
        wrapper.setPadding(new Insets(20));
        wrapper.setAlignment(Pos.TOP_CENTER);

        return new Scene(wrapper, 450, 500);
    }
}
