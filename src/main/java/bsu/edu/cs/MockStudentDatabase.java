package bsu.edu.cs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MockStudentDatabase {

    public static void main(String[] args) {
        String[] names = {"Alex", "Taylor", "Jordan", "Riley", "Casey", "Morgan", "Avery", "Jamie", "Sam", "Quinn"};
        String[] courses = {"CS120", "CS121", "CS222", "CS333", "MATH125", "MATH161", "ENG104", "HIST150", "BIO111", "CHEM101"};

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/mock_students.txt"))) {
            for (int i = 0; i < 50; i++) {
                String name = names[random.nextInt(names.length)];


                List<String> courseList = new ArrayList<>(Arrays.asList(courses));
                Collections.shuffle(courseList);
                List<String> selected = courseList.subList(0, 3);

                writer.write(name + ";" + String.join(",", selected));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
