package frontend;

import backend.FinanceBackend;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class HomePanel extends JPanel {
    private static final int PADDING = 20;
    private static final Color PRIMARY_COLOR = new Color(22, 66, 60);
    private static final Color SECONDARY_COLOR = Color.WHITE;
    private static final Font HEADER_FONT = new Font("Roboto", Font.BOLD, 36);
    private static final Font LABEL_FONT = new Font("Roboto", Font.BOLD, 16);
    private static final Font BALANCE_FONT = new Font("Roboto", Font.BOLD, 24);
    private static final Font SUBTITLE_FONT = new Font("Roboto", Font.PLAIN, 12);
    
    private final JLabel bankBalance;
    private final JLabel cashBalance;
    private final JLabel digitalWalletBalance;
    private final JLabel creditCardBalance;

    private final String userName;
    private final FinanceBackend fb;

    public HomePanel(FinanceBackend fb) {
        this.fb = fb;
        this.userName = initializeUserName();
        
        // Initialize text fields first
        this.bankBalance = createBalanceLabel("Bank");
        this.cashBalance = createBalanceLabel("Cash");
        this.digitalWalletBalance = createBalanceLabel("Digital Wallets");
        this.creditCardBalance = createBalanceLabel("Credit Card");
        
        // Setup panel
        setupMainPanel();
        
        // Initialize UI Components
        JPanel welcomePanel = createWelcomePanel();
        JPanel centerPanel = createCenterPanel();
        
        // Add components to main panel
        add(welcomePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private String initializeUserName() {
        try {
            return fb.readUserData().get("name");
        } catch (Exception e) {
            return "PennyWise";
        }
    }

    private void setupMainPanel() {
        setLayout(new BorderLayout(PADDING, PADDING));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(PADDING, PADDING));
        centerPanel.setOpaque(false);
        
        JPanel accountBalancesPanel = createAccountBalancesPanel();
        JPanel actionsAndViewsPanel = createActionsAndViewsPanel();
        
        centerPanel.add(accountBalancesPanel, BorderLayout.WEST);
        centerPanel.add(actionsAndViewsPanel, BorderLayout.CENTER);
        
        return centerPanel;
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, PADDING / 2, 0));
        welcomePanel.setOpaque(false);

        JLabel helloLabel = new JLabel("Hello, " + userName + "!");
        helloLabel.setFont(HEADER_FONT);
        helloLabel.setForeground(Color.BLACK);
        welcomePanel.add(helloLabel);
        
        return welcomePanel;
    }

    private JLabel createBalanceLabel(String accountType) {
        String balance = "\u20B1 " + fb.getAccountBalances().get(accountType);
        JLabel label = new JLabel(balance);
        label.setFont(BALANCE_FONT);
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    private JPanel createAccountBalancesPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 0, PADDING / 2));
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.setPreferredSize(new Dimension(300, 500));

        // Header
        JLabel headerLabel = new JLabel(("<html><div style='text-align: center;'>Account<br> Balances</div></html>"));
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, PADDING, 0));
        panel.add(headerLabel);
        
        // Balance cards
        panel.add(createBalanceCard("BANK", bankBalance));
        panel.add(createBalanceCard("CASH", cashBalance));
        panel.add(createBalanceCard("DIGITAL WALLETS", digitalWalletBalance));
        panel.add(createBalanceCard("CREDIT CARD", creditCardBalance));

        return panel;
    }

    private JPanel createBalanceCard(String title, JLabel balanceLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(SECONDARY_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(10, SECONDARY_COLOR),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Balance
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Available Balance text
        JLabel availableLabel = new JLabel("Available Balance");
        availableLabel.setFont(SUBTITLE_FONT);
        availableLabel.setForeground(Color.GRAY);
        availableLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(balanceLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(availableLabel);

        return card;
    }
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
    }

    private JPanel createActionsAndViewsPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Action Buttons Section
        JPanel actionButtons = createActionButtonsPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(actionButtons, gbc);
        
        // View Buttons Section
        JPanel viewButtons = createViewButtonsPanel();
        gbc.gridy = 1;
        gbc.weighty = 0.6;
        mainPanel.add(viewButtons, gbc);

        return mainPanel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, PADDING, 0));
        panel.setOpaque(false);

        JButton btnIncome = createActionButton("ADD INCOME", "src\\resources\\Images\\income.png");
        JButton btnExpense = createActionButton("ADD EXPENSE", "src\\resources\\Images\\expense.png");
        JButton btnTransfer = createActionButton("TRANSFER MONEY", "src\\resources\\Images\\transfer.png");

        setupActionButtonListeners(btnIncome, btnExpense, btnTransfer);
        
        panel.add(btnIncome);
        panel.add(btnExpense);
        panel.add(btnTransfer);

        return panel;
    }

    private void setupActionButtonListeners(JButton income, JButton expense, JButton transfer) {
        income.addActionListener(e -> PanelManager.getInstance().showPanel("Income"));
        expense.addActionListener(e -> PanelManager.getInstance().showPanel("Expense"));
        transfer.addActionListener(e -> PanelManager.getInstance().showPanel("Transfer"));
    }

    private JPanel createViewButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, PADDING, 0));
        panel.setOpaque(false);

        JPanel summaryPanel = createViewButtonContainer("VIEW SUMMARY", "src\\resources\\Images\\summary.png", "Summary");
        JPanel transactionsPanel = createViewButtonContainer("VIEW ALL TRANSACTIONS", "src\\resources\\Images\\transac.png", "Transaction");

        panel.add(summaryPanel);
        panel.add(transactionsPanel);

        return panel;
    }

    private JPanel createViewButtonContainer(String text, String iconPath, String panelName) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PRIMARY_COLOR);
        
        JButton button = createViewButton(text, iconPath);
        button.addActionListener(e -> PanelManager.getInstance().showPanel(panelName));
        
        container.add(button);
        return container;
    }

    private JButton createActionButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        setupButtonStyle(button, iconPath, 150, 150, SwingConstants.BOTTOM);
        return button;
    }

    private JButton createViewButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.setForeground(Color.WHITE);
        setupButtonStyle(button, iconPath, 530, 270, SwingConstants.TOP);
        return button;
    }

    private void setupButtonStyle(JButton button, String iconPath, int iconWidth, int iconHeight, int verticalPosition) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledImage = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + iconPath);
        }

        button.setVerticalTextPosition(verticalPosition);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
    }
}