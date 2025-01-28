import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AddExpenses1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load fonts dynamically
                Font robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
                Font interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\Inter-Regular.ttf")).deriveFont(14f);
                Font smallerInterRegular = interRegular.deriveFont(12f);

                JFrame frame = new JFrame("Add Expenses");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1536, 864);
                frame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBackground(Color.WHITE);

                // Header Panel
                JPanel headerPanel = new JPanel(new GridBagLayout());
                headerPanel.setBackground(new Color(22, 70, 65));
                headerPanel.setPreferredSize(new Dimension(1280, 70));
                headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

                GridBagConstraints headerGbc = new GridBagConstraints();
                headerGbc.insets = new Insets(0, 10, 0, 10); // Padding for components
                headerGbc.fill = GridBagConstraints.BOTH;

                // Left Section: Logo and Icon
                JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
                logoPanel.setBackground(new Color(22, 70, 65));

                ImageIcon pennyIcon = new ImageIcon("C:\\Users\\louie\\Documents\\Github\\Finance-Tracker\\src\\FrontEnd\\Images\\coins.png");
                Image scaledImage = pennyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(scaledImage);

                JLabel iconLabel = new JLabel(resizedIcon);
                JLabel logo = new JLabel("PENNYWISE");
                logo.setForeground(Color.WHITE);
                logo.setFont(interRegular.deriveFont(Font.BOLD, 16f));
                logoPanel.add(iconLabel);
                logoPanel.add(logo);

                headerGbc.gridx = 0;
                headerGbc.weightx = 1;
                headerGbc.anchor = GridBagConstraints.WEST;
                headerPanel.add(logoPanel, headerGbc);

                // Right Section: Buttons
                JPanel headerRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
                headerRightPanel.setBackground(new Color(22, 70, 65));

                JButton homeButton = new JButton("HOME");
                homeButton.setBackground(new Color(22, 70, 65));
                homeButton.setForeground(Color.WHITE);
                homeButton.setFont(interRegular.deriveFont(Font.BOLD)); // Make the font bold
                homeButton.setBorderPainted(false);
                homeButton.setFocusable(false);
                homeButton.addActionListener(e -> System.out.println("Home button clicked"));
                headerRightPanel.add(homeButton);                

                JButton settingsButton = new JButton("SETTINGS");
                settingsButton.setBackground(new Color(22, 70, 65));
                settingsButton.setForeground(Color.WHITE);
                settingsButton.setFont(interRegular);
                settingsButton.setBorderPainted(false);
                settingsButton.setFocusable(false);
                settingsButton.addActionListener(e -> System.out.println("Settings button clicked"));
                headerRightPanel.add(settingsButton);

                headerGbc.gridx = 1;
                headerGbc.weightx = 0;
                headerGbc.anchor = GridBagConstraints.EAST;
                headerPanel.add(headerRightPanel, headerGbc);

                // Wrapper Panel
                JPanel wrapperPanel = new JPanel(new BorderLayout());
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder(-10, -20, 30, -50));
                wrapperPanel.setBackground(Color.WHITE);
                wrapperPanel.setPreferredSize(new Dimension(600, 500));

                // Form Panel
                JPanel formPanel = new JPanel();
                formPanel.setLayout(new GridBagLayout());
                formPanel.setBackground(Color.WHITE);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel title = new JLabel("Add Expenses", SwingConstants.CENTER);
                title.setFont(robotoExtraBold);
                title.setForeground(new Color(22, 70, 65));
                gbc.gridwidth = 2;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                formPanel.add(title, gbc);

                gbc.gridwidth = 1;
                gbc.gridx = 0;

                JLabel amountLabel = new JLabel("Amount: ");
                amountLabel.setFont(interRegular);
                gbc.gridy = 1;
                formPanel.add(amountLabel, gbc);

                JTextField amountField = new JTextField();
                amountField.setPreferredSize(new Dimension(210, 35));
                amountField.setFont(interRegular);
                amountField.setBackground(new Color(196, 218, 210));
                amountField.setForeground(new Color(22, 70, 65));
                amountField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                gbc.gridx = 1;
                formPanel.add(amountField, gbc);

                JLabel categoriesLabel = new JLabel("Categories:");
                categoriesLabel.setFont(interRegular);
                gbc.gridx = 0;
                gbc.gridy = 2;
                formPanel.add(categoriesLabel, gbc);

                JPanel categoriesPanel = new JPanel(new GridLayout(3, 2));
                categoriesPanel.setBackground(Color.WHITE);
                ButtonGroup categoryGroup = new ButtonGroup();

                String[] categories = {"Food & Dining", "Leisure & Shopping", "Transportation", "Household", "Family & Education", "Health & Wellness"};
                for (String category : categories) {
                    JRadioButton radioButton = new JRadioButton(category);
                    radioButton.setFont(interRegular);
                    radioButton.setBackground(Color.WHITE); // Set background to white
                    radioButton.setOpaque(false); // Ensure transparency
                    radioButton.setIcon(new CustomRadioButtonIcon(new Color(196, 218, 210), new Color(22, 70, 65))); // Custom styled icon
                    radioButton.setFocusPainted(false); // Remove focus border
                    categoryGroup.add(radioButton); // Add to button group
                    categoriesPanel.add(radioButton);
                }
                gbc.gridx = 1;
                formPanel.add(categoriesPanel, gbc);

                UIManager.put("ComboBox.focus", new Color(0, 0, 0, 0)); // Remove the focus border
                UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder()); // Remove the default border
                UIManager.put("ComboBox.background", new Color(196, 218, 210)); // Set the background color explicitly

                JLabel accountLabel = new JLabel("Account:");
                accountLabel.setFont(interRegular);
                gbc.gridx = 0;
                gbc.gridy = 3;
                formPanel.add(accountLabel, gbc);

                JComboBox<String> accountDropdown = new JComboBox<>(new String[]{"Select Account"});
                accountDropdown.setFont(interRegular.deriveFont(Font.ITALIC, 14f));
                accountDropdown.setPreferredSize(new Dimension(210, 35));
                accountDropdown.setBackground(new Color(196, 218, 210));
                accountDropdown.setForeground(new Color(22, 70, 65));
                accountDropdown.setBorder(BorderFactory.createEmptyBorder()); // Remove the default border
                gbc.gridx = 1;
                formPanel.add(accountDropdown, gbc);

                JLabel recurrenceLabel = new JLabel("Recurrence:");
                recurrenceLabel.setFont(interRegular);
                gbc.gridx = 0;
                gbc.gridy = 4;
                formPanel.add(recurrenceLabel, gbc);

                JComboBox<String> recurrenceDropdown = new JComboBox<>(new String[]{"Select Recurrence"});
                recurrenceDropdown.setFont(interRegular.deriveFont(Font.ITALIC, 14f));
                recurrenceDropdown.setPreferredSize(new Dimension(210, 35));
                recurrenceDropdown.setBackground(new Color(196, 218, 210));
                recurrenceDropdown.setForeground(new Color(22, 70, 65));
                recurrenceDropdown.setBorder(BorderFactory.createEmptyBorder()); // Remove the default border
                gbc.gridx = 1;
                formPanel.add(recurrenceDropdown, gbc);

                // Start and End Date Panel
                JPanel datePanel = new JPanel(new GridBagLayout());
                datePanel.setBackground(Color.WHITE);
                GridBagConstraints dateGbc = new GridBagConstraints();
                dateGbc.insets = new Insets(10, 10, 10, 10);
                dateGbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel startDateLabel = new JLabel("Start-date:");
                startDateLabel.setFont(interRegular);
                dateGbc.gridx = 0;
                dateGbc.gridy = 5;
                datePanel.add(startDateLabel, dateGbc);

                JTextField startDateField = new JTextField("yyyy-mm-dd");
                startDateField.setFont(smallerInterRegular);
                startDateField.setPreferredSize(new Dimension(100, 35));
                startDateField.setBackground(new Color(196, 218, 210));
                startDateField.setForeground(new Color(150, 150, 150));
                startDateField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                dateGbc.gridx = 1;
                datePanel.add(startDateField, dateGbc);

                startDateField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        if (startDateField.getText().equals("yyyy-mm-dd")) {
                            startDateField.setText("");
                            startDateField.setForeground(new Color(22, 70, 65));
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        if (startDateField.getText().isEmpty()) {
                            startDateField.setText("yyyy-mm-dd");
                            startDateField.setForeground(new Color(150, 150, 150));
                        }
                    }
                });

                JLabel endDateLabel = new JLabel("End-date:");
                endDateLabel.setFont(interRegular);
                dateGbc.gridx = 2;
                datePanel.add(endDateLabel, dateGbc);

                JTextField endDateField = new JTextField("yyyy-mm-dd");
                endDateField.setFont(smallerInterRegular);
                endDateField.setPreferredSize(new Dimension(100, 35));
                endDateField.setBackground(new Color(196, 218, 210));
                endDateField.setForeground(new Color(150, 150, 150));
                endDateField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                dateGbc.gridx = 3;
                datePanel.add(endDateField, dateGbc);

                endDateField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent evt) {
                        if (endDateField.getText().equals("yyyy-mm-dd")) {
                            endDateField.setText("");
                            endDateField.setForeground(new Color(22, 70, 65));
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        if (endDateField.getText().isEmpty()) {
                            endDateField.setText("yyyy-mm-dd");
                            endDateField.setForeground(new Color(150, 150, 150));
                        }
                    }
                });

                // Add datePanel to the formPanel
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                formPanel.add(datePanel, gbc);


                JLabel descriptionLabel = new JLabel("Description:");
                descriptionLabel.setFont(interRegular);
                gbc.gridx = 0;
                gbc.gridy = 7;
                gbc.anchor = GridBagConstraints.NORTH;
                formPanel.add(descriptionLabel, gbc);

                JTextArea descriptionArea = new JTextArea(3, 20);
                descriptionArea.setFont(interRegular);
                descriptionArea.setBackground(new Color(196, 218, 210));
                descriptionArea.setForeground(new Color(22, 70, 65));
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(descriptionArea);
                scrollPane.setPreferredSize(new Dimension(210, 105));
                scrollPane.setBorder(BorderFactory.createEmptyBorder());
                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.BOTH;
                formPanel.add(scrollPane, gbc);

                JButton saveButton = new JButton("SAVE");
                saveButton.setFont(interRegular);
                saveButton.setBackground(new Color(22, 70, 65));
                saveButton.setForeground(Color.WHITE);
                saveButton.setFocusable(false);
                
                // Set preferred size for smaller width and larger height
                saveButton.setPreferredSize(new Dimension(saveButton.getPreferredSize().width + 20, 50)); // Adjust width and height
                
                gbc.gridy = 8;
                gbc.gridwidth = 1; // Ensure it doesn't stretch across columns
                formPanel.add(saveButton, gbc);                

                wrapperPanel.add(formPanel, BorderLayout.CENTER);
                mainPanel.add(headerPanel, BorderLayout.NORTH);
                mainPanel.add(wrapperPanel, BorderLayout.WEST);

                // Add main panel to frame
                frame.add(mainPanel);
                frame.setVisible(true);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}

class CustomRadioButtonIcon implements Icon {
    private final int size = 16; // Size of the radio button
    private final Color backgroundColor;
    private final Color dotColor;

    public CustomRadioButtonIcon(Color backgroundColor, Color dotColor) {
        this.backgroundColor = backgroundColor;
        this.dotColor = dotColor;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        JRadioButton rb = (JRadioButton) c;

        // Draw the outer circle
        g2.setColor(backgroundColor);
        g2.fillOval(x, y, size, size);
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(x, y, size - 1, size - 1);

        // Draw the inner dot if selected
        if (rb.isSelected()) {
            g2.setColor(dotColor);
            g2.fillOval(x + 4, y + 4, size - 8, size - 8);
        }

        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}