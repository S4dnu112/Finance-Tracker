import javax.swing.SwingUtilities;
import frontend.MainFrame;

public class Finance_Tracker {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}