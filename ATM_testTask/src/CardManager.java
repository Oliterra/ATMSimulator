import java.time.LocalDate;
import java.util.ArrayList;

public class CardManager {
    private static CardManager cardManager;
    private FileWorker fileWorker = new FileWorker();
    ArrayList<Card> cardsList = new ArrayList<>();
    ArrayList<BlockedCard> blockedCardsList = new ArrayList<>();

    private CardManager() {
    }

    public static CardManager getCardManager() {
        if (cardManager == null) {
            cardManager = new CardManager();
        }
        return cardManager;
    }

    public ArrayList<Card> createCardsList() {
        String[] records = fileWorker.readAccountInfo().split("\n");
        String[] record;
        for (String info : records) {
            record = info.trim().split("\s");
            cardsList.add(new Card(record[2], record[3], Integer.parseInt(record[4])));
        }
        return cardsList;
    }

    public ArrayList<BlockedCard> createBlockedCardsList() {
        String[] records = fileWorker.readBlockedCardsInfo().split("\n");
        String[] record;
        for (String info : records) {
            record = info.trim().split("\s");
            blockedCardsList.add(new BlockedCard(record[0], LocalDate.parse(record[1])));
        }
        return blockedCardsList;
    }

    public Card createCard() {
        return new Card(cardManager.generateNewCardNumber(), cardManager.generateNewPassword(), 0);
    }

    public void createBlockedCard(Card card) {
        BlockedCard blockedCard = new BlockedCard(card.getNumber(), LocalDate.now());
        blockedCardsList.add(blockedCard);
    }

    public boolean isCardBlocked(Card card) {
        boolean isCardBlocked = false;
        for (BlockedCard blockedCard : blockedCardsList) {
            if (blockedCard.getCardNumber().equals(card.getNumber())) {
                isCardBlocked = true;
                break;
            }
        }
        return isCardBlocked;
    }

    public void blockCard(Card card) {
        if (!isCardBlocked(card)) {
            createBlockedCard(card);
        }
    }

    private String generateNewCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(999 + (int) (Math.random() * ((9999 - 999) + 1)));
            if (i == 3) break;
            sb.append("-");
        }
        return sb.toString();
    }

    private String generateNewPassword() {
        ArrayList<String> result = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < 8; i++) {
            int switcher = (int) (Math.random() * 3);
            if (switcher == 0) {
                temp = getRandomInt();
            } else {
                temp = getRandomChar();
            }
            result.add(temp);
        }
        StringBuilder sb = new StringBuilder();
        for (String x : result) {
            sb.append(x);
        }
        return sb.toString();
    }

    private static String getRandomInt() {
        String result = String.valueOf((int) (Math.random() * 10));
        return result;
    }

    private static String getRandomChar() {
        String[] character = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String result = character[(int) (Math.random() * character.length)];
        if (Math.random() < 0.5) {
            return result.toUpperCase();
        }
        return result;
    }
}
