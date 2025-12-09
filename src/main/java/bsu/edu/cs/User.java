package bsu.edu.cs;

import java.util.*;

public class User {
    private String firstName;
    private String email;
    private String password;
    private List<String> courses;
    private Map<String, Boolean> availability;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email.toLowerCase();
        this.password = password;
        this.courses = new ArrayList<>();
        this.availability = new HashMap<>();
    }

    public User(String firstName, String email, String password, List<String> courses, Map<String, Boolean> availability) {
        this.firstName = firstName;
        this.email = email.toLowerCase();
        this.password = password;
        this.courses = new ArrayList<>(courses);
        this.availability = availability == null ? new HashMap<>() : new HashMap<>(availability);
    }

    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<String> getCourses() { return courses; }
    public Map<String, Boolean> getAvailability() { return availability; }

    public void addCourse(String course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void setAvailability(Map<String, Boolean> availability) {
        this.availability = availability;
    }

    // Load from CSV
    public static User fromString(String line) {
        String[] parts = line.split(";");
        String firstName = parts[0];
        String email = parts[1].toLowerCase();
        String password = parts[2];

        List<String> courses = new ArrayList<>();
        if (parts.length > 3 && !parts[3].isEmpty()) {
            courses = Arrays.asList(parts[3].split(","));
        }

        Map<String, Boolean> availability = new HashMap<>();
        if (parts.length > 4 && !parts[4].isEmpty()) {
            String[] entries = parts[4].split(",");
            for (String entry : entries) {
                String[] keyVal = entry.split("=");
                if (keyVal.length == 2) {
                    availability.put(keyVal[0], Boolean.parseBoolean(keyVal[1]));
                }
            }
        }

        return new User(firstName, email, password, courses, availability);
    }

    @Override
    public String toString() {
        String coursesStr = String.join(",", courses);

        String availabilityStr = "";
        if (availability != null && !availability.isEmpty()) {
            availabilityStr = String.join(",",
                    availability.entrySet().stream()
                            .map(e -> e.getKey() + "=" + e.getValue())
                            .toList()
            );
        }

        return firstName + ";" + email + ";" + password + ";" + coursesStr + ";" + availabilityStr;
    }
}
