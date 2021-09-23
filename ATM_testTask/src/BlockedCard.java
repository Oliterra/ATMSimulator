import java.time.LocalDate;

public class BlockedCard {
    private String cardNumber;
    private LocalDate blockingTime;

    public BlockedCard(String cardNumber, LocalDate blockingTime) {
        this.cardNumber = cardNumber;
        this.blockingTime = blockingTime;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getBlockingTime() {
        return blockingTime;
    }

    @Override
    public String toString() {
        return cardNumber + "\s" + blockingTime;
    }
}
