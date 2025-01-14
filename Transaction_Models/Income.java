package Transaction_Models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.ArrayList;

public class Income {
    private static final ArrayList<String> RECUR_FREQUENCIES = new ArrayList<>(Arrays.asList("daily", "weekly", "monthly", "yearly"));
    private static final ArrayList<String> ACCOUNTS = new ArrayList<>(Arrays.asList("Bank", "Cash", "Digital Wallets", "Credit Card"));

    private int ID;
    private double amount;
    private String account;
    private String dateAdded;
    private String description = "N/A";
    private String recurrence = "N/A";
    private String recurrenceStartDate = "N/A";
    private String recurrenceEndDate = "N/A";

    public Income(int ID, double amount, String account, String dateAdded, String description) {
        setID(ID);
        setAmount(amount);
        setAccount(account);
        setDateAdded(dateAdded);
        setDescription(description);
    }

    // Getters
    public int getID()                      { return ID; }
    public double getAmount()               { return amount; }
    public String getAccount()              { return account; }
    public String getDateAdded()            { return dateAdded; }
    public String getDescription()          { return description; }
    public String getRecurrence()           { return recurrence; }
    public String getRecurrenceStartDate()  { return recurrenceStartDate; }
    public String getRecurrenceEndDate()    { return recurrenceEndDate; }

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
    public void setAccount(String account) {
        if (!ACCOUNTS.contains(account))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(", ", ACCOUNTS));
        this.account = account;
    }
    public void setDescription(String description) {
        this.description = description != null ? description : "N/A";
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

    // Return the values as a comma-separated string
    public String values() {
        return ID + "," + amount + "," + account + "," + description + "," + dateAdded + "," + recurrence + "," + recurrenceStartDate + "," + recurrenceEndDate;
    }
    // Return the keys as a comma-separated string
    public String keys() {
        return "ID,Amount,Account,Description,Date-added,Recurrence,Start-date,End-date";
    }
    private void validateDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected 'YYYY-MM-DD'.");
        }
    }
}
