package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;

public class EditProfileScene {

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        Button editCoursesBtn = new Button("Edit Courses");
        Button editTimesBtn = new Button("Edit Time Availability");
        Button backBtn = new Button("Back");

        editCoursesBtn.setMinWidth(200);
        editTimesBtn.setMinWidth(200);
        backBtn.setMinWidth(200);

        // Go to CourseSelectionScene in editing mode
        editCoursesBtn.setOnAction(e -> {
            stage.setScene(CourseSelectionScene.create(currentUser, users, stage, true));
        });

        // Go to TimeAvailabilityScene in editing mode
        editTimesBtn.setOnAction(e -> {
            stage.setScene(TimeAvailabilityScene.create(currentUser, users, stage, true));
        });

        // Go back to MatchingScene
        backBtn.setOnAction(e -> {
            stage.setScene(MatchingScene.create(currentUser, users, stage));
        });

        layout.getChildren().addAll(editCoursesBtn, editTimesBtn, backBtn);

        return new Scene(layout, 400, 250);
    }
}
