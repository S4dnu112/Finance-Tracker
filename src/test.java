
import backend.FinanceBackend;
import transactionModels.Expense;
import transactionModels.Income;
import transactionModels.Transfer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        FinanceBackend fb = new FinanceBackend();
        HashMap<String, Double> accountBalances = fb.getAccountBalances();
        
        System.out.println(fb.getTotalIncome());

        Transfer transfer = new Transfer(1000, "Bank", "Cash", LocalDate.now().toString(), "N/A", "N/A", "N/A", 50, "N/A");
        fb.save(transfer);

        

        for (Map.Entry<String, Double> entry : accountBalances.entrySet()) {
            System.out.println("Account: " + entry.getKey() + ", Balance: " + entry.getValue());
        }

    }
    
}