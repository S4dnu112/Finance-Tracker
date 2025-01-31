package transactionModels;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transfer extends Transaction{
    private String toAccount;
    private double transactionFee;
    private double totalTransferAmount;

    public Transfer(double baseAmount, String account, String toAccount, String dateAdded, String recurrence, 
                    String recurrenceStartDate, String recurrenceEndDate, double transactionFee, String description) {
        super(baseAmount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description);
        setToAccount(toAccount);
        setTransactionFee(transactionFee);
        setTotalTransferAmount(baseAmount, transactionFee);
    }
    public String getToAccount() { 
        return this.toAccount;
    }
    public double getTransactionFee(){
        return this.transactionFee;
    }
    public double getTotalTransferAmount() {
        return this.totalTransferAmount;
    }
    public void setToAccount(String toAccount) {
        if (!ACCOUNTS.contains(toAccount))
            throw new IllegalArgumentException("Please Select an Account.");
        this.toAccount = toAccount;
    }
    public void setTransactionFee(double transactionFee){
        if(transactionFee<0){
            throw new IllegalArgumentException("Transaction fee cannot be negative.");
        } else {
            this.transactionFee = transactionFee;
        }
    }
    private void setTotalTransferAmount(double baseAmount, double transactionFee) {
        this.totalTransferAmount = baseAmount + transactionFee;
    }
    public double getTotalAmountWithFee() {
        if (recurrence.equals("N/A"))
            return totalTransferAmount;

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        LocalDate start = this.recurrenceStartDate.equals("N/A") ?  LocalDate.parse(this.dateAdded): LocalDate.parse(this.recurrenceStartDate);
        LocalDate end = this.recurrenceEndDate.equals("N/A") ? LocalDate.now() : LocalDate.parse(this.recurrenceEndDate);

        if (start.isAfter(end))
            throw new IllegalArgumentException("Start date is after end date");
            
        switch (recurrence) {
            case "daily":
                return (ChronoUnit.DAYS.between(start, end) + 1) * this.totalTransferAmount;
            case "weekly":
                return (ChronoUnit.DAYS.between(start, end) / 7 + 1) * this.totalTransferAmount;
            case "monthly":
                long monthsBetween = ChronoUnit.MONTHS.between(start, end);
                if (daysInMonth[end.getMonthValue() - 1] == end.getDayOfMonth() && 
                        end.getDayOfMonth() < start.getDayOfMonth()) {
                    monthsBetween++;
                }
                return (monthsBetween + 1) * this.totalTransferAmount;
            case "yearly":
                return (ChronoUnit.YEARS.between(start, end) + 1) * this.totalTransferAmount;
            default:
                throw new IllegalArgumentException(
                    "Invalid recurrence frequency. Allowed values are: daily, weekly, monthly, yearly.");
        }
    }

}