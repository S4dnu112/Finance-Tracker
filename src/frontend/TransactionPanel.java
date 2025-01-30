package frontend;

import backend.FinanceBackend;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class TransactionPanel extends JPanel {
    private FinanceBackend fb = new FinanceBackend();
    private JPanel mainTable;
    private JPanel tablePanel;
    private String currentTableType = "Income"; // Track current table type
    
    public TransactionPanel() {
        setLayout(new BorderLayout());
        createMainContent();
    }
    
    private void createMainContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        
        JLabel transactionLabel = new JLabel("Transactions");
        transactionLabel.setFont(new Font("Inter", Font.BOLD, 40));
        transactionLabel.setForeground(new Color(22, 66, 60));
        titlePanel.add(transactionLabel);
        
        // Left Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(196, 218, 210));
        menuPanel.setPreferredSize(new Dimension(200, -1));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        
        JButton incomeButton = new JButton("Income", resizeIcon("src/resources/Images/income.png", 40, 40));
        JButton expenseButton = new JButton("Expense", resizeIcon("src/resources/Images/expense.png", 40, 40));
        JButton transferButton = new JButton("Transfer", resizeIcon("src/resources/Images/transfer.png", 40, 40));
        
        styleMenuButton(incomeButton);
        styleMenuButton(expenseButton);
        styleMenuButton(transferButton);
        
        incomeButton.addActionListener(e -> loadTransactionData("Income"));
        expenseButton.addActionListener(e -> loadTransactionData("Expense"));
        transferButton.addActionListener(e -> loadTransactionData("Transfer"));
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(incomeButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(expenseButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(transferButton);
        
        // Table Panel
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        
        // Delete button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton(resizeIcon("src/resources/Images/delete.png", 25, 25));
        deleteButton.setBackground(new Color(238, 238, 238));
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 15));
        deleteButton.setPreferredSize(new Dimension(25, 25));
        deleteButton.addActionListener(e -> deleteSelectedRow());
        buttonPanel.add(deleteButton);
        
        // Initialize mainTable
        mainTable = fb.getIncomesTable();
        
        tablePanel.add(buttonPanel, BorderLayout.NORTH);
        tablePanel.add(mainTable, BorderLayout.CENTER);
        
        // Add components to content panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(titlePanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        contentPanel.add(menuPanel, BorderLayout.WEST);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void styleMenuButton(JButton button) {
        button.setFont(new Font("Inter", Font.PLAIN, 22));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(196, 218, 210));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(180, 50));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(165, 193, 183));
                button.setFont(new Font("Inter", Font.BOLD, 25));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(196, 218, 210));
                button.setFont(new Font("Inter", Font.PLAIN, 22));
            }
        });
    }
    
    private void loadTransactionData(String type) {
        // Remove the old table
        tablePanel.remove(mainTable);
        
        // Update current table type
        currentTableType = type;
        
        // Get and add the new table
        switch (type) {
            case "Income" -> mainTable = fb.getIncomesTable();
            case "Expense" -> mainTable = fb.getExpensesTable();
            case "Transfer" -> mainTable = fb.getTransfersTable();
        }
        
        // Add the new table
        tablePanel.add(mainTable, BorderLayout.CENTER);
        
        // Force the panel to update
        tablePanel.revalidate();
        tablePanel.repaint();
    }
    
    private void deleteSelectedRow() {
        // Show input dialog for ID
        String idString = JOptionPane.showInputDialog(this,
            "Enter the ID to delete from " + currentTableType + " table:",
            "Delete Record",
            JOptionPane.QUESTION_MESSAGE);
        
        // Check if user canceled or entered nothing
        if (idString == null || idString.trim().isEmpty()) {
            return;
        }
        
        try {
            int id = Integer.parseInt(idString);
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete ID " + id + " from " + currentTableType + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {                
                try {
                    switch (currentTableType) {
                        case "Income" -> fb.remove("Income", id);
                        case "Expense" -> fb.remove("Expense", id);
                        case "Transfer" -> fb.remove("Transfer", id);
                    }

                    loadTransactionData(currentTableType);
                    JOptionPane.showMessageDialog(this,
                        "Record deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                    "Failed to delete record. ID may not exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid number for ID",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Transaction Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TransactionPanel());
        frame.setVisible(true);
    }
}