import java.time.LocalDate;

import Transaction_Models.Expense;

public class test {
    public static void main(String[] args) {

        Expense expense = new Expense(1, 100, "Food", "Bank", LocalDate.now().toString(), "");
        FinanceBackend backend = new FinanceBackend();

        backend.writeToCSV("CSV_data/expense_data.csv", expense);
        
    }
}
