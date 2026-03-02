public class Transaction {

    private int price;  //Avoid using double for currency, use int to represent cents
    public int getPrice() { return price;}

    private String description;
    public String getDescription() { return description;}

    private String date;
    public String getDate() { return date;}

    private TransactionType type;
    public TransactionType getType() { return type;}


    public Transaction(int price, String description, String date, TransactionType type) {
        this.price = price;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Transaction{price=$%.2f, description='%s', date='%s', type=%s}", 
                            convertToDollars(), this.description, this.date, this.type);
    }

    private double convertToDollars() {
        return this.price / 100.0;
    }
}