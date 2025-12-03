public class WorkoutRecord {

    private int sessionId;
    private String workout;
    private int durationMinutes;
    private int avgHeartRate;
    private int calories;
    private String intensity;
    private String day;

    //constructor
    public WorkoutRecord(int sessionId, String workout, int durationMinutes,
                         int avgHeartRate, int calories, String intensity, String day) {
        this.sessionId = sessionId;
        this.workout = workout;
        this.durationMinutes = durationMinutes;
        this.avgHeartRate = avgHeartRate;
        this.calories = calories;
        this.intensity = intensity;
        this.day = day;
    }

    //getters
    public int getSessionId() { return sessionId; }
    public String getWorkout() { return workout; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getAvgHeartRate() { return avgHeartRate; }
    public int getCalories() { return calories; }
    public String getIntensity() { return intensity; }
    public String getDay() { return day; }

    @Override
    public String toString() {
        //formats columns for record 
        return String.format("%-8d\t%-8s\t%-4d\t%-4d\t%-5d\t%-8s\t%-10s",
                sessionId, workout, durationMinutes, avgHeartRate,
                calories, intensity, day);
    }

    // Format for saving back into the data file
    public String toDataLine() {
        return sessionId + " " + workout + " " + durationMinutes + " "
                + avgHeartRate + " " + calories + " " + intensity + " " + day;
    }


    public boolean containsTerm(String term) {
        if (term == null || term.isEmpty()) return false;
        String t = term.toLowerCase();
        return String.valueOf(sessionId).toLowerCase().contains(t)
                || workout.toLowerCase().contains(t)
                || String.valueOf(durationMinutes).toLowerCase().contains(t)
                || String.valueOf(avgHeartRate).toLowerCase().contains(t)
                || String.valueOf(calories).toLowerCase().contains(t)
                || intensity.toLowerCase().contains(t)
                || day.toLowerCase().contains(t);
    }
}
