import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkoutDataset {

    private List<WorkoutRecord> records = new ArrayList<>();
    private File dataFile;

    // Load file
    public void loadFromFile(File file) throws IOException {
        records.clear();
        dataFile = file;

        Scanner scanner = new Scanner(new FileReader(file));

        // skip header line
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
            //go through each line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] p = line.split("\\s+");
            if (p.length < 7) continue;

            //creats new WR using data from line
            WorkoutRecord rec = new WorkoutRecord(
                    Integer.parseInt(p[0]),  // Session_ID
                    p[1],                    // Workout
                    Integer.parseInt(p[2]),  // Duration_min
                    Integer.parseInt(p[3]),  // Avg_HeartRate
                    Integer.parseInt(p[4]),  // Calories
                    p[5],                    // Intensity
                    p[6]                     // Day
            );
            //adds new rec to list 
            records.add(rec);
        }
        scanner.close();
    }

    public List<WorkoutRecord> getRecords() {
        // return a copy of the list (same objects)
        return new ArrayList<>(records);
    }

    // methods for the statistics

    public int getRowCount() {
        return records.size();
    }

    public double getAverageDuration() {
        if (records.isEmpty()) return 0.0;
        double sum = 0;
        for (WorkoutRecord r : records) sum += r.getDurationMinutes();
        return sum / records.size();
    }

    public double getAverageHeartRate() {
        if (records.isEmpty()) return 0.0;
        double sum = 0;
        for (WorkoutRecord r : records) sum += r.getAvgHeartRate();
        return sum / records.size();
    }

    public int getTotalCalories() {
        int total = 0;
        for (WorkoutRecord r : records) total += r.getCalories();
        return total;
    }

    public int getHighIntensityCount() {
        int count = 0;
        for (WorkoutRecord r : records) {
            if ("High".equalsIgnoreCase(r.getIntensity())) {
                count++;
            }
        }
        return count;
    }

    // adding new row

    public void addRecord(WorkoutRecord record) throws IOException {
        records.add(record);
        appendRecordToFile(record);
    }

    private void appendRecordToFile(WorkoutRecord record) throws IOException {
        if (dataFile == null) return;  // no dataset loaded yet
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dataFile, true)))) {
            out.println(record.toDataLine());
        }
    }

    //search term occurrenced

    public int countOccurrences(String term) {
        if (term == null || term.isEmpty()) return 0;
        int count = 0;
        for (WorkoutRecord r : records) {
            if (r.containsTerm(term)) {
                count++;
            }
        }
        return count;
    }
}
