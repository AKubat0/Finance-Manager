
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        FinanceManager fm = new FinanceManager();

        while (true) { 
            
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Display Total");
            System.out.println("4. Display History");
            System.out.println("5. Display Expense Breakdown");
            System.out.println("6. Clear All Transactions");
            System.out.println("7. Delete a Transaction by number");
            System.out.println("8. Exit");

            int choice = 0;
            boolean validInput  = false;

            while (!validInput){

                try {
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
            
            switch(choice){
                case 1: 
                    double incomeAmount = 0;
                    boolean validIncome = false;
                    while (!validIncome) {
                        try {
                            System.out.print("Enter amount (in dollars): ");
                            incomeAmount = scanner.nextDouble();
                            if (incomeAmount <= 0) {
                                System.out.println("Amount cannot be negative or zero. Please try again.");
                                continue;
                            }
                            validIncome = true;
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number for the amount.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }

                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter description: ");
                    String incomeDesc = scanner.nextLine();
                    
                    String incomeDate = "";
                    boolean validIncomeDate = false;
                    while (!validIncomeDate){
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        incomeDate = scanner.nextLine();
                        try {
                            LocalDate.parse(incomeDate);
                            validIncomeDate = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
                        }
                    }
                    fm.addTransaction(new Transaction((int) Math.round(incomeAmount * 100), incomeDesc, incomeDate, TransactionType.INCOME));
                    break;
                
                case 2:
                    double expenseAmount = 0;
                    boolean validExpense = false;
                    while (!validExpense) {
                        try {
                            System.out.print("Enter amount (in dollars): ");
                            expenseAmount = scanner.nextDouble();
                            if (expenseAmount <= 0) {
                                System.out.println("Amount cannot be negative or zero. Please try again.");
                                continue;
                            }
                            validExpense = true;
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number for the amount.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter description: ");
                    String expenseDesc = scanner.nextLine();
                    String expenseDate = "";
                    boolean validExpenseDate = false;
                    while (!validExpenseDate){
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        expenseDate = scanner.nextLine();
                        try {
                            LocalDate.parse(expenseDate);
                            validExpenseDate = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
                        }
                    }
                    fm.addTransaction(new Transaction((int) Math.round(expenseAmount * 100), expenseDesc, expenseDate, TransactionType.EXPENSE));
                    break;

                case 3:
                    fm.displayTotal();
                    break;
                
                case 4:
                    fm.displayHistory();
                    break;
                
                case 5:
                    fm.displayExpenseBreakdown();
                    break;

                case 6:
                    while (true) { 
                        System.out.println("Are you sure you want to clear all transactions? This action cannot be undone. (yes/no)");
                        String decision = scanner.nextLine();
                        if (decision.equalsIgnoreCase("yes")) {
                            fm.clearAllTransactions();
                            break;
                        } else if (decision.equalsIgnoreCase("no")) {
                            break;
                        } else {
                            System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                        }
                    }
                    break;

                case 7: 
                    fm.displayHistory();
                    int indexToDelete = -1;
                    while (true) { 
                        try {
                            System.out.print("Enter the number of the transaction to delete (or 0 to cancel): ");
                            indexToDelete = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            if (indexToDelete == 0) {
                                break; // Cancel deletion
                            }
                            if (fm.clearTransaction(indexToDelete - 1)) {
                                break; // Successfully deleted, exit loop
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid transaction number.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }
                    break;

                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    fm.saveTransactions();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }
}
