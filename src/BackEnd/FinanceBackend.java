package backend;
import java.util.HashMap;

import transactionModels.Expense;
import transactionModels.Income;
import transactionModels.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

public class FinanceBackend {
    private HashMap<String, Double> accountBalances = new HashMap<>();
    private HashMap<String, Double> expensePerCategory = new HashMap<>();
    private Double totalIncome;
    private Double totalExpense;
    private final String DB_URL = "jdbc:sqlite:src\\database\\transactions.db";


    // BACKEND ATTRIBUTES (AGGREGATES)
    public Double getTotalIncome() {
        return totalIncome;
    }
    public Double getTotalExpense() {
        return totalExpense;
    }
    public HashMap<String, Double> getAccountBalances() {
        return accountBalances;
    }
    public HashMap<String, Double> getExpensePerCategory() {
        return expensePerCategory;
    }

    /*
    * CONSTRUCTING THE BACKEND WILL AUTOMATICALLY LOAD AGGREGATES DATA FROM PREVIOUS TRANSACTIONS
    */
    public FinanceBackend() {
        try(Connection db = DriverManager.getConnection(DB_URL)) {
            getAggregates(getIncomeData(db), getExpenseData(db), getTransferData(db));
        } catch(SQLException e) {
            System.err.println("Database error (Constructor): " + e.getMessage());
        }
    }   

    /*
     * SAVING DATA TO THE SQLITE DATABASE AND UPDATING THE AGGREGATES
     */
    public void save(Income income) {
        updateAggregates("add", income);
    
        String createQuery = """
            CREATE TABLE IF NOT EXISTS incomes (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                Account TEXT NOT NULL,
                Date_Added TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                Start_Date TEXT NOT NULL,
                End_Date TEXT NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO incomes (Amount, Account, Date_Added, Recurrence, Start_Date, End_Date, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection db = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = db.createStatement()) {
                createStmt.execute(createQuery);
            }
            try (PreparedStatement insertStmt = db.prepareStatement(insertQuery)) {
                insertStmt.setDouble(1, income.getBaseAmount());
                insertStmt.setString(2, income.getAccount());
                insertStmt.setString(3, income.getDateAdded());
                insertStmt.setString(4, income.getRecurrence());
                insertStmt.setString(5, income.getRecurrenceStartDate());
                insertStmt.setString(6, income.getRecurrenceEndDate());
                insertStmt.setString(7, income.getDescription());
                insertStmt.execute();
            }
        } catch (SQLException e) {
            System.err.println("Database error on save(Income): " + e.getMessage());
        }
    }
    
    public void save(Expense expense) {
        updateAggregates("add", expense);
    
        String createQuery = """
            CREATE TABLE IF NOT EXISTS expenses (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                Category TEXT NOT NULL,
                Account TEXT NOT NULL,
                Date_Added TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                Start_Date TEXT NOT NULL,
                End_Date TEXT NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO expenses (Amount, Category, Account, Date_Added, Recurrence, Start_Date, End_Date, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection db = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = db.createStatement()) {
                createStmt.execute(createQuery);
            }
            try (PreparedStatement insertStmt = db.prepareStatement(insertQuery)) {
                insertStmt.setDouble(1, expense.getBaseAmount());
                insertStmt.setString(2, expense.getCategory());
                insertStmt.setString(3, expense.getAccount());
                insertStmt.setString(4, expense.getDateAdded());
                insertStmt.setString(5, expense.getRecurrence());
                insertStmt.setString(6, expense.getRecurrenceStartDate());
                insertStmt.setString(7, expense.getRecurrenceEndDate());
                insertStmt.setString(8, expense.getDescription());
                insertStmt.execute();
            }
        } catch (SQLException e) {
            System.err.println("Database error on save(Expense): " + e.getMessage());
        }
    }
    
