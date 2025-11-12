package bsu.edu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String firstName;
    private String email;
    private String password;
    private List<String> courses;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email.toLowerCase();
        this.password = password;
        this.courses = new ArrayList<>();
    }

    public User(String firstName, String email, String password, List<String> courses) {
        this.firstName = firstName;
        this.email = email.toLowerCase();
        this.password = password;
        this.courses = new ArrayList<>(courses);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
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
        String firstName = parts[0];
        String email = parts[1].toLowerCase();
        String password = parts[2];
        List<String> courses = parts.length > 3 ? Arrays.asList(parts[3].split(",")) : new ArrayList<>();
        return new User(firstName, email, password, courses);
    }

    @Override
    public String toString() {
        return firstName + ";" + email + ";" + password + ";" + String.join(",", courses);
    }
}
