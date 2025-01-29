package frontend;

import java.awt.*;
import javax.swing.*;

public class PennywiseDashboard extends JFrame {

    // Declare text fields as instance variables for individual access
    private JTextField bankTextField;
    private JTextField cashTextField;
    private JTextField digitalWalletTextField;
    private JTextField creditCardTextField;

    public PennywiseDashboard() {

        // Frame setup
        setTitle("");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
       
        Color mainBackgroundColor = Color.WHITE;

        // Top Navigation Bar
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(22, 66, 60));
        navBar.setBounds(0, 0, 1280, 50);
        navBar.setLayout(null);

        // PENNYWISE Label
        JLabel pennywiseLabel = new JLabel("PENNYWISE");
        pennywiseLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        pennywiseLabel.setForeground(Color.WHITE);
        pennywiseLabel.setBounds(50, 10, 150, 25);
        navBar.add(pennywiseLabel);

        // Home and Settings Buttons
        JButton homeButton = createNavButton("HOME");
        homeButton.setBounds(1000, 10, 100, 25);
        navBar.add(homeButton);
        homeButton.setContentAreaFilled(false); // Make the button background transparent

        JButton settingsButton = createNavButton("SETTINGS");
        settingsButton.setBounds(1080, 10, 150, 25);
        navBar.add(settingsButton);
        settingsButton.setContentAreaFilled(false); // Make the button background transparent

        add(navBar);

        // "Hello," Label and Editable TextField
        JLabel helloLabel = new JLabel("Hello,");
        helloLabel.setFont(new Font("Roboto", Font.BOLD, 36)); // Bold font and increased size
        helloLabel.setForeground(Color.BLACK);
        helloLabel.setBounds(60, 80, 100, 50); // Adjusted position
        add(helloLabel);

        JTextField nameTextField = new JTextField();
        nameTextField.setFont(new Font("Roboto", Font.BOLD, 36)); // Bold font and increased size
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setBounds(160, 80, 350, 50); // Adjusted position and size
        nameTextField.setEditable(true);
        nameTextField.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Same border color as background
        add(nameTextField);

        // Left Panel: Account Balances
        JPanel accountBalancesPanel = new JPanel();
        accountBalancesPanel.setBackground(new Color(22, 66, 60)); // Matches border color of balance panels
        accountBalancesPanel.setBounds(50, 150, 250, 500); // Adjusted size
        accountBalancesPanel.setLayout(new GridLayout(5, 1, 5, 5)); // Added one more row for the button

        // Account Balances Label
        JButton accountBalancesButton = new JButton("ACCOUNT BALANCES");
        accountBalancesButton.setFont(new Font("Roboto", Font.BOLD, 16)); // Bold font and all caps
        accountBalancesButton.setForeground(Color.WHITE);
        accountBalancesButton.setBackground(new Color(22, 66, 60));
        accountBalancesButton.setBorder(BorderFactory.createEmptyBorder());
        accountBalancesButton.setFocusPainted(false);
        accountBalancesButton.setOpaque(false);
        accountBalancesButton.setContentAreaFilled(false); // Make the button background transparent
        accountBalancesButton.setHorizontalAlignment(SwingConstants.CENTER);
        accountBalancesButton.setVerticalAlignment(SwingConstants.CENTER);

        // Add the button instead of the label
        accountBalancesPanel.add(accountBalancesButton);

        // Bank Balance Panel
        bankTextField = new JTextField("₱0.00");
        accountBalancesPanel.add(createBalancePanel("BANK", bankTextField));

        // Cash Balance Panel
        cashTextField = new JTextField("₱0.00");
        accountBalancesPanel.add(createBalancePanel("CASH", cashTextField));

        // Digital Wallet Balance Panel
        digitalWalletTextField = new JTextField("₱0.00");
        accountBalancesPanel.add(createBalancePanel("DIGITAL WALLET", digitalWalletTextField));

