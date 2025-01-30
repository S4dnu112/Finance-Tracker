package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.function.Supplier;

public class PanelManager {
    private static PanelManager instance;
    private final JPanel contentPanel;
    private final CardLayout cardLayout;
    private final HashMap<String, Supplier<JPanel>> panelFactories;
    private final HashMap<String, JPanel> activePanels;

    private PanelManager() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        panelFactories = new HashMap<>();
        activePanels = new HashMap<>();
    }

    public static PanelManager getInstance() {
        if (instance == null) {
            instance = new PanelManager();
        }
        return instance;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    /**
     * Registers a panel with a factory method to allow dynamic re-creation.
     */
    public void registerPanel(String panelName, Supplier<JPanel> panelFactory) {
        panelFactories.put(panelName, panelFactory);
    }

    /**
     * Shows the panel, ensuring it is refreshed on every switch.
     */
    public void showPanel(String panelName) {
        if (!panelFactories.containsKey(panelName)) {
            System.out.println("Error: Panel '" + panelName + "' not registered.");
            return;
        }

        // Remove the old panel if it exists
        if (activePanels.containsKey(panelName)) {
            contentPanel.remove(activePanels.get(panelName));
        }

        // Create a fresh panel instance
        JPanel newPanel = panelFactories.get(panelName).get();
        activePanels.put(panelName, newPanel);
        contentPanel.add(newPanel, panelName);

        // Show the panel
        cardLayout.show(contentPanel, panelName);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
