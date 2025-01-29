package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class TransferMoney extends JPanel {
    private Font interRegular;
    private Font robotoExtraBold;
    private TransferMoneyPanel transferMoneyPanel;
    private TransferMoney2Panel transferMoney2Panel;

    public TransferMoney() {
        loadFonts();
        setupUI();
    }

    private void loadFonts() {
        try {
            interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
            robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
        } catch (FontFormatException | IOException e) {
            interRegular = new Font("Arial", Font.PLAIN, 14);
            robotoExtraBold = new Font("Arial", Font.BOLD, 40);
        }
    }

    public void switchPanels(JPanel panelCurrent, JPanel panelNew){
        remove(panelCurrent);
        panelCurrent = panelNew;
        add(panelCurrent);
        revalidate();
        repaint();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Create navigation bar
        add(createNavBar(), BorderLayout.NORTH);
        
        // Create the two panels
        transferMoneyPanel = new TransferMoneyPanel();
        transferMoney2Panel = new TransferMoney2Panel();
        
        add(transferMoneyPanel, BorderLayout.CENTER);
    }

    private JPanel createNavBar() {
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(22, 66, 60));
        navBar.setPreferredSize(new Dimension(1280, 50));
        navBar.setLayout(null);

        // PENNYWISE Label
        ImageIcon icon = new ImageIcon("src\\resources\\Images\\Logo.png");
        Image scaledIcon = icon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        JLabel pennywiseLabel = new JLabel("PENNYWISE", new ImageIcon(scaledIcon), JLabel.LEFT);
        pennywiseLabel.setFont(interRegular);
        pennywiseLabel.setForeground(Color.WHITE);
        pennywiseLabel.setBounds(50, 10, 200, 25);
        navBar.add(pennywiseLabel);

        // Navigation buttons
        JButton homeButton = createNavButton("HOME", 1000, 10);
        JButton settingsButton = createNavButton("SETTINGS", 1080, 10);
        navBar.add(homeButton);
        navBar.add(settingsButton);

        return navBar;
    }

    private JButton createNavButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(interRegular);
        button.setBounds(x, y, text.equals("HOME") ? 100 : 150, 25);
        button.setBackground(new Color(22, 66, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    // First Panel
    private class TransferMoneyPanel extends JPanel {
        private JTextField amountField;

        public TransferMoneyPanel() {
            setupPanel();
        }

        private void setupPanel() {
            setLayout(new BorderLayout());
            
            // Create split pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                createMainPanel(), createRightPanel());
            splitPane.setDividerSize(0);
            splitPane.setResizeWeight(0.5);
            
            add(splitPane);
        }

        private JPanel createMainPanel() {
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Title
            JLabel title = new JLabel("Transfer Money", SwingConstants.CENTER);
            title.setFont(robotoExtraBold);
            title.setForeground(new Color(22, 66, 60));
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainPanel.add(title, gbc);

            // Form fields
            String[] labels = {"Amount:", "Source:", "Destination:", "Description:", 
                             "Transaction Fee:", "Recurrence:"};
            gbc.gridwidth = 1;
            int row = 1;

            for (String label : labels) {
                addFormField(mainPanel, label, gbc, row++);
            }

            // Date fields
            addDateFields(mainPanel, gbc, row);

            // Next Button
            JButton nextButton = createNextButton();
            gbc.gridy = row + 3;
            gbc.gridx = 1;
            mainPanel.add(nextButton, gbc);

            return mainPanel;
        }

        private void addFormField(JPanel panel, String labelText, 
                                GridBagConstraints gbc, int row) {
            JLabel label = new JLabel(labelText);
            label.setFont(interRegular);
            gbc.gridx = 0;
            gbc.gridy = row;
            panel.add(label, gbc);

            Component field;
            if (labelText.equals("Source:") || labelText.equals("Destination:")) {
                field = createStyledComboBox(new String[]{"Select Account", "Bank", 
                    "Cash", "Digital Wallet", "Credit Card"});
            } else if (labelText.equals("Recurrence:")) {
                field = createStyledComboBox(new String[]{"Select Recurrence", "N/A", 
                    "Daily", "Weekly", "Monthly"});
            } else if (labelText.equals("Amount:")) {
                amountField = createStyledTextField();
                field = amountField;
            } else {
                field = createStyledTextField();
            }

            gbc.gridx = 1;
            panel.add(field, gbc);
        }

        private JTextField createStyledTextField() {
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(250, 40));
            field.setFont(interRegular);
            field.setBackground(new Color(196, 218, 210));
            field.setBorder(new EmptyBorder(0, 20, 0, 0));
            return field;
        }

        private JComboBox<String> createStyledComboBox(String[] items) {
            JComboBox<String> comboBox = new JComboBox<>(items);
            comboBox.setFont(interRegular);
            comboBox.setBackground(new Color(196, 218, 210));
            comboBox.setPreferredSize(new Dimension(250, 40));
            comboBox.setBorder(BorderFactory.createEmptyBorder());
            comboBox.setUI(new BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton arrowButton = super.createArrowButton();
                    arrowButton.setBorder(BorderFactory.createEmptyBorder());
                    return arrowButton;
                }
            });
            return comboBox;
        }

        private void addDateFields(JPanel panel, GridBagConstraints gbc, int row) {
            String[] dateLabels = {"Start-date:", "End-date:"};
            for (String label : dateLabels) {
                JLabel dateLabel = new JLabel(label);
                dateLabel.setFont(interRegular);
                gbc.gridx = 0;
                gbc.gridy = row++;
                panel.add(dateLabel, gbc);

                JTextField dateField = createStyledTextField();
                gbc.gridx = 1;
                panel.add(dateField, gbc);
            }
        }

        private JButton createNextButton() {
            JButton nextButton = new JButton("NEXT \u25B6");
            nextButton.setFont(new Font("Inter", Font.BOLD, 16));
            nextButton.setBackground(null);
            nextButton.setForeground(Color.BLACK);
            nextButton.setFocusPainted(false);
            nextButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            nextButton.addActionListener(e -> {
                transferMoney2Panel.updateAmount(amountField.getText());
                switchPanels(transferMoneyPanel, transferMoney2Panel);
            });
            return nextButton;
        }

        private JPanel createRightPanel() {
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(106, 156, 137));
            rightPanel.setPreferredSize(new Dimension(640, 670));
            return rightPanel;
        }
    }

    // Second Panel
    private class TransferMoney2Panel extends JPanel {
        private JLabel amountValue;
        private JLabel totalValue;

        public TransferMoney2Panel() {
            setupPanel();
        }

        private void setupPanel() {
            setLayout(new BorderLayout());
            
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                createMainPanel(), createRightPanel());
            splitPane.setDividerSize(0);
            splitPane.setResizeWeight(0.5);
            
            add(splitPane);
        }

        private JPanel createMainPanel() {
            JPanel mainPanel = new JPanel(new GridBagLayout());
            mainPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Transfer details
            JLabel transferLabel = new JLabel("YOU ARE ABOUT TO TRANSFER");
            transferLabel.setFont(interRegular);
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainPanel.add(transferLabel, gbc);

            // Amount
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            mainPanel.add(new JLabel("Amount:"), gbc);

            amountValue = new JLabel("PHP 0.00");
            gbc.gridx = 1;
            mainPanel.add(amountValue, gbc);

            // Separator
            JPanel separatorPanel = new JPanel(new BorderLayout());
            JSeparator separator = new JSeparator();
            separator.setPreferredSize(new Dimension(250,3));
            separator.setForeground(Color.GRAY);
            separatorPanel.add(separator, BorderLayout.CENTER);

            gbc.fill = GridBagConstraints.BOTH;
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 2;
            mainPanel.add(separatorPanel, gbc);

            // Total
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            mainPanel.add(new JLabel("Total"), gbc);

            totalValue = new JLabel("PHP 0.00");
            totalValue.setFont(robotoExtraBold.deriveFont(20f));
            gbc.gridx = 1;
            mainPanel.add(totalValue, gbc);

            // Confirmation message
            JLabel confirmMsg = new JLabel("<html>Please ensure your account has enough " +
                "balance<br> to cover the Transfer amount.</html>");
            confirmMsg.setFont(interRegular);
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 4;
            mainPanel.add(confirmMsg, gbc);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(createConfirmButton());
            buttonPanel.add(createBackButton());

            gbc.gridy = 5;
            mainPanel.add(buttonPanel, gbc);

            return mainPanel;
        }

        private JButton createConfirmButton() {
            JButton confirmButton = new JButton("CONFIRM");
            confirmButton.setFont(interRegular);
            confirmButton.setPreferredSize(new Dimension(200, 50));
            confirmButton.setBackground(new Color(106, 156, 137));
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFocusPainted(false);
            confirmButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Transfer Successful!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            });
            return confirmButton;
        }

        private JButton createBackButton() {
            JButton backButton = new JButton("\u25C0 BACK");
            backButton.setFont(new Font("Inter", Font.BOLD, 16));
            backButton.setBackground(Color.WHITE);
            backButton.setForeground(Color.BLACK);
            backButton.setBorderPainted(false);
            backButton.addActionListener(e -> {
                switchPanels(transferMoney2Panel, transferMoneyPanel);
            });
            return backButton;
        }

        private JPanel createRightPanel() {
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(106, 156, 137));
            rightPanel.setPreferredSize(new Dimension(640, 670));
            return rightPanel;
        }

        public void updateAmount(String amount) {
            String formattedAmount = "PHP " + amount;
            amountValue.setText(formattedAmount);
            totalValue.setText(formattedAmount);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transfer Money");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TransferMoney());
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
