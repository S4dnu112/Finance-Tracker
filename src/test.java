
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
        super("Finance Tracker");


        add(fb.createPieChart());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    public static void main(String[] args) {
        new test();
    }
    
}