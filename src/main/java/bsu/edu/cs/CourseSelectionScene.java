package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseSelectionScene {

    private static final String[] COURSE_LIST = {
            "CS120", "CS121", "CS222", "CS333", "CS345", "CS446",
            "MATH125", "MATH161", "ENG104", "HIST150", "BIO111",
            "CHEM101", "PHYS215", "ECON201", "PSYCH100"
    };

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {
        Label titleLabel = new Label("Select Your Courses");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        ComboBox<String> courseDropdown = new ComboBox<>();
        courseDropdown.getItems().addAll(COURSE_LIST);
        courseDropdown.setPromptText("Select a course");

        VBox selectedCoursesBox = new VBox(5);
        selectedCoursesBox.setPadding(new Insets(10));
        selectedCoursesBox.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");

        List<String> selectedCourses = new ArrayList<>();

        Button addCourseButton = new Button("Add Course");
        Label messageLabel = new Label();

        addCourseButton.setOnAction(e -> {
            String selected = courseDropdown.getValue();
            if (selected == null || selected.isEmpty()) {
                messageLabel.setText("Please select a course.");
                return;
            }
            if (selectedCourses.contains(selected)) {
                messageLabel.setText("You already added this course.");
                return;
            }
            selectedCourses.add(selected);
            currentUser.addCourse(selected);

            Label courseLabel = new Label(selected);
            selectedCoursesBox.getChildren().add(courseLabel);

        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            if (selectedCourses.isEmpty()) {
                messageLabel.setText("Please add at least one course.");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Courses");
            alert.setHeaderText("Are you sure these are your courses?");
            alert.setContentText("You can edit them later if needed.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    stage.setScene(MatchingScene.create(currentUser, users, stage));
                }
            });
        });

        HBox selectionBox = new HBox(10, courseDropdown, addCourseButton);
        selectionBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titleLabel, selectionBox, selectedCoursesBox, messageLabel, nextButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 400, 450);
    }
}
