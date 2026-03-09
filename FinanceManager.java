
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;;


public class FinanceManager {
    
    private ArrayList<Transaction> transactions;
    public ArrayList<Transaction> getTransactions() { return this.transactions; }

    public FinanceManager() {
        this.transactions = new ArrayList<>();
        loadTransactions();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void displayTotal() {
        int total = calculateTotal();
        System.out.printf("Total Balance: $%.2f\n", total / 100.0);
    }

    private int calculateTotal(){

        int incomeTotal = 0;
        int expenseTotal = 0;

        for (Transaction t : transactions){

            if (t.getType() == TransactionType.INCOME) {
                incomeTotal += t.getPrice();
            } else if (t.getType() == TransactionType.EXPENSE) {
                expenseTotal += t.getPrice();
            }
        }
        return incomeTotal - expenseTotal;
    }

    public void saveTransactions(){

        try (FileWriter writer = new FileWriter("Transactions.csv")) {
            for (Transaction t : transactions) {
                writer.write(t.getType() + "," + t.getPrice() + "," + t.getDescription() + "," + t.getDate() + "\n");
            }
            System.out.println("Transactions saved to Transactions.csv"); 
        } catch (IOException e) {
            System.out.println("An error occurred while saving transactions: " + e.getMessage());
        }
    }

    public void loadTransactions(){

        try (Scanner scanner = new Scanner (new File("Transactions.csv"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    TransactionType type = TransactionType.valueOf(parts[0]);
                    int price = Integer.parseInt(parts[1]);
                    String description = parts[2];
                    String date = parts[3];
                    transactions.add(new Transaction(price, description, date, type));
                }
            }
            System.out.println("Transactions loaded from Transactions.csv");
        } catch (Exception e) {
            System.out.println("An error occurred while loading transactions: " + e.getMessage());
        }
    }

    public void clearAllTransactions(){
        this.transactions.clear();
        saveTransactions();
        System.out.println("All transactions cleared.");
    }

    public boolean clearTransaction(int index){

        if (index < 0 || index >= transactions.size()){
            System.out.println("Invalid index. No transaction removed.");
            return false;
        }
        transactions.remove(index);
        saveTransactions();
        System.out.println("Transaction at index " + index + " removed.");
        return true;
    }

    public void displayHistory(){

        if (transactions.isEmpty()){
            System.out.println("No transactions to display.");
            return;
        }

        System.out.println("\n--- Transaction History ---");

        this.transactions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i).toString()); 
        }

        System.out.println("---------------------------\n");
    }

    public void displayExpenseBreakdown(){

        HashMap<String,Integer> categoryTotals = new HashMap<>();

        for (Transaction t : transactions){
            if (t.getType() == TransactionType.EXPENSE){
                String category = t.getDescription();
                int amount = t.getPrice();
                if (categoryTotals.containsKey(category)){
                    categoryTotals.put(category, categoryTotals.get(category) + amount);
                } else {
                    categoryTotals.put(category, amount);
                }
            }
        }

        System.out.println("\n--- Expense Breakdown ---");
        // This special loop iterates through every Key-Value pair (Entry) in the map
        for (Map.Entry<String, Integer> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            int totalCents = entry.getValue();
            
            // Reusing your awesome String.format logic to convert cents back to dollars!
            System.out.printf("%s: $%.2f\n", category, totalCents / 100.0);
        }
        System.out.println("-------------------------\n");
    }
}
