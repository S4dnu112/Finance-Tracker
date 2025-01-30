package frontend;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import backend.FinanceBackend;
import transactionModels.Transfer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class TransferPanel extends JPanel {
    private Font interRegular;
    private Font robotoExtraBold;
    private Font smallerInterRegular;
    private TransferMoneyPanel transferMoneyPanel;
    private TransferMoney2Panel transferMoney2Panel;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private Transfer transferData;

    FinanceBackend fb = new FinanceBackend();

    String[] accountsCombo = {"Select Account", "Bank", "Cash", "Digital Wallets", "Credit Card"};
    String[] recurrenceCombo = {"Select Recurrence", "Daily", "Weekly", "Monthly"};
    private boolean isVerified = false;

    public TransferPanel() {
        loadFonts();
        setupUI();
    }

    private void loadFonts() {
        try {
            interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
            robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
            smallerInterRegular = interRegular.deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            interRegular = new Font("Arial", Font.PLAIN, 14);
            robotoExtraBold = new Font("Arial", Font.BOLD, 40);
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Create main content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // Create the two panels
        transferMoneyPanel = new TransferMoneyPanel();
        transferMoney2Panel = new TransferMoney2Panel();
        
        // Add panels to card layout
        contentPanel.add(transferMoneyPanel, "TransferMoney");
        contentPanel.add(transferMoney2Panel, "TransferMoney2");
        
        add(contentPanel, BorderLayout.CENTER);
    }

    // First Panel
    private class TransferMoneyPanel extends JPanel {
        private JTextField amountField;
        private JComboBox<String> sourceField;
        private JComboBox<String> destinationField;
        private JComboBox<String> recurrenceField;
        private JTextField transactionFeeField;
        private JTextField startDateField;
        private JTextField endDateField;
        private JTextArea descriptionField;

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

            gbc.gridwidth = 1;
            amountField = createStyledTextField();
            addFormField(mainPanel, "Amount: ", amountField, gbc, 2);
    
            sourceField = createStyledComboBox(accountsCombo);
            addFormField(mainPanel, "Source:", sourceField, gbc, 3);
    
            destinationField = createStyledComboBox(accountsCombo);
            addFormField(mainPanel, "Destination:", destinationField, gbc, 4);

            transactionFeeField = createStyledTextField();
            addFormField(mainPanel, "Transaction Fee:", transactionFeeField, gbc, 5);

            recurrenceField = createStyledComboBox(recurrenceCombo);
            recurrenceField.setSelectedIndex(0);
            recurrenceField.addActionListener(e -> {
                disableDateFields();
            });
            addFormField(mainPanel, "Recurrence:", recurrenceField, gbc, 6);

            // Date fields
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 2;
            mainPanel.add(createDatePanel(), gbc);
            disableDateFields();

            // Description
            gbc.gridy = 8;
            gbc.gridwidth = 1;
            JLabel descriptionLabel = new JLabel("Description:");
            descriptionLabel.setFont(interRegular);
            mainPanel.add(descriptionLabel, gbc);
            gbc.gridx = 1;
            descriptionField = createDescriptionArea();
            mainPanel.add(descriptionField, gbc);


            // Next Button
            JButton nextButton = createNextButton();
            gbc.gridy = 9;
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            mainPanel.add(nextButton, gbc);

            return mainPanel;
        }
        private void disableDateFields() {
            if(recurrenceField.getSelectedIndex() == 0) {
                endDateField.setEditable(false);
                startDateField.setEditable(false);
            } else {
                endDateField.setEditable(true);
                startDateField.setEditable(true);
            }
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

        private JButton createNextButton() {
            JButton nextButton = new JButton("NEXT \u25B6");
            nextButton.setFont(new Font("Inter", Font.PLAIN, 15));
            nextButton.setBackground(null);
            nextButton.setForeground(Color.BLACK);
            nextButton.setFocusPainted(false);
            nextButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            nextButton.addActionListener(e -> {
                verifyTransfer();
                if (isVerified) {
                    double totalAmount = transferData.getBaseAmount() + transferData.getTransactionFee(); 
                    transferMoney2Panel.updateAmount(""+totalAmount);
                    cardLayout.show(contentPanel, "TransferMoney2");
                }
            });
            return nextButton;
        }

        private void verifyTransfer() {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String source = sourceField.getSelectedItem().toString();
                String destination = destinationField.getSelectedItem().toString();
                double transactionFee = transactionFeeField.getText().trim().isEmpty() ? 0 : Double.parseDouble(transactionFeeField.getText());
                String recurrence = recurrenceField.getSelectedItem().toString();
                String startDate = startDateField.getText().trim().isEmpty() || startDateField.getText().equals("yyyy-mm-dd") ? "N/A" : startDateField.getText();
                String endDate = endDateField.getText().trim().isEmpty()  || endDateField.getText().equals("yyyy-mm-dd") ? "N/A" : endDateField.getText();
                String description = descriptionField.getText().trim().isEmpty() ? "N/A" : descriptionField.getText();
    
                if (recurrence.equals("Select Recurrence")) {
                    recurrence = "N/A";
                    startDate = "N/A";
                    endDate = "N/A";
                }
                transferData = new Transfer(amount, source, destination, LocalDate.now().toString(), recurrence, startDate, endDate, transactionFee, description);
                isVerified = true;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input for amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Please select an account", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving transfer data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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
                fb.save(transferData);
                JOptionPane.showMessageDialog(this, "Transfer Successful!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                PanelManager.getInstance().showPanel("Home");
            });
            return confirmButton;
        }

        private JButton createBackButton() {
            JButton backButton = new JButton("\u25C0 BACK");
            backButton.setFont(new Font("Inter", Font.PLAIN, 15));
            backButton.setBackground(Color.WHITE);
            backButton.setForeground(Color.BLACK);
            backButton.setBorderPainted(false);
            backButton.addActionListener(e -> 
                cardLayout.show(contentPanel, "TransferMoney"));
                isVerified = false;
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
}