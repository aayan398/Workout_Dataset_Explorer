//launches gui on edt 

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatasetExplorerFrame frame = new DatasetExplorerFrame();
            frame.setVisible(true);
        });
    }
}
