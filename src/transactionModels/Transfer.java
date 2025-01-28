package transactionModels;

public class Transfer extends Transaction{
    private String toAccount;
    private double transactionFee;

    public Transfer(double baseAmount, String account, String toAccount, String dateAdded, String recurrence, 
                    String recurrenceStartDate, String recurrenceEndDate, double transactionFee, String description) {
        super(baseAmount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description);
        setToAccount(toAccount);
        setTransactionFee(transactionFee);
    }
    public String getToAccount() { 
        return this.toAccount;
    }
    public void setToAccount(String toAccount) {
        if (!ACCOUNTS.contains(toAccount))
            throw new IllegalArgumentException("Invalid account. Allowed accounts: " + String.join(", ", ACCOUNTS));
        this.toAccount = toAccount;
    }
    public double getTransactionFee(){
        return this.transactionFee;
    }
    public void setTransactionFee(double transactionFee){
        if(transactionFee<0){
            throw new IllegalArgumentException("Transaction fee cannot be negative.");
        } else {
            this.transactionFee = transactionFee;
        }
    }
}