        // Credit Card Balance Panel
        creditCardTextField = new JTextField("₱0.00");
        accountBalancesPanel.add(createBalancePanel("CREDIT CARD", creditCardTextField));

        add(accountBalancesPanel);

     // Buttons Panel (Add Income, Add Expense, Transfer Money)
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 35, 0)); // Adjusted spacing
        buttonsPanel.setBounds(350, 150, 850, 200); // Reduced height of the panel
        buttonsPanel.setOpaque(false);

        // Add Income Button
        JButton addIncomeButton = createDashboardButton("ADD INCOME");
        addIncomeButton.setFont(new Font("Roboto", Font.BOLD, 16));
        addIncomeButton.setContentAreaFilled(false); // Make the button background transparent
        addIncomeButton.setBorderPainted(false);    // Remove the border
        addIncomeButton.setOpaque(false);  
        ImageIcon incomeIcon = new ImageIcon("add_income-removebg-preview.png");
        Image incomeImage = incomeIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust width and height
        incomeIcon = new ImageIcon(incomeImage); // Apply the scaled image
        addIncomeButton.setIcon(incomeIcon);
        addIncomeButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        addIncomeButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Position the text below the icon
        addIncomeButton.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon and text horizontally
        addIncomeButton.setVerticalAlignment(SwingConstants.CENTER); // Center the icon and text vertically

        // Add Expense Button
        JButton addExpenseButton = createDashboardButton("ADD EXPENSE");
        addExpenseButton.setFont(new Font("Roboto", Font.BOLD, 16));
        addExpenseButton.setContentAreaFilled(false); // Make the button background transparent
        addExpenseButton.setBorderPainted(false);    // Remove the border
        addExpenseButton.setOpaque(false);  
        ImageIcon expenseIcon = new ImageIcon("add_expense-removebg-preview.png");
        Image expenseImage = expenseIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust width and height
        expenseIcon = new ImageIcon(expenseImage); // Apply the scaled image
        addExpenseButton.setIcon(expenseIcon);
        addExpenseButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        addExpenseButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Position the text below the icon
        addExpenseButton.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon and text horizontally
        addExpenseButton.setVerticalAlignment(SwingConstants.CENTER); // Center the icon and text vertically

        // Transfer Money Button
        JButton transferMoneyButton = createDashboardButton("TRANSFER MONEY");
        transferMoneyButton.setFont(new Font("Roboto", Font.BOLD, 16));
        transferMoneyButton.setContentAreaFilled(false); // Make the button background transparent
        transferMoneyButton.setBorderPainted(false);    // Remove the border
        transferMoneyButton.setOpaque(false);  
        ImageIcon transferIcon = new ImageIcon("transfer.png");
        Image transferImage = transferIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust width and height
        transferIcon = new ImageIcon(transferImage); // Apply the scaled image
        transferMoneyButton.setIcon(transferIcon);
        transferMoneyButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        transferMoneyButton.setVerticalTextPosition(SwingConstants.BOTTOM); // Position the text below the icon
        transferMoneyButton.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon and text horizontally
        transferMoneyButton.setVerticalAlignment(SwingConstants.CENTER); // Center the icon and text vertically

        // Add buttons to panel
        buttonsPanel.add(addIncomeButton);
        buttonsPanel.add(addExpenseButton);
        buttonsPanel.add(transferMoneyButton);

        add(buttonsPanel);
        
        JPanel buttonsPanel2 = new JPanel(); // Adjusted spacing
        buttonsPanel2.setBackground(new Color(22, 66, 60)); // Matches border color of balance panels
        buttonsPanel2.setBounds(350, 380, 400, 240); // Reduced height of the panel

        JButton viewSummaryButton = createDashboardButton("VIEW SUMMARY");
        viewSummaryButton.setFont(new Font("Roboto", Font.BOLD, 16));
        viewSummaryButton.setForeground(Color.WHITE);
        ImageIcon summaryIcon = new ImageIcon("summary1.png");
        Image summaryImage = summaryIcon.getImage().getScaledInstance(350, 180, Image.SCALE_SMOOTH); // Adjust width and height
        summaryIcon = new ImageIcon(summaryImage); // Apply the scaled image
        viewSummaryButton.setIcon(summaryIcon);
        viewSummaryButton.setContentAreaFilled(false); // Make the button background transparent
        viewSummaryButton.setFocusPainted(false); // Removes the focus highlight but retains focusability
        viewSummaryButton.setOpaque(false);
        viewSummaryButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        viewSummaryButton.setVerticalTextPosition(SwingConstants.TOP); // Position the text below the icon
        viewSummaryButton.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon and text horizontally
        viewSummaryButton.setVerticalAlignment(SwingConstants.CENTER); // Center the icon and text vertically
        
        JPanel buttonsPanel3 = new JPanel(); // Adjusted spacing
        buttonsPanel3.setBackground(new Color(22, 66, 60)); // Matches border color of balance panels
        buttonsPanel3.setBounds(800, 380, 400, 240); // Reduced height of the panel

        JButton viewTransactionsButton = createDashboardButton("VIEW TRANSACTIONS");
        viewTransactionsButton.setFont(new Font("Roboto", Font.BOLD, 16));
        viewTransactionsButton.setForeground(Color.WHITE);
        ImageIcon transactionsIcon = new ImageIcon("transac1.png");
        Image transactionsImage = transactionsIcon.getImage().getScaledInstance(350, 180, Image.SCALE_SMOOTH); // Adjust width and height
        transactionsIcon = new ImageIcon(transactionsImage); // Apply the scaled image
        viewTransactionsButton.setIcon(transactionsIcon);
        viewTransactionsButton.setContentAreaFilled(false); // Make the button background transparent
        viewTransactionsButton.setFocusPainted(false); // Removes the focus highlight but retains focusability
        viewTransactionsButton.setOpaque(false);
        viewTransactionsButton.setHorizontalTextPosition(SwingConstants.CENTER); // Center the text
        viewTransactionsButton.setVerticalTextPosition(SwingConstants.TOP); // Position the text below the icon
        viewTransactionsButton.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon and text horizontally
        viewTransactionsButton.setVerticalAlignment(SwingConstants.CENTER); // Center the icon and text vertically
        
        // Add buttons to panel
        buttonsPanel2.add(viewSummaryButton);
        buttonsPanel3.add(viewTransactionsButton);
       
        add(buttonsPanel2);
        add(buttonsPanel3);
        
        JPanel backColor = new JPanel();
        backColor.setBackground(Color.WHITE);  // Set background to white
        backColor.setBounds(0, 50, 1280, 720);
        backColor.setLayout(null);
        add(backColor);

        
    }
   
    private JPanel createBalancePanel(String label, JTextField textField) {
        JPanel balancePanel = new JPanel(new BorderLayout());
        balancePanel.setBackground(new Color(196, 218, 210)); // Matches border color
        balancePanel.setBorder(BorderFactory.createLineBorder(new Color(22, 66, 60), 15)); // Thicker green border

        JLabel balanceLabel = new JLabel(label);
        balanceLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(22, 66, 60));

        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(new Color(22, 66, 60));
        textField.setBackground(Color.WHITE);
        textField.setEditable(true);
        textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        balancePanel.add(balanceLabel, BorderLayout.NORTH);
        balancePanel.add(textField, BorderLayout.CENTER);

        return balancePanel;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(22, 66, 60));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    private JButton createDashboardButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setForeground(new Color(22, 66, 60));
        button.setBackground(new Color(196, 218, 210));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    private JButton createDashboardButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setForeground(new Color(22, 66, 60));
        button.setBackground(new Color(196, 218, 210));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());

        // Set icon if available
        ImageIcon icon = new ImageIcon(iconPath); // Path to your image
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PennywiseDashboard().setVisible(true);
        });
    }
}
