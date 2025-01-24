import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        String date = "2023-10-10"; // Example date
        LocalDate parsedDate = LocalDate.parse(LocalDate.now().toString());
        System.out.println(parsedDate);
    }
}
