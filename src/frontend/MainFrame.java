package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import backend.FinanceBackend;


public class MainFrame extends JFrame {
    private JPanel headerPanel;
    private JPanel contentPanel;
    private Font interRegular, robotoExtraBold, interExtraBold, smallerInterRegular;
    FinanceBackend fb; 

    public MainFrame() {
        fb = new FinanceBackend();

        loadFonts();

        setTitle("PennyWise");
        setSize(1536, 864);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializePanels();

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        showPanel("Home"); // Show this panel by default
    }

    private void loadFonts() {
        try {
            interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
            robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
            interExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-ExtraBold.ttf")).deriveFont(14f);
            smallerInterRegular = interRegular.deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            // System.out.println("Error loading fonts: " + e.getMessage());
            interRegular = new Font("Arial", Font.PLAIN, 14); // Fallback font
            smallerInterRegular = interRegular.deriveFont(12f);
        }
    }

    // okay
    private void initializePanels() {
        headerPanel = createHeaderPanel();
        contentPanel = new JPanel(new CardLayout());
        
        // Add content panels
        contentPanel.add(new HomePanel(fb), "Home");
        contentPanel.add(new SettingsPanel(fb, robotoExtraBold, interRegular, interExtraBold), "Settings");
        // contentPanel.add(new BodyPanel(), "Body");

        // sa body panel i2
        // contentPanel.add(new ExpensesFormPanel(fb, interRegular, robotoExtraBold), "ExpensePanel");

    }


    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(22, 70, 65));
        panel.setPreferredSize(new Dimension(1280, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        GridBagConstraints headerGbc = new GridBagConstraints();
        headerGbc.insets = new Insets(0, 10, 0, 10);
        headerGbc.fill = GridBagConstraints.BOTH;

        // Add logo section
        JPanel logoPanel = createLogoPanel();
        headerGbc.gridx = 0;
        headerGbc.weightx = 1;
        headerGbc.anchor = GridBagConstraints.WEST;
        panel.add(logoPanel, headerGbc);

        // Add navigation buttons
        JPanel buttonPanel = createButtonPanel();
        headerGbc.gridx = 1;
        headerGbc.weightx = 0;
        headerGbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, headerGbc);

        return panel;
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        logoPanel.setBackground(new Color(22, 70, 65));

        ImageIcon pennyIcon = new ImageIcon("src\\resources\\Images\\logo.png");
        Image scaledImage = pennyIcon.getImage().getScaledInstance(42, 35, Image.SCALE_REPLICATE);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel iconLabel = new JLabel(resizedIcon);
        JLabel logo = new JLabel("PENNYWISE");
        logo.setForeground(Color.WHITE);
        logo.setFont(interRegular.deriveFont(Font.BOLD, 16f));
        
        logoPanel.add(iconLabel);
        logoPanel.add(logo);

        return logoPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(22, 70, 65));

        JButton homeButton = createHeaderButton("HOME", true);
        JButton settingsButton = createHeaderButton("SETTINGS", false);

        homeButton.addActionListener(e -> showPanel("Home"));
        settingsButton.addActionListener(e -> showPanel("Settings"));

        buttonPanel.add(homeButton);
        buttonPanel.add(settingsButton);

        return buttonPanel;
    }

    private JButton createHeaderButton(String text, boolean isBold) {
        JButton button = new JButton(text);
        button.setBackground(new Color(22, 70, 65));
        button.setForeground(Color.WHITE);
        button.setFont(isBold ? interRegular.deriveFont(Font.BOLD) : interRegular);
        button.setBorderPainted(false);
        button.setFocusable(false);
        return button;
    }

    // kaya may name eh kasi dahil dito: contentPanel.add(createHomePanel(), "Home");
    private void showPanel(String panelName) {
        contentPanel.removeAll();
        switch (panelName) {
            case "Home" -> contentPanel.add(new HomePanel(fb), "Home");
            case "Settings" -> contentPanel.add(new SettingsPanel(fb, robotoExtraBold, interRegular, interExtraBold), "Settings");
        }
        contentPanel.revalidate();
        contentPanel.repaint();
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, panelName);
    }
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
