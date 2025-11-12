package bsu.edu.cs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MockStudentDatabase {

    public static void main(String[] args) {
        String[] names = {"Alex", "Taylor", "Jordan", "Riley", "Casey", "Morgan", "Avery", "Jamie", "Sam", "Quinn"};
        String[] courses = {"CS120", "CS121", "CS222", "CS333", "MATH125", "MATH161", "ENG104", "HIST150", "BIO111", "CHEM101"};
        String[] times = {
                "8AM–10AM", "9AM–12PM", "10AM–1PM", "11AM–2PM",
                "12PM–3PM", "1PM–4PM", "2PM–5PM", "3PM–6PM"
        };

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/mock_students.txt"))) {
            for (int i = 0; i < 50; i++) {
                String name = names[random.nextInt(names.length)];

                char lastInitial = (char) ('a' + random.nextInt(26));
                int randomNum = random.nextInt(100);
                String email = name.toLowerCase() + lastInitial + randomNum + "@bsu.edu";

                List<String> courseList = new ArrayList<>(Arrays.asList(courses));
                Collections.shuffle(courseList);
                List<String> selectedCourses = courseList.subList(0, 3);

                String availableTime = times[random.nextInt(times.length)];

                writer.write(name + ";" + email + ";" + availableTime + ";" + String.join(",", selectedCourses));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

