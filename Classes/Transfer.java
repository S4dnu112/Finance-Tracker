package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Transfer {
    private static final ArrayList<String> ACCOUNTS = new ArrayList<>(Arrays.asList("Bank", "Cash/Wallets", "Credit Card"));
    
    private int ID;
    private double amount;
    private String fromAccount;
    private String toAccount;
    private String dateAdded;
    private String description;

    //constructor
    public Transfer(int ID, double amount, String fromAccount, String toAccount, String dateAdded, String description){
        setID(ID);
        setAmount(amount);
        setFromAccount(fromAccount);
        setToAccount(toAccount);
        setDateAdded(dateAdded);
        this.description = description;
    }

    //getters
    public int getID() { return ID; }
    public double getAmount() { return amount; }
    public String getFromAccount() { return fromAccount; }
    public String getToAccount() { return toAccount; }
    public String getDateAdded() { return dateAdded; }
    public String getDescription() { return description; }

    //setters
    public void setID(int ID){
        if(ID < 1)
            throw new IllegalArgumentException("ID must be greater than zero.");
        this.ID = ID;
    }
    public void setAmount(double amount){
        if (amount <= 0) 
            throw new IllegalArgumentException("Amount must be greater than zero.");
        this.amount = amount;
    }
    public void setFromAccount(String fromAccount){
        if(!ACCOUNTS.contains(fromAccount))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(",", ACCOUNTS));
        this.fromAccount = fromAccount;
    }
    public void setToAccount(String toAccount){
        if(!ACCOUNTS.contains(toAccount))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(",", ACCOUNTS));
        this.toAccount = toAccount;
    }
    public void setDateAdded(String dateAdded){
        validateDate(dateAdded);
        this.dateAdded = dateAdded;
    }
    public void setDescription(String description){
        this.description = description;
    }

    //Return the values as a comma-separated string
    public String values(){
        return ID + "," amount + "," + fromAccount + "," + toAccount + "," + dateAdded + "," + description;
    }

    //Return the keys as a comma-separated string
    public String keys(){
        return "ID,Amount,From-account,To-account,Date-added,Description";
    }

    private void validateDate(String date){
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expeced 'YYYY-MM-DD'.");
        }
    }
}