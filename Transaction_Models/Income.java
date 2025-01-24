package Transaction_Models;

public class Income extends Transaction {
    public Income(double baseAmount, String account, String dateAdded, String recurrence, String recurrenceStartDate, String recurrenceEndDate, String description) {
        super(baseAmount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description);
    }
}
