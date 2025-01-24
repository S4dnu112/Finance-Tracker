package Transaction_Models;
import java.util.List;

public class Expense extends Transaction {
    private String category;
    static final List<String> CATEGORIES = List.of(
        "Food & Dining", "Leisure & Shopping", "Transportation", "Household",
        "Family & Education", "Health & Wellness", "Other"
    );
    public Expense(double baseAmount, String category, String account, String dateAdded, String recurrence, String recurrenceStartDate, String recurrenceEndDate, String description) {
        super(baseAmount, account, dateAdded, recurrence, recurrenceStartDate, recurrenceEndDate, description);
        setCategory(category);
    }

    public String getCategory() {
        return category; 
    }
    public void setCategory(String category) {
        if (!CATEGORIES.contains(category))
            throw new IllegalArgumentException("Invalid category. Allowed categories: " + String.join(", ", CATEGORIES));
        this.category = category;
    }
}
