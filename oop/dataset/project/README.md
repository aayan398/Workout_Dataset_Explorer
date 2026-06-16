# Workout Dataset Explorer

A Java Swing desktop application for loading, viewing, searching, updating, and analysing workout dataset records.

## Features

- Load workout data from a text file
- Display records in a table-style view
- Add new workout records
- Search records by word or number
- Calculate summary statistics:
  - total number of rows
  - average duration
  - average heart rate
  - total calories
  - high-intensity session count

## Technologies Used

- Java
- Java Swing
- Object-Oriented Programming
- File I/O

## How to Run

1. Compile the Java files:
   `javac Main.java DatasetExplorerFrame.java WorkoutDataset.java WorkoutRecord.java`

2. Run the program:
   `java Main`

3. Click **Load Dataset** and select `sample_workouts.txt`.

## Project Structure

- `Main.java` - starts the application
- `DatasetExplorerFrame.java` - handles the GUI
- `WorkoutDataset.java` - loads, stores, searches, and analyses records
- `WorkoutRecord.java` - represents one workout record