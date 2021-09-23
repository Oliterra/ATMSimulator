import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static AccountManager accountManager;
    private FileWorker fileWorker = new FileWorker();
    private UserManager userManager = UserManager.getUserManager();
    private CardManager cardManager = CardManager.getCardManager();
    private HashMap<Card, User> accounts = new HashMap<>();

    private AccountManager() {
    }

    public static AccountManager getAccountManager() {
        if (accountManager == null) {
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public Map<Card, User> createAccountsList() {
        ArrayList<Card> cards = cardManager.createCardsList();
        ArrayList<User> users = userManager.createUsersList();
        if (cards.size() == users.size()) {
            for (int i = 0; i < cards.size(); i++) {
                accounts.put(cards.get(i), users.get(i));
            }
        }
        return accounts;
    }

    public ArrayList<BlockedCard> createBlockedCardsList() {
        return cardManager.createBlockedCardsList();
    }

    public void updateDB(Map<Card, User> accounts, ArrayList<BlockedCard> blockedCardList) {
        fileWorker.cleanUpAccountsList();
        fileWorker.cleanUpBlockedCardsList();
        for (Map.Entry<Card, User> account : accounts.entrySet()) {
            fileWorker.writeAccountInfo(new Account(account.getValue(), account.getKey()));
        }
        for (BlockedCard blockedCard : blockedCardList) {
            fileWorker.writeBlockedCardsInfo(new BlockedCard(blockedCard.getCardNumber(), blockedCard.getBlockingTime()));
        }
    }

    public User createUser(String firstName, String lastName) throws IOException {
        return userManager.createUser(firstName, lastName);
    }

    public boolean findUser(User user) {
        return userManager.usersList.contains(user);
    }

    public Account createAccount(User user) {
        Card newCard = cardManager.createCard();
        Account newAccount = new Account(user, newCard);
        accounts.put(newCard, user);
        return newAccount;
    }

    public Map<Integer, Card> getUserCards(User user) {
        Map<Integer, Card> userCards = new HashMap();
        int cardNumber = 1;
        if (accounts.containsValue(user)) {
            for (Map.Entry<Card, User> pair : accounts.entrySet()) {
                if (pair.getValue().equals(user)) {
                    userCards.put(cardNumber, pair.getKey());
                    cardNumber++;
                }
            }
        }
        return userCards;
    }

    public void showUserCardsList(Map<Integer, Card> userCards) {
        for (Map.Entry<Integer, Card> pair : userCards.entrySet()) {
            System.out.println(pair.getKey() + ". " + pair.getValue().getNumber());
        }
    }

    public Card chooseCard(Map<Integer, Card> userCards, int number) throws IOException {
        if (userCards.containsKey(number)) return userCards.get(number);
        else throw new IOException();
    }

    public void blockCard(Card card) {
        cardManager.blockCard(card);
        System.out.println("Your card " + card.getNumber() + " was blocked. Wait a day or call the bank.");
    }

    private boolean unblockUserCard(BlockedCard blockedCard) {
        LocalDate currentDate = LocalDate.now();
        LocalDate blockingDate = LocalDate.of(blockedCard.getBlockingTime().getYear(), blockedCard.getBlockingTime().getMonth(), blockedCard.getBlockingTime().getDayOfMonth());
        Period period = Period.between(currentDate, blockingDate);
        return (period.getDays() < 0) ? true : false;
    }

    public ArrayList<BlockedCard> unblockAllCards(ArrayList<BlockedCard> blockedCards) {
        ArrayList<BlockedCard> newBlockedCards = new ArrayList<>();
        for (BlockedCard card : blockedCards) {
            if (!unblockUserCard(card)) newBlockedCards.add(card);
        }
        cardManager.blockedCardsList = newBlockedCards;
        return newBlockedCards;
    }

    public ArrayList<BlockedCard> updateBlockedCards(ArrayList<BlockedCard> blockedCards) {
        return unblockAllCards(blockedCards);
    }

    public ArrayList<BlockedCard> getBlockedCards() {
        return cardManager.blockedCardsList;
    }

    public boolean isCardBlocked(Card card) {
        return (cardManager.isCardBlocked(card)) ? true : false;
    }
}

