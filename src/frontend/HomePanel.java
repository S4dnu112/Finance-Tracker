package frontend;

import backend.FinanceBackend;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class HomePanel extends JPanel {
    private JTextField bankTextField;
    private JTextField cashTextField;
    private JTextField digitalWalletTextField;
    private JTextField creditCardTextField;
    FinanceBackend fb;

    public HomePanel(FinanceBackend fb) {
        this.fb = fb;

        // ito na mismo ung maginteract
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel: Welcome Message
        JPanel welcomePanel = createWelcomePanel();
        add(welcomePanel, BorderLayout.NORTH);

        // Center Panel: Main Content
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

        // Left Side: Account Balances
        JPanel accountBalancesPanel = createAccountBalancesPanel();
        centerPanel.add(accountBalancesPanel, BorderLayout.WEST);

        // Right Side: Actions and Views
        JPanel actionsAndViewsPanel = createActionsAndViewsPanel();
        centerPanel.add(actionsAndViewsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        welcomePanel.setOpaque(false);

        JLabel helloLabel = new JLabel("Hello,");
        helloLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        helloLabel.setForeground(Color.BLACK);

        JTextField nameTextField = new JTextField(15);
        nameTextField.setFont(new Font("Roboto", Font.BOLD, 36));
        nameTextField.setForeground(Color.BLACK);
        nameTextField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        welcomePanel.add(helloLabel);
        welcomePanel.add(nameTextField);

        return welcomePanel;
    }

    private JPanel createAccountBalancesPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 0, 10));
        panel.setBackground(new Color(22, 66, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setPreferredSize(new Dimension(250, 500));

        // Account Balances Header
        JLabel headerLabel = new JLabel("ACCOUNT BALANCES", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        headerLabel.setForeground(Color.WHITE);
        panel.add(headerLabel);

        // Balance Fields
        bankTextField = new JTextField("\u20B1 " + fb.getAccountBalances().get("Bank"));
        panel.add(createBalancePanel("BANK", bankTextField));

        cashTextField = new JTextField("\u20B1 " + fb.getAccountBalances().get("Cash"));
        panel.add(createBalancePanel("CASH", cashTextField));

        digitalWalletTextField = new JTextField("\u20B1 " + fb.getAccountBalances().get("Digital Wallets"));
        panel.add(createBalancePanel("DIGITAL WALLET", digitalWalletTextField));

        creditCardTextField = new JTextField("\u20B1 " + + fb.getAccountBalances().get("Credit Card"));
        panel.add(createBalancePanel("CREDIT CARD", creditCardTextField));

        return panel;
    }

    private JPanel createActionsAndViewsPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Action Buttons Panel
        JPanel actionButtonsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        actionButtonsPanel.setOpaque(false);
        actionButtonsPanel.add(createActionButton("ADD INCOME", "src\\resources\\Images\\income.png"));
        actionButtonsPanel.add(createActionButton("ADD EXPENSE", "src\\resources\\Images\\expense.png"));
        actionButtonsPanel.add(createActionButton("TRANSFER MONEY", "src\\resources\\Images\\transfer.png"));

        // View Buttons Panel
        JPanel viewButtonsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        viewButtonsPanel.setOpaque(false);

        // Create individual panels for each view button with dark green background
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(new Color(22, 66, 60));
        summaryPanel.add(createViewButton("VIEW SUMMARY", "src\\resources\\Images\\summary.png"));

        JPanel transactionsPanel = new JPanel(new GridBagLayout());
        transactionsPanel.setBackground(new Color(22, 66, 60));
        transactionsPanel.add(createViewButton("VIEW ALL TRANSACTIONS", "src\\resources\\Images\\transac.png"));

        viewButtonsPanel.add(summaryPanel);
        viewButtonsPanel.add(transactionsPanel);

        // Add panels using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(actionButtonsPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.6;
        mainPanel.add(viewButtonsPanel, gbc);

        return mainPanel;
    }

    private JPanel createBalancePanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(196, 218, 210));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(22, 66, 60), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel balanceLabel = new JLabel(label);
        balanceLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        balanceLabel.setForeground(new Color(22, 66, 60));

        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(new Color(22, 66, 60));
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        panel.add(balanceLabel, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + iconPath);
        }

        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        return button;
    }

    private JButton createViewButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image image = icon.getImage().getScaledInstance(530, 270, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + iconPath);
        }

        button.setVerticalTextPosition(SwingConstants.TOP);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        return button;
    }
}