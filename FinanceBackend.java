import java.util.HashMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

import Transaction_Models.*;

public class FinanceBackend {
    // no setters for encapsulation
    private HashMap<String, Double> accountBalances = new HashMap<>();
    private HashMap<String, Double> expensePerCategory = new HashMap<>();
    private double totalIncome;
    private double totalExpense;
    private final String DB_URL = "jdbc:sqlite:transactions.db";
    

    // constructing the backend will automatically load the aggregate data from the database
    public FinanceBackend() {
        try(Connection db = DriverManager.getConnection(DB_URL)) {
            getAggregates(getIncomeData(db), getExpenseData(db), getTransferData(db));
        } catch(SQLException e) {
            System.err.println("Database error (Transfer): " + e.getMessage());
        }
    }   


    // Finance-tracker attributes
    public double getTotalIncome() {
        return totalIncome;
    }
    public double getTotalExpense() {
        return totalExpense;
    }
    public HashMap<String, Double> getAccountBalances() {
        return accountBalances;
    }
    private HashMap<String, Double> getExpensePerCategory() {
        return expensePerCategory;
    }


    
    
    /*
     * Loading all data from the database and storing them to arraylists
     */
    private ArrayList<Income> getIncomeData(Connection db) {
        ArrayList<Income> incomeList = new ArrayList<>();
        String query = "SELECT Amount, Account, Date-Added, Recurrence, Start-Date, End-Date, Description FROM incomes;";

        if(!tableExist(db, "incomes"))
            return incomeList;
        
        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                double amount = rs.getDouble("Amount");
                String account = rs.getString("Account");
                String dateAdded = rs.getString("Date-Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start-Date");
                String recurrenceEndDate = rs.getString("End-Date");
                String description = rs.getString("Description");
                incomeList.add(new Income(amount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description));
            }
        } catch (SQLException e) {}
        return incomeList;
    }


    private ArrayList<Expense> getExpenseData(Connection db) {
        ArrayList<Expense> expenseList = new ArrayList<>();
        String query = "SELECT Amount, Category, Account, Date-Added, Recurrence, Start-Date, End-Date, Description FROM expenses;";

        if(!tableExist(db, "expenses"))
            return expenseList;

        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                double amount = rs.getDouble("Amount");
                String category = rs.getString("Category");
                String account = rs.getString("Account");
                String dateAdded = rs.getString("Date-Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start-Date");
                String recurrenceEndDate = rs.getString("End-Date");
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
        String query = "SELECT Amount, From, To, Date-Added, Recurrence, Start-Date, End-Date, Description FROM transfers;";

        if(!tableExist(db, "transfers"))
            return transferList;

        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                double amount = rs.getDouble("Amount");
                String fromAccount = rs.getString("FromAccount");
                String toAccount = rs.getString("ToAccount");
                String dateAdded = rs.getString("Date-Added");
                String recurrence = rs.getString("Recurrence");
                String recurrenceStartDate = rs.getString("Start-Date");
                String recurrenceEndDate = rs.getString("End-Date");
                String description = rs.getString("Description");
                transferList.add(new Transfer(amount, fromAccount, toAccount, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description));
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
    
        for (Income income : incomes) {
            String account = income.getAccount();
            double amount = income.getTotalAmount();
            accountBalances.put(account, accountBalances.get(account) + amount);
            totalIncome += amount;
        }
        for (Expense expense : expenses) {
            String account = expense.getAccount();
            String category = expense.getCategory();
            double amount = expense.getTotalAmount();

            accountBalances.put(account, accountBalances.get(account) - amount);
            expensePerCategory.put(category, expensePerCategory.get(category) + amount);
            totalExpense += amount;
        }
        for (Transfer transfer : transfers) {
            String fromAccount = transfer.getAccount();
            String toAccount = transfer.getToAccount();
            double amount = transfer.getTotalAmount();
            accountBalances.put(fromAccount, accountBalances.get(fromAccount) - amount);
            accountBalances.put(toAccount, accountBalances.get(toAccount) + amount);
        }
    }

    /*
     * UPDATES AGGREGATES BASED ON NEWLY ADDED DATA
     */
    public void updateAggregates(String key, Income income) {
        String[] keys = {"add", "remove"};
        // to be implemented
    }
    public void updateAggregates(String key, Expense expense) {
        String[] keys = {"add", "remove"};
        // to be implemented
    }
    public void updateAggregates(String key, Transfer transfer) {
        String[] keys = {"add", "remove"};
        // to be implemented
    }

    /*
     * SAVING DATA TO THE SQLITE DATABASE
     */
    public void save(Income income) {
        String createQuery = """
            CREATE TABLE IF NOT EXISTS incomes (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                Account TEXT NOT NULL,
                Date-Added TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                Start-Date TEXT NOT NULL,
                End-Date TEXT NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO incomes (Amount, Account, Date-Added, Recurrence, Start-Date, End-Date, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = conn.createStatement(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                createStmt.execute(createQuery);
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
            System.err.println("Database error (Income): " + e.getMessage());
        }
    }
    public void save(Expense expense) {
        String createQuery = """
            CREATE TABLE IF NOT EXISTS expenses (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                Category TEXT NOT NULL,
                Account TEXT NOT NULL,
                DateAdded TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                StartDate TEXT NOT NULL,
                EndDate TEXT NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO expenses (Amount, Category, Account, DateAdded, Recurrence, StartDate, EndDate, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = conn.createStatement(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                createStmt.execute(createQuery);
                insertStmt.setDouble(1, expense.getBaseAmount());
                insertStmt.setString(2, expense.getCategory());
                insertStmt.setString(3, expense.getAccount());
                insertStmt.setString(4, expense.getDateAdded());
                insertStmt.setString(5, expense.getRecurrence());
                insertStmt.setString(6, expense.getRecurrenceStartDate());
                insertStmt.setString(7, expense.getRecurrenceEndDate());
                insertStmt.setString(8, expense.getDescription());
                insertStmt.executeUpdate();
            } 
        } catch (SQLException e) {
            System.err.println("Database error (Expense): " + e.getMessage());
        }   
    }
    public void save(Transfer transfer) {
        String createQuery = """
            CREATE TABLE IF NOT EXISTS transfers (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                Amount REAL NOT NULL,
                From TEXT NOT NULL,
                To TEXT NOT NULL,
                Date-Added TEXT NOT NULL,
                Recurrence TEXT NOT NULL,
                Start-Date TEXT NOT NULL,
                End-Date TEXT NOT NULL,
                Description TEXT NOT NULL
            );
        """;
        String insertQuery = """
            INSERT INTO transfers (Amount, From, To, Date-Added, Recurrence, Start-Date, End-Date, Description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement createStmt = conn.createStatement(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                createStmt.execute(createQuery);
                insertStmt.setDouble(1, transfer.getBaseAmount());
                insertStmt.setString(2, transfer.getAccount());
                insertStmt.setString(3, transfer.getToAccount());
                insertStmt.setString(4, transfer.getDateAdded());
                insertStmt.setString(5, transfer.getRecurrence());
                insertStmt.setString(6, transfer.getRecurrenceStartDate());
                insertStmt.setString(7, transfer.getRecurrenceEndDate());
                insertStmt.setString(8, transfer.getDescription());
                insertStmt.execute();
            } 
        } catch(SQLException e) {
            System.err.println("Database error (Transfer): " + e.getMessage());
        }
    }
    
    /*
     * CHECKS IF TABLES EXISTS
     */
    private static boolean tableExist(Connection conn, String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, tableName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }



    // test method
    public static void main(String[] args) {

        // SAMPLE EXECUTION
        FinanceBackend backend = new FinanceBackend();

        backend.save(new Income(50000, "Bank", LocalDate.now().toString(), "monthly", "N/A", "N/A", "salary"));
        backend.save(new Expense(95,  "Food & Dining","Cash", LocalDate.now().toString(), "daily", "N/A", "N/A", "Daily Food Expense"));
        backend.save(new Transfer(1000, "Bank", "Cash", LocalDate.now().toString(), "daily", "N/A", "N/A", "Transfer from bank to cash"));








    
    }
}

