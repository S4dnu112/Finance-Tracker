package src.Transaction_Models;

public class Transfer extends Transaction{
    private String toAccount;

    public Transfer(double baseAmount, String account, String toAccount, String dateAdded, String recurrence, String recurrenceStartDate, String recurrenceEndDate, String description) {
        super(baseAmount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description);
        setToAccount(toAccount);
    }
    public String getToAccount() { 
        return this.toAccount;
    }
    public void setToAccount(String toAccount) {
        if (!ACCOUNTS.contains(toAccount))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(", ", ACCOUNTS));
        this.toAccount = toAccount;
    }
}