package bsu.edu.cs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class TimeAvailabilityScene {

    private static final String[] DAYS = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
    private static final String[] DISPLAY_HOURS = {
            "8 AM","9 AM","10 AM","11 AM","12 PM",
            "1 PM","2 PM","3 PM","4 PM","5 PM","6 PM","7 PM","8 PM"
    };
    private static final int[] HOUR_VALUES = {8,9,10,11,12,13,14,15,16,17,18,19,20}; // for storing in users.txt

    public static Scene create(User user, Map<String, User> users, Stage stage) {
        return new TimeAvailabilityScene(user, users, stage).build();
    }

    private final User user;
    private final Map<String, User> users;
    private final Stage stage;
    private final ToggleButton[][] buttons;

    private TimeAvailabilityScene(User user, Map<String, User> users, Stage stage) {
        this.user = user;
        this.users = users;
        this.stage = stage;
        this.buttons = new ToggleButton[DAYS.length][HOUR_VALUES.length];
    }

    private Scene build() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        Label title = new Label("Select Your Weekly Availability");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-text-fill: #990000;");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        GridPane grid = new GridPane();
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setPadding(new Insets(12));

        // Days
        for (int c = 0; c < DAYS.length; c++) {
            Label dayLabel = new Label(DAYS[c]);
            dayLabel.setStyle("-fx-font-size:14px; -fx-font-weight:bold;");
            grid.add(dayLabel, c + 1, 0);
        }

        // Hours
        for (int r = 0; r < DISPLAY_HOURS.length; r++) {
            Label hourLabel = new Label(DISPLAY_HOURS[r]);
            hourLabel.setStyle("-fx-font-size:12px;");
            grid.add(hourLabel, 0, r + 1);

            for (int c = 0; c < DAYS.length; c++) {
                ToggleButton btn = new ToggleButton();
                btn.setPrefSize(100, 30);
                btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;"); // red = unavailable
                int cc = c;
                int rr = r;
                btn.setOnAction(e -> {
                    if (btn.isSelected()) {
                        btn.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;"); // green = available
                    } else {
                        btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    }
                });
                buttons[c][r] = btn;
                grid.add(btn, c + 1, r + 1);
            }
        }

        // Pre-fill user's existing schedule if available
        Map<String, List<String>> schedule = user.getStudySchedule();
        if (schedule != null) {
            for (int c = 0; c < DAYS.length; c++) {
                List<String> times = schedule.get(DAYS[c]);
                if (times != null) {
                    for (String t : times) {
                        try {
                            int hour = Integer.parseInt(t.replace("h", ""));
                            for (int r = 0; r < HOUR_VALUES.length; r++) {
                                if (HOUR_VALUES[r] == hour) {
                                    buttons[c][r].setSelected(true);
                                    buttons[c][r].setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");
                                    break;
                                }
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }

        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        root.setCenter(sp);

        HBox bottom = new HBox(12);
        bottom.setPadding(new Insets(10));
        bottom.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setOnAction(e -> stage.setScene(CourseSelectionScene.create(user, users, stage)));

        Button save = new Button("Save Availability");
        save.setOnAction(e -> {
            Map<String, List<String>> newSchedule = collectSchedule();
            user.setStudySchedule(newSchedule);
            users.put(user.getEmail(), user);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Saved");
            a.setHeaderText("Availability saved");
            a.setContentText("Your availability has been stored.");
            a.showAndWait();
            stage.setScene(MatchingScene.create(user, users, stage));
        });

        bottom.getChildren().addAll(back, save);
        root.setBottom(bottom);

        return new Scene(root, 1100, 700);
    }

    private Map<String, List<String>> collectSchedule() {
        Map<String, List<String>> schedule = new LinkedHashMap<>();
        for (int c = 0; c < DAYS.length; c++) {
            List<String> times = new ArrayList<>();
            for (int r = 0; r < HOUR_VALUES.length; r++) {
                if (buttons[c][r].isSelected()) {
                    times.add(HOUR_VALUES[r] + "h"); // store in correct format
                }
            }
            if (!times.isEmpty()) schedule.put(DAYS[c], times);
        }
        return schedule;
    }
}
