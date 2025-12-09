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

    // New: editingMode determines whether to return to edit scene or next scene
    public static Scene create(User currentUser, Map<String, User> users, Stage stage, boolean editingMode) {
        Label titleLabel = new Label("Select Your Courses");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #990000;");

        ComboBox<String> courseDropdown = new ComboBox<>();
        courseDropdown.getItems().addAll(COURSE_LIST);
        courseDropdown.setPromptText("Select a course");

        ListView<String> selectedCoursesView = new ListView<>();
        selectedCoursesView.getItems().addAll(currentUser.getCourses());
        selectedCoursesView.setMaxHeight(150);

        List<String> selectedCourses = new ArrayList<>(currentUser.getCourses());

        Label messageLabel = new Label();

        // Add course button
        Button addCourseButton = new Button("Add Course");
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
            selectedCoursesView.getItems().add(selected);
        });

        // Remove course button
        Button removeCourseButton = new Button("Remove Course");
        removeCourseButton.setOnAction(e -> {
            String selected = selectedCoursesView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                messageLabel.setText("Please select a course to remove.");
                return;
            }
            selectedCourses.remove(selected);
            currentUser.getCourses().remove(selected);
            selectedCoursesView.getItems().remove(selected);
        });

        HBox selectionButtons = new HBox(10, courseDropdown, addCourseButton, removeCourseButton);
        selectionButtons.setAlignment(Pos.CENTER);

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            if (selectedCourses.isEmpty()) {
                messageLabel.setText("Please add at least one course.");
                return;
            }

            if (editingMode) {
                // Return to EditProfileScene
                stage.setScene(EditProfileScene.create(currentUser, users, stage));
            } else {
                stage.setScene(TimeAvailabilityScene.create(currentUser, users, stage));
            }
        });

        VBox layout = new VBox(15, titleLabel, selectionButtons, selectedCoursesView, messageLabel, nextButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 450, 450);
    }
}
