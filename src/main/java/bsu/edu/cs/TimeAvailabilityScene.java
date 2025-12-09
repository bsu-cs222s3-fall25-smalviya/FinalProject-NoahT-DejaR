package bsu.edu.cs;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.Map;

public class TimeAvailabilityScene {

    private static final String[] DAYS = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 20;

    public static Scene create(User currentUser, Map<String, User> users, Stage stage, boolean editing) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int d = 0; d < DAYS.length; d++) {
            Label dayLabel = new Label(DAYS[d]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            grid.add(dayLabel, 0, d + 1);
        }

        for (int h = START_HOUR; h <= END_HOUR; h++) {
            String rangeLabel = formatRange(h);
            Label hourLabel = new Label(rangeLabel);
            hourLabel.setStyle("-fx-font-weight: bold;");
            grid.add(hourLabel, h - START_HOUR + 1, 0);
        }

        for (int d = 0; d < DAYS.length; d++) {
            for (int h = START_HOUR; h <= END_HOUR; h++) {
                String key = DAYS[d] + "-" + String.format("%02d:00", h);
                boolean isAvailable = currentUser.getAvailability().getOrDefault(key, false);

                Button slot = new Button();
                slot.setMinSize(50, 30);
                slot.setStyle("-fx-background-color: " + (isAvailable ? "green;" : "red;"));

                slot.setOnAction(e -> {
                    boolean newVal = !currentUser.getAvailability().getOrDefault(key, false);
                    currentUser.getAvailability().put(key, newVal);
                    slot.setStyle("-fx-background-color: " + (newVal ? "green;" : "red;"));
                });

                grid.add(slot, h - START_HOUR + 1, d + 1);
            }
        }

        Button nextButton = new Button(editing ? "Save and Return" : "Next");
        nextButton.setOnAction(e -> {
            if (editing) {
                stage.setScene(EditProfileScene.create(currentUser, users, stage));
            } else {
                stage.setScene(MatchingScene.create(currentUser, users, stage));
            }
        });

        VBox layout = new VBox(15, grid, nextButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 1000, 500);
    }

    public static Scene create(User currentUser, Map<String, User> users, Stage stage) {
        return create(currentUser, users, stage, false);
    }

    private static String formatRange(int hour) {
        return convert(hour) + " - " + convert(hour + 1);
    }

    private static String convert(int hour) {
        int h = hour % 12;
        if (h == 0) h = 12;

        String suffix = (hour < 12 || hour == 24) ? " AM" : " PM";
        return h + suffix;
    }
}
