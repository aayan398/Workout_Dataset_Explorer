
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DatasetExplorerFrame extends JFrame {

    private WorkoutDataset dataset = new WorkoutDataset();
    private JTextArea dataArea = new JTextArea();

    public DatasetExplorerFrame() {
        super("Dataset Explorer (Half Done)");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dataArea.setEditable(false);
        dataArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(dataArea), BorderLayout.CENTER);

        JButton loadBtn = new JButton("Load Dataset");
        loadBtn.addActionListener(e -> loadFile());
        add(loadBtn, BorderLayout.NORTH);
    }

    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                dataset.loadFromFile(file);
                displayData();
                JOptionPane.showMessageDialog(
                        this,
                        "Loaded " + dataset.getRecords().size() + " rows.",
                        "Load complete",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage(),
                        "Load error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void displayData() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sess_ID\tType\tDur\tHR\tCal\tInt\tDay\n");
        sb.append("---------------------------------------------\n");
        for (WorkoutRecord r : dataset.getRecords()) {
            sb.append(r.toString()).append("\n");
        }
        dataArea.setText(sb.toString());
        dataArea.setCaretPosition(0);
    }
}
