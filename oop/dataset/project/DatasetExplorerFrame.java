//general gui and viewing and interact


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DatasetExplorerFrame extends JFrame {

    private WorkoutDataset dataset = new WorkoutDataset();

    private JTextArea dataArea;
    private JTextArea statsArea;

    private JTextField searchField;
    private JLabel searchResultLabel;
 
    public DatasetExplorerFrame() {
        super("Workout Dataset Explorer");
        initGui();
    }

    //building the ui
    private void initGui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // centre: data display
        dataArea = new JTextArea(); //record printed text form 
        dataArea.setEditable(false);//cant write 
        dataArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(dataArea), BorderLayout.CENTER);

        // right: statistics
        statsArea = new JTextArea(10, 25);
        statsArea.setEditable(false);
        statsArea.setBorder(BorderFactory.createTitledBorder("Summary Statistics"));
        add(statsArea, BorderLayout.EAST);

        // top: controls
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        // row 1: load + add row
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadButton = new JButton("Load Dataset"); // open file chooser 
        JButton addRowButton = new JButton("Add Row");//open dialog to add a new rec manually
        row1.add(loadButton);
        row1.add(addRowButton);
        topPanel.add(row1);

        // row 2: search
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Search term:"));
        searchField = new JTextField(12);//type term 
        JButton searchButton = new JButton("Search");//triggers search
        searchResultLabel = new JLabel("Occurrences: 0");//shows amnt rows have term 
        row2.add(searchField);
        row2.add(searchButton);
        row2.add(searchResultLabel);
        topPanel.add(row2);

        add(topPanel, BorderLayout.NORTH);

        // actions/event listeners
        loadButton.addActionListener(e -> onLoadDataset());
        addRowButton.addActionListener(e -> onAddRow());
        searchButton.addActionListener(e -> onSearch());
    }

    private void onLoadDataset() {
        //choosing files
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select dataset file (e.g. Aayan_Mohammad.txt)");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                dataset.loadFromFile(file);
                updateDataDisplay();
                updateStatsDisplay();
                JOptionPane.showMessageDialog(this,
                        "Dataset loaded. Rows: " + dataset.getRowCount(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error loading file: " + ex.getMessage(),
                        "Load error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateDataDisplay() {
        if (dataset.getRowCount() == 0) {
            dataArea.setText("No data loaded.");
            return;
        }

        //stringbuffer would slow down the project its for many threads 
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s\t%-8s\t%-4s\t%-4s\t%-5s\t%-8s\t%-10s%n",
                "Sess_ID", "Type", "Dur", "HR", "Cal", "Intens.", "Day"));
        sb.append("---------------------------------------------------------------------\n");
        for (WorkoutRecord r : dataset.getRecords()) {
            sb.append(r.toString()).append("\n");
        }
        dataArea.setText(sb.toString());
        dataArea.setCaretPosition(0);
    }

    private void updateStatsDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of rows: ").append(dataset.getRowCount()).append("\n");
        sb.append(String.format("Average duration (min): %.2f%n", dataset.getAverageDuration()));
        sb.append(String.format("Average heart rate: %.2f%n", dataset.getAverageHeartRate()));
        sb.append("Total calories: ").append(dataset.getTotalCalories()).append("\n");
        sb.append("High intensity sessions: ").append(dataset.getHighIntensityCount()).append("\n");
        statsArea.setText(sb.toString());
    }

    private void onAddRow() {
        if (dataset.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please load a dataset first.",
                    "No dataset",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField sessionIdField = new JTextField();
        JTextField workoutField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField hrField = new JTextField();
        JTextField calField = new JTextField();
        JTextField intensityField = new JTextField();
        JTextField dayField = new JTextField();

        panel.add(new JLabel("Session ID:"));
        panel.add(sessionIdField);
        panel.add(new JLabel("Workout:"));
        panel.add(workoutField);
        panel.add(new JLabel("Duration (min):"));
        panel.add(durationField);
        panel.add(new JLabel("Avg Heart Rate:"));
        panel.add(hrField);
        panel.add(new JLabel("Calories:"));
        panel.add(calField);
        panel.add(new JLabel("Intensity (Low/Medium/High):"));
        panel.add(intensityField);
        panel.add(new JLabel("Day:"));
        panel.add(dayField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Row", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String sId = sessionIdField.getText().trim();
                String w = workoutField.getText().trim();
                String durStr = durationField.getText().trim();
                String hrStr = hrField.getText().trim();
                String calStr = calField.getText().trim();
                String inten = intensityField.getText().trim();
                String day = dayField.getText().trim();

                if (sId.isEmpty() || w.isEmpty() || durStr.isEmpty()
                        || hrStr.isEmpty() || calStr.isEmpty()
                        || inten.isEmpty() || day.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "All fields must be filled.",
                            "Invalid input",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int sessionId = Integer.parseInt(sId);
                int dur = Integer.parseInt(durStr);
                int hr = Integer.parseInt(hrStr);
                int cal = Integer.parseInt(calStr);

                WorkoutRecord rec = new WorkoutRecord(sessionId, w, dur, hr, cal, inten, day);
                dataset.addRecord(rec);
                updateDataDisplay();
                updateStatsDisplay();
                JOptionPane.showMessageDialog(this,
                        "Row added successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Session ID, Duration, Heart Rate and Calories must be numbers.",
                        "Invalid number",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error writing to file: " + ex.getMessage(),
                        "File error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSearch() {
        if (dataset.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Please load a dataset first.",
                    "No dataset",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String term = searchField.getText().trim();
        if (term.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a search term.",
                    "No term",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int count = dataset.countOccurrences(term);
        searchResultLabel.setText("Occurrences: " + count);
    }
}
