import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String DATA_FILE = "expenses.txt";

    private static Map<String, List<Expense>> userExpenses = new HashMap<>();
    private static Map<String, Double> categoryTotals = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username;

        System.out.print("Enter your username: ");
        username = scanner.nextLine();

        loadExpenseData(username);

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addExpense(username, scanner);
                    break;
                case 2:
                    listExpenses(username);
                    break;
                case 3:
                    displayCategoryTotals(username);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        saveExpenseData(username);
    }

    private static void displayMenu() {
        System.out.println("\nExpense Tracker Menu:");
        System.out.println("1. Add Expense");
        System.out.println("2. List Expenses");
        System.out.println("3. View Category Totals");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addExpense(String username, Scanner scanner) {
        System.out.print("Enter expense date (MM/dd/yyyy): ");
        String date = scanner.nextLine();
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        Expense expense = new Expense(date, category, amount);
        userExpenses.computeIfAbsent(username, k -> new ArrayList<>()).add(expense);
        updateCategoryTotal(username, category, amount);
    }

    private static void listExpenses(String username) {
        List<Expense> expenses = userExpenses.get(username);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private static void displayCategoryTotals(String username) {
        System.out.println("\nCategory Totals:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private static void loadExpenseData(String username) {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4 && parts[0].equals(username)) {
                        Expense expense = new Expense(parts[1], parts[2], Double.parseDouble(parts[3]));
                        userExpenses.computeIfAbsent(username, k -> new ArrayList<>()).add(expense);
                        updateCategoryTotal(username, parts[2], Double.parseDouble(parts[3]));
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error loading expense data: " + e.getMessage());
        }
    }

    private static void saveExpenseData(String username) {
        try {
            FileWriter writer = new FileWriter(DATA_FILE);
            for (Expense expense : userExpenses.get(username)) {
                writer.write(username + "," + expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving expense data: " + e.getMessage());
        }
    }

    private static void updateCategoryTotal(String username, String category, double amount) {
        categoryTotals.merge(category, amount, Double::sum);
    }
}

class Expense {
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }
}