import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

class TransactionBodyPanel extends JPanel {
    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionBodyPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel for the delete button at the upper right
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 10));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(Color.RED);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 15));
        deleteButton.addActionListener(e -> deleteSelectedRow());
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Table for Transactions
        String[] columnNames = {"Amount", "Account", "Description", "Date-Added", "Recurrence", "Start-Date", "End-Date"};
        tableModel = new DefaultTableModel(columnNames, 6) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("Inter", Font.PLAIN, 14));
        transactionTable.setRowHeight(40);
        transactionTable.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));

        // Set column widths
        int[] columnWidths = {150, 150, 200, 150, 150, 150, 150};
        for (int i = 0; i < columnWidths.length; i++) {
            transactionTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Set table styles
        transactionTable.setBackground(Color.WHITE);
        transactionTable.getTableHeader().setBackground(Color.WHITE);
        transactionTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 20));

        JScrollPane tableScrollPane = new JScrollPane(transactionTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    private void deleteSelectedRow() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TransactionBodyPanel());
        frame.setVisible(true);
    }
}
