package frontend;

import javax.swing.*;
import backend.FinanceBackend;
import transactionModels.Income;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class IncomeFormPanel extends JPanel {
    private Font interRegular;
    private Font robotoExtraBold;
    private Font smallerInterRegular;

    // Global Fields for Form Elements
    private JTextField amountField;
    private JComboBox<String> accountField;
    private JComboBox<String> recurrenceField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea descriptionField;

    private String[] accounts = {"Select Account", "Bank", "Cash", "Digital Wallets", "Credit Card"};
    private String[] recurrences = {"Select Recurrence", "daily", "weekly", "monthly", "yearly"};

    FinanceBackend fb = new FinanceBackend();
    public IncomeFormPanel() {
        try {
            interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("frontend\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
            robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("frontend\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
            smallerInterRegular = interRegular.deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            interRegular = new Font("Arial", Font.PLAIN, 14); // Fallback font
            smallerInterRegular = interRegular.deriveFont(12f);
        }

        setupPanel();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 500));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createFormPanel(), createSummaryPanel());
        splitPane.setDividerSize(0);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Add Income", SwingConstants.CENTER);
        title.setFont(robotoExtraBold);
        title.setForeground(new Color(22, 70, 65));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(title, gbc);

        // Amount Field
        gbc.gridwidth = 1;
        amountField = createStyledTextField();
        addFormField(formPanel, "Amount: ", amountField, gbc, 1);

        // Account Dropdown
        accountField = createStyledComboBox(accounts);
        addFormField(formPanel, "Account:", accountField, gbc, 2);

        // Recurrence Dropdown
        recurrenceField = createStyledComboBox(recurrences);
        addFormField(formPanel, "Recurrence:", recurrenceField, gbc, 3);

        // Date Fields
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(createDatePanel(), gbc);

        // Description
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(interRegular);
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        descriptionField = createDescriptionArea();
        formPanel.add(descriptionField, gbc);

        // Save Button
        JButton saveButton = createSaveButton();
        gbc.gridy = 6;
        formPanel.add(saveButton, gbc);

        return formPanel;
    }

    private JPanel createSummaryPanel() {
        JPanel summaryWrapperPanel = new JPanel(new BorderLayout());
        summaryWrapperPanel.setBackground(Color.WHITE);
        summaryWrapperPanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 100));
        summaryWrapperPanel.setPreferredSize(new Dimension(900, 600));

        JPanel summaryPanel = fb.createBarGraphPanel();

        summaryWrapperPanel.add(summaryPanel, BorderLayout.CENTER);
        return summaryWrapperPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(210, 35));
        field.setFont(interRegular);
        field.setBackground(new Color(196, 218, 210));
        field.setForeground(new Color(22, 70, 65));
        field.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(interRegular.deriveFont(Font.ITALIC, 14f));
        comboBox.setPreferredSize(new Dimension(210, 35));
        comboBox.setBackground(new Color(196, 218, 210));
        comboBox.setForeground(new Color(22, 70, 65));
        comboBox.setBorder(BorderFactory.createEmptyBorder());
        return comboBox;
    }

    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel(new GridBagLayout());
        datePanel.setBackground(Color.WHITE);
        GridBagConstraints dateGbc = new GridBagConstraints();
        dateGbc.insets = new Insets(10, 10, 10, 10);
        dateGbc.fill = GridBagConstraints.HORIZONTAL;

        addDateField(datePanel, "Start-date:", dateGbc, 0, true);
        addDateField(datePanel, "End-date:", dateGbc, 2, false);

        return datePanel;
    }

    private void addDateField(JPanel panel, String label, GridBagConstraints gbc, int x, boolean isStartDate) {
        JLabel dateLabel = new JLabel(label);
        dateLabel.setFont(interRegular);
        gbc.gridx = x;
        gbc.gridy = 0;
        panel.add(dateLabel, gbc);

        JTextField dateField = new JTextField("yyyy-mm-dd");
        dateField.setFont(smallerInterRegular);
        dateField.setPreferredSize(new Dimension(100, 35));
        dateField.setBackground(new Color(196, 218, 210));
        dateField.setForeground(new Color(150, 150, 150));
        dateField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        dateField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (dateField.getText().equals("yyyy-mm-dd")) {
                    dateField.setText("");
                    dateField.setForeground(new Color(22, 70, 65));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (dateField.getText().isEmpty()) {
                    dateField.setText("yyyy-mm-dd");
                    dateField.setForeground(new Color(150, 150, 150));
                }
            }
        });

        if (isStartDate) {
            startDateField = dateField;
        } else {
            endDateField = dateField;
        }

        gbc.gridx = x + 1;
        panel.add(dateField, gbc);
    }

    private JTextArea createDescriptionArea() {
        JTextArea descriptionArea = new JTextArea(3, 20);
        descriptionArea.setFont(interRegular);
        descriptionArea.setBackground(new Color(196, 218, 210));
        descriptionArea.setForeground(new Color(22, 70, 65));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return descriptionArea;
    }

    private JButton createSaveButton() {
        JButton saveButton = new JButton("SAVE");
        saveButton.setFont(interRegular);
        saveButton.setBackground(new Color(22, 70, 65));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusable(false);
        saveButton.setPreferredSize(new Dimension(saveButton.getPreferredSize().width + 20, 50));
        saveButton.addActionListener(e -> saveIncome());
        return saveButton;
    }

    private void addFormField(JPanel panel, String labelText, Component field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(interRegular);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void saveIncome() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String account = accountField.getSelectedItem().toString();
            String recurrence = recurrenceField.getSelectedItem().toString();
            String startDate = startDateField.getText().trim().isEmpty() || startDateField.getText().equals("yyyy-mm-dd") ? "N/A" : startDateField.getText();
            String endDate = endDateField.getText().trim().isEmpty() || endDateField.getText().equals("yyyy-mm-dd") ? "N/A" : endDateField.getText();
            String description = descriptionField.getText().trim().isEmpty() ? "N/A" : descriptionField.getText();

            if (recurrence.equals("Select Recurrence")) {
                recurrence = "N/A";
                startDate = "N/A";
                endDate = "N/A";
            }
            
            fb.save(new Income(amount, account, LocalDate.now().toString(), recurrence, startDate, endDate, description));

            JOptionPane.showMessageDialog(this, "Income saved successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving income: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Income Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new IncomeFormPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}