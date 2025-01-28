package src;
import javax.swing.SwingUtilities;

import src.FrontEnd.Finance_Tracker;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new Finance_Tracker()
        );
    }
}
