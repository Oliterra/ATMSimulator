public class Card {
    private String number;
    private int balance;
    private String password;

    public Card(String number, String password, int balance) {
        this.number = number;
        this.password = password;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public int getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return number + "\s" + password + "\s" + balance + "\s";
    }
}
