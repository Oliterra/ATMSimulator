public class Account {
    private User user;
    private Card card;

    public Account(User user, Card card) {
        this.user = user;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() { return user + "\s" + card; }
}
