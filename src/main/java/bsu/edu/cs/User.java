package bsu.edu.cs;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String firstName;
    private String email;
    private String password;
    private List<String> courses;

    public User(String firstName, String email, String password) {
        this(firstName, email, password, new ArrayList<>());
    }

    public User(String firstName, String email, String password, List<String> courses) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.courses = courses;
    }

    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<String> getCourses() { return courses; }
    public void setCourses(List<String> courses) { this.courses = courses; }

    public void addCourse(String course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    @Override
    public String toString() {
        return firstName + ";" + email + ";" + password + ";" + String.join(",", courses);
    }

    public static User fromString(String data) {
        String[] parts = data.split(";");
        if (parts.length < 4) return null;

        List<String> userCourses;
        if (parts[3].isEmpty()) {
            userCourses = new ArrayList<>();
        } else {
            userCourses = List.of(parts[3].split(","));
        }

        return new User(parts[0], parts[1], parts[2], new ArrayList<>(userCourses));
    }
}
