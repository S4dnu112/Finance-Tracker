package transactionModels;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;


public abstract class Transaction {
    static final List<String> RECUR_FREQUENCIES = List.of("daily", "weekly", "monthly", "yearly");
    public static final List<String> ACCOUNTS = List.of("Bank", "Cash", "Digital Wallets", "Credit Card");

    protected double baseAmount;
    protected String account;
    protected String dateAdded;
    protected String description = "N/A";
    protected String recurrence = "N/A";
    protected String recurrenceStartDate = "N/A";
    protected String recurrenceEndDate = "N/A";

    // Constructor
    public Transaction(double baseAmount, String account, String dateAdded, String recurrence, 
                        String recurrenceStartDate, String recurrenceEndDate, String description) 
    {
        setBaseAmount(baseAmount);
        setAccount(account);
        setDateAdded(dateAdded);
        setRecurrence(recurrence);
        setRecurrenceStartDate(recurrenceStartDate);
        setRecurrenceEndDate(recurrenceEndDate);
        setDescription(description);
    }
    public Transaction() {}

    public double getBaseAmount() {
        return baseAmount;
    }
    public String getAccount() {
        return account;
    }
    public String getDateAdded() {
        return dateAdded;
    }
    public String getRecurrence() {
        return recurrence;
    }
    public String getRecurrenceStartDate() {
        return recurrenceStartDate;
    }
    public String getRecurrenceEndDate() {
        return recurrenceEndDate;
    }
    public String getDescription() {
        return description;
    }


    public void setBaseAmount(double baseAmount) {
        if (baseAmount <= 0)
            throw new IllegalArgumentException("Amount must be greater than zero.");
        this.baseAmount = baseAmount;
    }
    public void setAccount(String account) {
        if (!ACCOUNTS.contains(account))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + 
            String.join(", ", ACCOUNTS));
        this.account = account;
    }
    public void setDateAdded(String dateAdded) {
        validateDate(dateAdded);
        this.dateAdded = dateAdded;
    }
    public void setRecurrence(String recurrence) {
        if (!"N/A".equals(recurrence) && !RECUR_FREQUENCIES.contains(recurrence))
            throw new IllegalArgumentException("Invalid recurrence frequency. Allowed values: " + 
            String.join(", ", RECUR_FREQUENCIES));
        this.recurrence = recurrence;
    }
    public void setRecurrenceStartDate(String recurrenceStartDate) {
        if (!"N/A".equals(recurrenceStartDate))
            validateDate(recurrenceStartDate);
        this.recurrenceStartDate = recurrenceStartDate;
    }
    public void setRecurrenceEndDate(String recurrenceEndDate) {
        if (!"N/A".equals(recurrenceEndDate))
            validateDate(recurrenceEndDate);
        this.recurrenceEndDate = recurrenceEndDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Validate Date
    protected void validateDate(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected 'YYYY-MM-DD'.");
        }
    }
    // compute total amount including recurrences
    public double getTotalAmount() {
        if (recurrence.equals("N/A"))
            return baseAmount;

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        LocalDate start = this.recurrenceStartDate.equals("N/A") ?  LocalDate.parse(this.dateAdded): LocalDate.parse(this.recurrenceStartDate);
        LocalDate end = this.recurrenceEndDate.equals("N/A") ? LocalDate.now() : LocalDate.parse(this.recurrenceEndDate);

        switch (recurrence) {
            case "daily":
                return (ChronoUnit.DAYS.between(start, end) + 1) * this.baseAmount;
            case "weekly":
                return (ChronoUnit.DAYS.between(start, end) / 7 + 1) * this.baseAmount;
            case "monthly":
                long monthsBetween = ChronoUnit.MONTHS.between(start, end);
                if (daysInMonth[end.getMonthValue() - 1] == end.getDayOfMonth() && 
                        end.getDayOfMonth() < start.getDayOfMonth()) {
                    monthsBetween++;
                }
                return (monthsBetween + 1) * this.baseAmount;
            case "yearly":
                return (ChronoUnit.YEARS.between(start, end) + 1) * this.baseAmount;
            default:
                throw new IllegalArgumentException(
                    "Invalid recurrence frequency. Allowed values are: daily, weekly, monthly, yearly.");
        }
    }
}
