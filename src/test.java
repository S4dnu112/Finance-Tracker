package src;

import src.Transaction_Models.Income;
import src.BackEnd.FinanceBackend;
import src.Transaction_Models.Expense;
import src.Transaction_Models.Transfer;

public class test {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        // Code to measure
        {
            FinanceBackend fb = new FinanceBackend();
            System.out.println(fb.getTotalIncome());

            Income income = new Income(10, "Bank", "2021-01-01", "daily", "N/A", "N/A", "test");
            fb.save(income);

            System.out.println(fb.getTotalIncome());
        }


        // Record the end time
        long endTime = System.nanoTime();

        // Calculate the time difference in nanoseconds, milliseconds, or seconds
        long durationInNano = endTime - startTime;
        double durationInMillis = durationInNano / 1_000_000.0;
        double durationInSeconds = durationInNano / 1_000_000_000.0;

        // Print the result
        System.out.println("Execution time in nanoseconds: " + durationInNano);
        System.out.println("Execution time in milliseconds: " + durationInMillis);
        System.out.println("Execution time in seconds: " + durationInSeconds);
    }
    
}
