import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Transaction_Models.Expense;

public class FinanceBackend {


    public void writeToCSV(String filePath, Expense expense) {
        try {
            File file = new File(filePath);
            boolean isNewFile = !file.exists();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode
                if (isNewFile) {
                    writer.write(expense.keys());
                    writer.newLine();
                }
                writer.write(expense.values());
                writer.newLine();
            }
            System.out.println("Data successfully written to CSV file.");
        } 
        catch(IOException e) { e.printStackTrace(); }
    }
    
}
