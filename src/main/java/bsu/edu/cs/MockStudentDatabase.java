package bsu.edu.cs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MockStudentDatabase {

    private static final String[] COURSE_LIST = {
            "CS120", "CS121", "CS222", "CS333", "CS345", "CS446",
            "MATH125", "MATH161", "ENG104", "HIST150", "BIO111",
            "CHEM101", "PHYS215", "ECON201", "PSYCH100"
    };

    private static final String[] FIRST_NAMES = {
            "Alex", "Jordan", "Taylor", "Morgan", "Casey", "Riley", "Jamie",
            "Drew", "Cameron", "Bailey", "Avery", "Parker", "Quinn", "Dakota",
            "Skyler", "Reese", "Harper", "Logan", "Emerson", "Rowan"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia",
            "Miller", "Davis", "Martinez", "Hernandez", "Lopez", "Gonzalez",
            "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"
    };

    public static void main(String[] args) {
        generateMockDatabase(5000);
    }

    public static void generateMockDatabase(int numberOfStudents) {
        File file = new File("src/main/resources/mock_students.txt");
        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < numberOfStudents; i++) {
                String first = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String last = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
                String username = (first.charAt(0) + last + i).toLowerCase();
                String password = "pass" + (1000 + random.nextInt(9000));

                // Assign 3 random courses to each student
                StringBuilder courses = new StringBuilder();
                for (int j = 0; j < 3; j++) {
                    courses.append(COURSE_LIST[random.nextInt(COURSE_LIST.length)]);
                    if (j < 2) courses.append(",");
                }

                writer.write(username + ";" + password + ";" + courses);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
