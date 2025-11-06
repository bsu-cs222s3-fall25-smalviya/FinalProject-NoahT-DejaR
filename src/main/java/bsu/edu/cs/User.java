package bsu.edu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> courses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.courses = new ArrayList<>();
    }

    public User(String username, String password, List<String> courses) {
        this.username = username;
        this.password = password;
        this.courses = new ArrayList<>(courses);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void addCourse(String course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public static User fromString(String line) {
        String[] parts = line.split(";");
        String username = parts[0];
        String password = parts[1];
        List<String> courses = parts.length > 2 ? Arrays.asList(parts[2].split(",")) : new ArrayList<>();
        return new User(username, password, courses);
    }

    @Override
    public String toString() {
        return username + ";" + password + ";" + String.join(",", courses);
    }
}
