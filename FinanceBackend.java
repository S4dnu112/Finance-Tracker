import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

import Transaction_Models.*;

public class FinanceBackend {

    private final String csvHeaderFormat = "ID,Amount,Category,Account,Date-added,Recurrence,Start-date,End-date,Description";

    /*
     * Stores expense csv data to an array of expense objects
     */
    public ArrayList<Expense> readExpenseData(String filePath) {
        ArrayList<Expense> expenseList = new ArrayList<>();
        String line;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            if (!header.equals(csvHeaderFormat))
                throw new IllegalArgumentException("CSV file is tampered: Header does not match the expected format.");

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length != 9)
                    throw new IllegalArgumentException("CSV file is tampered: A row is not composed of 9 columns");

                expenseList.add(new Expense(
                    Integer.parseInt(columns[0].trim()),   // id
                    Double.parseDouble(columns[1].trim()), // amount
                    columns[2].trim(),                     // category
                    columns[3].trim(),                     // account
                    columns[4].trim(),                     // dateAdded
                    columns[5].trim(),                     // recurrence
                    columns[6].trim(),                     // recurrenceStartDate
                    columns[7].trim(),                     // recurrenceEndDate
                    columns[8].trim()                      // description
                ));
            }
        } 
        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return expenseList;
    }

    /*
     * Returns an array containing the balances per account index 0,1,2,3 -> bank, cash, digital wallets, credit respectively
     */
    public HashMap<String, Double> computeAccountBalance(ArrayList<Expense> expenses, ArrayList<Income> incomes, ArrayList<Transfer> transfers) {
        HashMap<String, Double> accountBalance = new HashMap<>();
        accountBalance.put("Bank", 0.0);
        accountBalance.put("Cash", 0.0);
        accountBalance.put("Digital Wallets", 0.0);
        accountBalance.put("Credit Card", 0.0);
        
        for (Income income: incomes) { 
            accountBalance.put(
                income.getAccount(),
                accountBalance.get(income.getAccount()) + (
                    income.getRecurrence() != "N/A" ? computeRecurrence(income) : income.getAmount()
                )
            );
        }

        // for (Expense expense : expenses) {
        //     switch(expense.getAccount()) { 
        //         case "Bank" -> 
        //     }
        // }
    }

    public double computeRecurrence(Income income) {
        String[] FREQUENCIES = {"daily", "weekly", "monthly", "yearly"};
        String recurrence = income.getRecurrence();
        double amount = income.getAmount();

        LocalDate start = LocalDate.parse(income.getRecurrenceStartDate());
        LocalDate end = income.getRecurrenceStartDate().equals("N/A") ? LocalDate.now() : LocalDate.parse(income.getRecurrenceStartDate());

        if (!java.util.Arrays.asList(FREQUENCIES).contains(recurrence))
            throw new IllegalArgumentException("CSV file is tampered: Invalid recurrence frequency. Allowed values are: daily, weekly, monthly, yearly.");

        switch (recurrence) {
            case "daily":
                return ChronoUnit.DAYS.between(start, end) + 1 * amount;
            case "weekly":
                return (ChronoUnit.DAYS.between(start, end) / 7 + 1) * amount;
            case "monthly":
                int monthsPassed = (end.getYear() - start.getYear()) * 12 + (end.getMonthValue() - start.getMonthValue());
                if (end.getDayOfMonth() >= start.getDayOfMonth())
                    monthsPassed++;
                return monthsPassed * amount;

            case "yearly":
                int yearsPassed = end.getYear() - start.getYear();
                if (end.getMonthValue() < start.getMonthValue() || 
                   (end.getMonthValue() == start.getMonthValue() && end.getDayOfMonth() < start.getDayOfMonth())) {
                    yearsPassed--;
                }
                return (yearsPassed + 1) * amount;

            default:
                throw new IllegalArgumentException("Invalid recurrence frequency. Allowed values are: daily, weekly, monthly, yearly.");
        }
    }
    }


    
    // public void writeToCSV(String filePath, Expense expense) {
    //     try {
    //         File file = new File(filePath);
    //         boolean isNewFile = !file.exists();

    //         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode
    //             if (isNewFile) {
    //                 writer.write(expense.keys());
    //                 writer.newLine();
    //             }
    //             writer.write(expense.values());
    //             writer.newLine();
    //         }
    //         System.out.println("Data successfully written to CSV file.");
    //     } 
    //     catch(IOException e) {
    //         e.printStackTrace(); 
    //     }
    // }

    // public int lastId(String filePath) throws IOException, NumberFormatException {
    //     try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    //         String line;
    //         int id = 0;

    //         while ((line = reader.readLine()) != null)
    //             id = Integer.parseInt(line.split(",")[0]);
    //         return id;
    //     } 
    //     catch (FileNotFoundException e) {
    //         return 0;
    //     }
    // }


}
