
import backend.FinanceBackend;
import transactionModels.Expense;
import transactionModels.Income;
import transactionModels.Transfer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class test extends JFrame{

    FinanceBackend fb = new FinanceBackend();

    public test() {
        super("test");
        //tests here
    }
    public static void main(String[] args) {
        new test();
    }
    
}