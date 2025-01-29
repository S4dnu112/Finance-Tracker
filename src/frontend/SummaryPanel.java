package frontend;

import backend.FinanceBackend;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SummaryPanel extends JPanel {
    private Font robotoExtraBold;
    private Font interRegular;
    private Font interExtraBold;
    private FinanceBackend fb = new FinanceBackend();
    
    public SummaryPanel() {
        try {
            initializeFonts();
            setupPanel();
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initializeFonts() throws FontFormatException, IOException {
        robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
        interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
        interExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-ExtraBold.ttf")).deriveFont(14f);
    }
    
    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel contentWrapperPanel = new JPanel(new BorderLayout());
        contentWrapperPanel.setBackground(Color.WHITE);
        contentWrapperPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        contentWrapperPanel.add(createContentPanel(), BorderLayout.CENTER);
        
        add(contentWrapperPanel, BorderLayout.CENTER);
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased vertical spacing
        gbc.fill = GridBagConstraints.BOTH;

        // Add Summary title
        addSummaryTitle(contentPanel, gbc);

        // Add Stats section
        addStatsSection(contentPanel, gbc);

        // Add Breakdown section
        addBreakdownSection(contentPanel, gbc);

        return contentPanel;
    }
    
    private void addSummaryTitle(JPanel panel, GridBagConstraints gbc) {
        JLabel summaryLabel = new JLabel("Summary", SwingConstants.CENTER);
        summaryLabel.setFont(robotoExtraBold.deriveFont(60f));
        summaryLabel.setForeground(new Color(22, 70, 65));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(-50, 10, 5, 10); // Added bottom padding
        panel.add(summaryLabel, gbc);
    }
    
    private void addStatsSection(JPanel panel, GridBagConstraints gbc) {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        Color panelColor = new Color(196, 218, 210);

        Double remaining = fb.getAccountBalances().get("Cash") + fb.getAccountBalances().get("Bank") + fb.getAccountBalances().get("Digital Wallets");
        
        statsPanel.add(createStatsPanel("Total Income", fb.getTotalIncome(), panelColor, "src\\resources\\Images\\income.png"));
        statsPanel.add(createStatsPanel("Total Expenses", fb.getTotalExpense(), panelColor,"src\\resources\\Images\\expense.png"));
        statsPanel.add(createStatsPanel("Remaining Balance", remaining, panelColor, "src\\resources\\Images\\balance.png"));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 10, 15, 10); // Increased bottom padding
        panel.add(statsPanel, gbc);
    }
    
    private void addBreakdownSection(JPanel panel, GridBagConstraints gbc) {
        JPanel breakdownPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        breakdownPanel.setBackground(Color.WHITE);
        breakdownPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        Color panelColor = new Color(196, 218, 210);
    
        breakdownPanel.add(createBreakdownPanel("Income Breakdown", panelColor));
        breakdownPanel.add(createBreakdownPanel("Expenses Breakdown", panelColor));
    
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.7; // Reduced weight to make graphs smaller
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(breakdownPanel, gbc);
    }
    
    private JPanel createBreakdownPanel(String title, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createLineBorder(new Color(22, 70, 65), 2));
    
        // Create main content panel with size constraints
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(bgColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Set preferred size for the content panel
        contentPanel.setPreferredSize(new Dimension(0, 300)); // Fixed height for graphs
    
        // Add appropriate graph panel based on title
        JPanel graphPanel;
        if (title.equals("Income Breakdown")) {
            graphPanel = fb.createBarGraphPanel();
        } else {
            graphPanel = fb.createPieChartPanel();
        }
        
        contentPanel.add(graphPanel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);
    
        return panel;
    }
    
    private JPanel createStatsPanel(String title, Double value, Color bgColor, String iconPath) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        panel.setPreferredSize(new Dimension(300, 50));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10);
    
        Color textColor = new Color(0x16423C);
    
        // Add icon wrapper
        JPanel iconWrapper = createIconWrapper(iconPath);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(iconWrapper, gbc);
    
        // Add text panel
        JPanel textPanel = createStatsTextPanel(title, value, bgColor, textColor);
        gbc.gridx = 1;
        panel.add(textPanel, gbc);
    
        return panel;
    }
    
    private JPanel createIconWrapper(String iconPath) {
        JPanel iconWrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                int diameter = Math.min(getWidth(), getHeight());
                g2d.fillOval((getWidth() - diameter) / 2, (getHeight() - diameter) / 2, diameter, diameter);
            }
        };
        iconWrapper.setPreferredSize(new Dimension(60, 60));
        iconWrapper.setOpaque(false);
        iconWrapper.setLayout(new GridBagLayout());
    
        JLabel iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
        iconWrapper.add(iconLabel);
    
        return iconWrapper;
    }
    
    private JPanel createStatsTextPanel(String title, Double value, Color bgColor, Color textColor) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBackground(bgColor);
    
        GridBagConstraints textGbc = new GridBagConstraints();
        textGbc.gridx = 0;
        textGbc.anchor = GridBagConstraints.WEST;
    
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(interExtraBold.deriveFont(22f));
        titleLabel.setForeground(textColor);
    
        JTextField valueField = new JTextField("\u20B1 " + value);
        valueField.setFont(interExtraBold.deriveFont(24f));
        valueField.setForeground(textColor);
        valueField.setBackground(bgColor);
        valueField.setBorder(null);
        valueField.setEditable(false);
        valueField.setColumns(8);
    
        textGbc.gridy = 0;
        textPanel.add(titleLabel, textGbc);
    
        textGbc.gridy = 1;
        textPanel.add(valueField, textGbc);
    
        return textPanel;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Summary Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(new SummaryPanel());
        frame.setVisible(true);
    }
}
