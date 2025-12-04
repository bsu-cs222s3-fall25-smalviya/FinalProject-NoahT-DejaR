package bsu.edu.cs;

import java.util.*;

public class User {
    private String firstName;
    private String email;
    private String password;
    private List<String> courses;
    private Map<String, List<String>> studySchedule; // day → list of times

    public User(String firstName, String email, String password) {
        this(firstName, email, password, new ArrayList<>(), new HashMap<>());
    }

    public User(String firstName, String email, String password, List<String> courses) {
        this(firstName, email, password, courses, new HashMap<>());
    }

    public User(String firstName, String email, String password, List<String> courses,
                Map<String, List<String>> studySchedule) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.courses = courses;
        this.studySchedule = studySchedule;
    }

    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<String> getCourses() { return courses; }
    public Map<String, List<String>> getStudySchedule() { return studySchedule; }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public void addCourse(String course) {
        if (courses == null) courses = new ArrayList<>();
        if (!courses.contains(course)) courses.add(course);
    }

    public void setStudySchedule(Map<String, List<String>> schedule) {
        this.studySchedule = schedule;
    }

    @Override
    public String toString() {
        String courseStr = String.join(",", courses);

        // serialize schedule: day:time1,time2|day:time3,time4
        List<String> chunks = new ArrayList<>();
        for (var entry : studySchedule.entrySet()) {
            String day = normalizeDay(entry.getKey()); // ensures 3-letter abbreviation
            chunks.add(day + ":" + String.join(",", entry.getValue()));
        }
        String scheduleStr = String.join("|", chunks);

        return firstName + ";" + email + ";" + password + ";" + courseStr + ";" + scheduleStr;
    }

    public static User fromString(String data) {
        String[] parts = data.split(";");
        if (parts.length < 4) return null;

        List<String> userCourses =
                parts[3].isEmpty() ? new ArrayList<>() : Arrays.asList(parts[3].split(","));

        Map<String, List<String>> schedule = new LinkedHashMap<>();
        if (parts.length >= 5) {
            for (int i = 4; i < parts.length; i++) {
                String part = parts[i].trim();
                if (part.isEmpty()) continue;

                String[] daySplit = part.split(":");
                if (daySplit.length != 2) continue;

                String dayRaw = daySplit[0].trim();
                String day = normalizeDay(dayRaw);  // Convert "Monday" → "Mon", etc.
                String timesRaw = daySplit[1];

                List<String> times = new ArrayList<>();
                if (!timesRaw.equalsIgnoreCase("NONE")) {
                    String[] timeParts = timesRaw.split(",");
                    for (String t : timeParts) {
                        t = t.trim();
                        if (!t.isEmpty()) {
                            times.add(t);
                        }
                    }
                }
                schedule.put(day, times);
            }
        }

        return new User(parts[0], parts[1], parts[2], userCourses, schedule);
    }

    /** Converts full day names to 3-letter abbreviations, leaves abbreviations as-is */
    private static String normalizeDay(String day) {
        switch (day.toLowerCase()) {
            case "monday": return "Mon";
            case "tuesday": return "Tue";
            case "wednesday": return "Wed";
            case "thursday": return "Thu";
            case "friday": return "Fri";
            case "saturday": return "Sat";
            case "sunday": return "Sun";
            default: return day; // assume it's already an abbreviation
        }
    }
}