    public void save(Transfer transfer) {
        updateAggregates("add", transfer);
    
        String createQuery = """
            CREATE TABLE IF NOT EXISTS transfers (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                Source TEXT NOT NULL,
                Destination TEXT NOT NULL,
                Date_Added TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                Start_Date TEXT NOT NULL,
                End_Date TEXT NOT NULL,
                Transaction_Fee REAL NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO transfers (Amount, Source, Destination, Date_Added, Recurrence, Start_Date, End_Date, Transaction_Fee, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection db = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = db.createStatement()) {
                createStmt.execute(createQuery);
            }
            try (PreparedStatement insertStmt = db.prepareStatement(insertQuery)) {
                insertStmt.setDouble(1, transfer.getBaseAmount());
                insertStmt.setString(2, transfer.getAccount());
                insertStmt.setString(3, transfer.getToAccount());
                insertStmt.setString(4, transfer.getDateAdded());
                insertStmt.setString(5, transfer.getRecurrence());
                insertStmt.setString(6, transfer.getRecurrenceStartDate());
                insertStmt.setString(7, transfer.getRecurrenceEndDate());
                insertStmt.setDouble(8, transfer.getTransactionFee());
                insertStmt.setString(9, transfer.getDescription());
                insertStmt.execute();
            }
        } catch (SQLException e) {
            System.err.println("Database error on save(Transfer): " + e.getMessage());
        }
    }
    
    /*
     * REMOVES RECORD FROM THE DATABASE AND UPDATES THE AGGREGATES
     */
    public void remove(String transaction, int transactionID) {
        String tableName = validateTransactionName(transaction);
        String selectQuery = "SELECT * FROM " + tableName + " WHERE ID = ?";
        String deleteQuery = "DELETE FROM " + tableName + " WHERE ID = ?";
    
        try (Connection db = DriverManager.getConnection(DB_URL);
             PreparedStatement selectStmt = db.prepareStatement(selectQuery);
             PreparedStatement deleteStmt = db.prepareStatement(deleteQuery)) {
    
            selectStmt.setInt(1, transactionID);
            ResultSet rs = selectStmt.executeQuery();
    
            if(!rs.next())
                System.err.println("Transaction with ID " + transactionID + " not found in " + tableName + " table.");

            if (transaction.equalsIgnoreCase("income")) {
                updateAggregates("remove", new Income(
                    rs.getDouble("Amount"), rs.getString("Account"),
                    rs.getString("Date_Added"), rs.getString("Recurrence"),
                    rs.getString("Start_Date"), rs.getString("End_Date"),
                    rs.getString("Description")
                ));
            } 
            else if (transaction.equalsIgnoreCase("expense")) {
                updateAggregates("remove", new Expense(
                    rs.getDouble("Amount"), rs.getString("Category"),
                    rs.getString("Account"), rs.getString("Date_Added"),
                    rs.getString("Recurrence"), rs.getString("Start_Date"),
                    rs.getString("End_Date"), rs.getString("Description")
                ));
            } 
            else if (transaction.equalsIgnoreCase("transfer")) {
                updateAggregates("remove", new Transfer(
                    rs.getDouble("Amount"), rs.getString("Source"),
                    rs.getString("Destination"), rs.getString("Date_Added"),
                    rs.getString("Recurrence"), rs.getString("Start_Date"),
                    rs.getString("End_Date"), rs.getDouble("Transaction_Fee"), 
                    rs.getString("Description")
                ));
            }
            deleteStmt.setInt(1, transactionID);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Transaction with ID " + transactionID + " successfully deleted from " + tableName + " table.");
            } else {
                System.err.println("Transaction with ID " + transactionID + " not found in " + tableName + " table.");
            }
        } catch (SQLException e) {
            System.err.println("Database error while deleting transaction: " + e.getMessage());
        }
    }

    

    /*
     * ENDS A RECURRENCE TRANSACTION
     */
    public void endRecurring(String transaction, int transactionID) {
        String tableName = validateTransactionName(transaction);
    
        String updateQuery = "UPDATE " + tableName + " SET End_Date = ? WHERE ID = ?";
        
        try (Connection db = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement updateStmt = db.prepareStatement(updateQuery)) {
                updateStmt.setString(1, LocalDate.now().toString());
                updateStmt.setInt(2, transactionID);
                int rowsUpdated = updateStmt.executeUpdate();
    
                if (rowsUpdated > 0) {
                    System.out.println("Recurring transaction with ID " + transactionID + " has been successfully ended.");
                } else {
                    System.err.println("Transaction with ID " + transactionID + " cannot be found or has already been ended.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error while ending recurrence transaction: " + e.getMessage());
        }
    }

    private void updateAggregates(String key, Income income) {
        String[] keys = {"add", "remove"};
        Double amount = income.getTotalAmount();
        String account = income.getAccount();

        if(key.equals(keys[0])){
            accountBalances.put(account, accountBalances.get(account) + amount);
            totalIncome += amount;
        } else if(key.equals(keys[1])){
            accountBalances.put(account, accountBalances.get(account) - amount);
            totalIncome -= amount;
        }
    }
    private void updateAggregates(String key, Expense expense) {
        String[] keys = {"add", "remove"};
        Double amount = expense.getTotalAmount();
        String category = expense.getCategory();
        String account = expense.getAccount();

        if(key.equals(keys[0])){
            accountBalances.put(account, accountBalances.get(account) - amount);
            expensePerCategory.put(category, expensePerCategory.get(category) + amount);
            totalExpense += amount;
        } else if(key.equals(keys[1])){
            accountBalances.put(account, accountBalances.get(account) + amount);
            expensePerCategory.put(category, expensePerCategory.get(category) - amount);
            totalExpense -= amount;
        }
    }
    private void updateAggregates(String key, Transfer transfer) {
        String[] keys = {"add", "remove"};
        String fromAccount = transfer.getAccount();
        String toAccount = transfer.getToAccount();
        Double amount = transfer.getTotalAmount();
        Double transactionFee = transfer.getTransactionFee();
        
        if(key.equals(keys[0])){
            accountBalances.put(fromAccount, accountBalances.get(fromAccount) - amount - transactionFee);
            accountBalances.put(toAccount, accountBalances.get(toAccount) + amount);
        } else if(key.equals(keys[1])){
            accountBalances.put(fromAccount, accountBalances.get(fromAccount) + amount + transactionFee);
            accountBalances.put(toAccount, accountBalances.get(toAccount) - amount);
        }
    }

    /*
     * Loading all data from the database and storing them to arraylists
     */
    private ArrayList<Income> getIncomeData(Connection db) {
        ArrayList<Income> incomeList = new ArrayList<>();
        String query = "SELECT Amount, Account, Date_Added, Recurrence, Start_Date, End_Date, Description FROM incomes;";

        if(!tableExist(db, "incomes"))
            return incomeList;
        
        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Double amount = rs.getDouble("Amount");
                String account = rs.getString("Account");
                String dateAdded = rs.getString("Date_Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start_Date");
                String recurrenceEndDate = rs.getString("End_Date");
                String description = rs.getString("Description");
                incomeList.add(new Income(amount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description));
            }
        } catch (SQLException e) {}
        return incomeList;
    }
    private ArrayList<Expense> getExpenseData(Connection db) {
        ArrayList<Expense> expenseList = new ArrayList<>();
        String query = "SELECT Amount, Category, Account, Date_Added, Recurrence, Start_Date, End_Date, Description FROM expenses;";

        if(!tableExist(db, "expenses"))
            return expenseList;

        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Double amount = rs.getDouble("Amount");
                String category = rs.getString("Category");
                String account = rs.getString("Account");
                String dateAdded = rs.getString("Date_Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start_Date");
                String recurrenceEndDate = rs.getString("End_Date");
                String description = rs.getString("Description");
                expenseList.add(new Expense(amount, category, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description));
            }
        } catch (SQLException e) {
            System.err.println("Database error (Expense data): " + e.getMessage());
        }
        return expenseList;
    }
    private ArrayList<Transfer> getTransferData(Connection db) {
        ArrayList<Transfer> transferList = new ArrayList<>();
        String query = "SELECT Amount, Source, Destination, Date_Added, Recurrence, Start_Date, End_Date, Description FROM transfers;";

        if(!tableExist(db, "transfers"))
            return transferList;

        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Double amount = rs.getDouble("Amount");
                String fromAccount = rs.getString("Source");
                String toAccount = rs.getString("Destination");
                String dateAdded = rs.getString("Date_Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start_Date");
                String recurrenceEndDate = rs.getString("End_Date");
                Double transactionFee = rs.getDouble("Transaction_Fee");
                String description = rs.getString("Description");
                transferList.add(new Transfer(amount, fromAccount, toAccount, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, transactionFee, description));
            }
        } catch (SQLException e) {
            System.err.println("Database error (Transfer data): " + e.getMessage());
        }
        return transferList;
    }




    
    /*
     * COMPUTES THE AGGREGATE OF THE STORED DATA SUCH AS TOTAL INCOME & EXPENSE, EXPENSE PER CATEGORY, AND BALANCE PER ACCOUNT
     */
    private void getAggregates(ArrayList<Income> incomes, ArrayList<Expense> expenses, ArrayList<Transfer> transfers) {
        accountBalances.put("Bank", 0.0);
        accountBalances.put("Cash", 0.0);
        accountBalances.put("Digital Wallets", 0.0);
        accountBalances.put("Credit Card", 0.0);
        expensePerCategory.put("Food & Dining", 0.0);
        expensePerCategory.put("Leisure & Shopping", 0.0);
        expensePerCategory.put("Transportation", 0.0);
        expensePerCategory.put("Household", 0.0);
        expensePerCategory.put("Family & Education", 0.0);
        expensePerCategory.put("Health & Wellness", 0.0);
        expensePerCategory.put("Other", 0.0);
        totalIncome = 0.0;
        totalExpense = 0.0;
    
        for (Income income : incomes) {
            updateAggregates("add", income);
        }
        for (Expense expense : expenses) {
            updateAggregates("add", expense);
        }
        for (Transfer transfer : transfers) {
            updateAggregates("add", transfer);
        }
    }

    /*
     * VALIDATES TRANSACTION TYPE
     */
    private String validateTransactionName(String transaction) {
        String tableName;
        switch (transaction.toLowerCase()) {
            case "income" -> tableName = "incomes";
            case "expense"-> tableName = "expenses";
            case "transfer"-> tableName = "transfers";
            default -> {
                throw new IllegalArgumentException("Invalid transaction type: " + transaction);
            }
        }
        return tableName;
    }
    /*
     * CHECKS IF TABLES EXISTS
     */
    private static boolean tableExist(Connection db, String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (PreparedStatement pstmt = db.prepareStatement(query)) {
            pstmt.setString(1, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }
}

