public class WorkoutRecord {

    private int sessionId;
    private String workout;
    private int durationMinutes;
    private int avgHeartRate;
    private int calories;
    private String intensity;
    private String day;

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

    public int getSessionId() { return sessionId; }
    public String getWorkout() { return workout; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getAvgHeartRate() { return avgHeartRate; }
    public int getCalories() { return calories; }
    public String getIntensity() { return intensity; }
    public String getDay() { return day; }

    @Override
    public String toString() {
        return sessionId + "\t" + workout + "\t" + durationMinutes + "\t"
                + avgHeartRate + "\t" + calories + "\t" + intensity + "\t" + day;
    }
}
