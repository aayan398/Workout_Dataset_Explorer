package oop.dataset.project;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkoutDataset {

    private List<WorkoutRecord> records = new ArrayList<>();
    private File dataFile;

    public void loadFromFile(File file) throws IOException {
        records.clear();
        dataFile = file;

        Scanner scanner = new Scanner(new FileReader(file));

       
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] p = line.split("\\s+");
            if (p.length < 7) continue;

            WorkoutRecord rec = new WorkoutRecord(
                    Integer.parseInt(p[0]),  // Session_ID
                    p[1],                    // Workout
                    Integer.parseInt(p[2]),  // Duration_min
                    Integer.parseInt(p[3]),  // Avg_HeartRate
                    Integer.parseInt(p[4]),  // Calories
                    p[5],                    // Intensity
                    p[6]                     // Day
            );
            records.add(rec);
        }
        scanner.close();
    }

    public List<WorkoutRecord> getRecords() {
        return records;
    }
}
