package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.ArrayList;

public class Expense {
    private static final ArrayList<String> RECUR_FREQUENCIES = new ArrayList<>(Arrays.asList("daily", "weekly", "monthly", "yearly"));
    private static final ArrayList<String> ACCOUNTS = new ArrayList<>(Arrays.asList("Bank", "Cash", "Digital Wallets", "Credit Card"));
    private static final ArrayList<String> CATEGORIES = new ArrayList<>(Arrays.asList(
            "Food", "Leisure & Shopping", "Transportation", "Household",
            "Family & Education", "Health & Wellness", "Other"));

    private int ID;
    private double amount;
    private String category;
    private String account;
    private String dateAdded;
    private String recurrence = "N/A";
    private String recurrenceStartDate = "N/A";
    private String recurrenceEndDate = "N/A";
    private String description = "N/A";

    // Constructor
    public Expense(int ID, double amount, String category, String account, String dateAdded, String description) {
        setID(ID);
        setAmount(amount);
        setCategory(category);
        setAccount(account);
        setDateAdded(dateAdded);
        this.description = description;
    }

    // Getters
    public int getID()                      { return ID; }
    public double getAmount()               { return amount; }
    public String getCategory()             { return category; }
    public String getAccount()              { return account; }
    public String getDateAdded()            { return dateAdded; }
    public String getRecurrence()           { return recurrence; }
    public String getRecurrenceStartDate()  { return recurrenceStartDate; }
    public String getRecurrenceEndDate()    { return recurrenceEndDate; }
    public String getDescription()          { return description; }

    // Setters
    public void setID(int ID) {
        if (ID < 1) 
            throw new IllegalArgumentException("ID must be greater than 0.");
        this.ID = ID;
    }
    public void setAmount(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        this.amount = amount;
    }
    public void setCategory(String category) {
        if (!CATEGORIES.contains(category))
            throw new IllegalArgumentException("Invalid category. Allowed categories: " + String.join(", ", CATEGORIES));
        this.category = category;
    }
    public void setAccount(String account) {
        if (!ACCOUNTS.contains(account))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(", ", ACCOUNTS));
        this.account = account;
    }
    public void setDateAdded(String dateAdded) {
        validateDate(dateAdded);
        this.dateAdded = dateAdded;
    }
    public void setRecurrence(String recurrence) {
        if (!"N/A".equals(recurrence) && !RECUR_FREQUENCIES.contains(recurrence))
            throw new IllegalArgumentException("Invalid recurrence frequency. Allowed values: " + String.join(", ", RECUR_FREQUENCIES));
        this.recurrence = recurrence;
    }
    public void setRecurrenceStartDate(String recurrenceStartDate) {
        if (!"N/A".equals(recurrence))
            validateDate(recurrenceStartDate);
        this.recurrenceStartDate = recurrenceStartDate;
    }
    public void setRecurrenceEndDate(String recurrenceEndDate) {
        if (!"N/A".equals(recurrence))
            validateDate(recurrenceEndDate);
        this.recurrenceEndDate = recurrenceEndDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Return the values as a comma-separated string
    public String values() {
        return ID + "," + amount + "," + category + "," + account + "," + dateAdded + "," + recurrence + "," + recurrenceStartDate + "," + recurrenceEndDate + "," + description;
    }

    // Return the keys as a comma-separated string
    public String keys() {
        return "ID,Amount,Category,Account,Date-added,Recurrence,Start-date,End-date,Description";
    }

    private void validateDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected 'YYYY-MM-DD'.");
        }
    }
}
