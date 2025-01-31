package frontend;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import backend.FinanceBackend;


public class SettingsPanel extends JPanel {
    private Font robotoExtraBold;
    private Font interRegular;
    private Font interExtraBold;
    private Font smallerInterRegular;
    private JTextField nameField;
    private JButton updateButton;
    private Color editableColor = new Color(196, 218, 210);
    private Color uneditableColor = new Color(175, 200, 190);
    private Color updateDefaultColor = new Color(150, 200, 180);
    private Color updateActiveColor = new Color(22, 70, 65);
    private boolean isChanged = false;
    private FinanceBackend Fb;

    private String[] usageOptions = {"Select Purpose", "Student", "Personal", "Small business"};
    private String[] reindexOptions = {"Select Table", "Income table", "Expense table", "Transfer table"};
    private JComboBox<String> usageComboBox;
    private JComboBox<String> reindexComboBox;


    public SettingsPanel(FinanceBackend Fb) {

        this.Fb = Fb;
        loadFonts();
        this.nameField = new JTextField();
        this.updateButton = new JButton("UPDATE");
        
        setupPanel();
    }
    private void loadFonts() {
        try {
            interRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Inter-Regular.ttf")).deriveFont(14f);
            robotoExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("src\\resources\\Fonts\\Roboto-ExtraBold.ttf")).deriveFont(40f);
            smallerInterRegular = interRegular.deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            interRegular = new Font("Arial", Font.PLAIN, 14);
            robotoExtraBold = new Font("Arial", Font.BOLD, 40);
        }
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(25, 30, 30, -50));
        setPreferredSize(new Dimension(800, 500));

        add(createFormPanel(), BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addTitle(formPanel, gbc);
        addNameSection(formPanel, gbc);
        addPurposeSection(formPanel, gbc);
        addReindexSection(formPanel, gbc);
        addButtonPanel(formPanel, gbc);

        return formPanel;
    }

    private void addTitle(JPanel panel, GridBagConstraints gbc) {
        JLabel workspaceSettingsLabel = new JLabel("Workspace Settings", SwingConstants.CENTER);
        workspaceSettingsLabel.setFont(robotoExtraBold);
        workspaceSettingsLabel.setForeground(new Color(22, 70, 65));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(workspaceSettingsLabel, gbc);
    }

    private void addNameSection(JPanel panel, GridBagConstraints gbc) {
        // Name Label
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(interExtraBold);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nameLabel, gbc);

        // Name Field Panel
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(Color.WHITE);

        nameField.setFont(interRegular);
        nameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        nameField.setEditable(false);
        nameField.setBackground(uneditableColor);
        nameField.setPreferredSize(new Dimension(600, 30));

        JButton editButton = createEditButton();
        
        namePanel.add(nameField, BorderLayout.CENTER);
        namePanel.add(editButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(namePanel, gbc);

        // Name Hint
        JLabel nameHint = new JLabel("You can use your organization or company name. Keep it simple.");
        nameHint.setFont(smallerInterRegular.deriveFont(Font.ITALIC));
        nameHint.setForeground(new Color(150, 150, 150));
        gbc.insets = new Insets(5, 5, 30, 5);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(nameHint, gbc);
    }

    private void addPurposeSection(JPanel panel, GridBagConstraints gbc) {
        JLabel purposeLabel = new JLabel("What will you be using Pennywise for?");
        purposeLabel.setFont(interExtraBold);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(purposeLabel, gbc);

        usageComboBox = createStyledComboBox(usageOptions);
        gbc.insets = new Insets(5, 5, 30, 5);
        gbc.gridy = 5;
        panel.add(usageComboBox, gbc);
    }

    private void addReindexSection(JPanel panel, GridBagConstraints gbc) {
        JLabel reindexLabel = new JLabel("Reindex Database:");
        reindexLabel.setFont(interExtraBold);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 6;
        panel.add(reindexLabel, gbc);

        reindexComboBox = createStyledComboBox(reindexOptions);
        gbc.insets = new Insets(5, 5, 100, 5);
        gbc.gridy = 7;
        panel.add(reindexComboBox, gbc);
    }

    private JComboBox<String> createStyledComboBox(String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(interRegular.deriveFont(Font.ITALIC));
        comboBox.setBackground(new Color(196, 218, 210));
        comboBox.setForeground(new Color(22, 70, 65));
        comboBox.setBorder(BorderFactory.createEmptyBorder());
        comboBox.setPreferredSize(new Dimension(300, 30));
        comboBox.setEnabled(false);
        return comboBox;
    }

    private void addButtonPanel(JPanel panel, GridBagConstraints gbc) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton cancelButton = createStyledButton("CANCEL", new Color(150, 150, 150));
        updateButton.setPreferredSize(new Dimension(150, 50));
        updateButton.setMinimumSize(new Dimension(150, 50));
        updateButton.setMaximumSize(new Dimension(150, 50));        
        updateButton.setBackground(updateDefaultColor);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(interRegular);
        updateButton.setFocusable(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(updateButton);

        setupButtonListeners(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(buttonPanel, gbc);
    }

    private JButton createEditButton() {
        JButton editButton = new JButton("Edit");
        editButton.setFont(interRegular);
        editButton.setBackground(new Color(22, 70, 65));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);
        
        editButton.addActionListener(e -> {
            nameField.setEditable(true);
            nameField.setBackground(editableColor);
            usageComboBox.setSelectedIndex(0);
            usageComboBox.setEnabled(true);
            reindexComboBox.setSelectedIndex(0);
            reindexComboBox.setEnabled(true);
            setChanged(true);
        });
        
        return editButton;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(interRegular);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.setMinimumSize(new Dimension(150, 50));
        button.setMaximumSize(new Dimension(150, 50));
        return button;
    }

    private void setupButtonListeners(JButton cancelButton) {
        // Document listener for name field
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { setChanged(true); }
            @Override
            public void removeUpdate(DocumentEvent e) { setChanged(true); }
            @Override
            public void changedUpdate(DocumentEvent e) { setChanged(true); }
        });

        // Update button listener
        updateButton.addActionListener(e -> {
            if (isChanged) {
                setChanged(false);
                nameField.setEditable(false);
                nameField.setBackground(uneditableColor);
                Map<String, String> name = new HashMap<>();
                name.put("name", nameField.getText());
                try {
                    Fb.writeUserData(name);
                    System.out.println(reindexComboBox.getSelectedItem().toString());
                    int selected = 0;
                    if((selected = reindexComboBox.getSelectedIndex()) != 0)
                        Fb.reindex(reindexComboBox.getSelectedItem().toString());
                        System.out.println(reindexComboBox.getSelectedItem().toString());
                } catch (IOException ioException) {
                    System.err.println("Error writing user data on settings panel, find user data method on backend");
                }
                System.out.println("Changes have been updated!");
            }
        });

        // Cancel button listener
        cancelButton.addActionListener(e -> {
            nameField.setEditable(false);
            nameField.setBackground(uneditableColor);
            usageComboBox.setSelectedIndex(-1);
            usageComboBox.setEnabled(false);
            reindexComboBox.setSelectedIndex(-1);
            reindexComboBox.setEnabled(false);
            setChanged(false);
            System.out.println("Changes were canceled.");
        });
    }

    private void setChanged(boolean changed) {
        isChanged = changed;
        updateButton.setBackground(changed ? updateActiveColor : updateDefaultColor);
    }
}