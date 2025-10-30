package bsu.edu.cs;

public class User {
    private String username;
    private String password;
    private String major;
    private String interests;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    @Override
    public String toString() {
        return username + "," + password + "," + (major == null ? "" : major) + "," + (interests == null ? "" : interests);
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        User user = new User(parts[0], parts[1]);
        if (parts.length > 2) user.setMajor(parts[2]);
        if (parts.length > 3) user.setInterests(parts[3]);
        return user;
    }
}
