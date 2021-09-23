import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ATM {
    private static ATM atm;
    private double totalSum;
    private AccountManager accountManager = AccountManager.getAccountManager();

    private ATM() {
    }

    public static ATM getAtm() {
        if (atm == null) {
            atm = new ATM();
        }
        return atm;
    }

    private double getTotalSum() {
        return totalSum;
    }

    private void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    private double putMoneyInAnATM() {
        return (Math.random() * (999999999 - 999999)) + 999999;
    }

    private void removeOldBlocks() {
        ArrayList<BlockedCard> blockedCards = accountManager.createBlockedCardsList();
        ArrayList<BlockedCard> newBlockedCards = accountManager.updateBlockedCards(blockedCards);
    }

    public Map<Card, User> initialization() throws ArrayIndexOutOfBoundsException {
        removeOldBlocks();
        totalSum = putMoneyInAnATM();
        return accountManager.createAccountsList();
    }

    public void termination(Map<Card, User> accounts) {
        accountManager.updateDB(accounts, accountManager.getBlockedCards());
    }

    private void withdrawMoney(Card card, int sum) {
        if (sum <= totalSum) {
            card.setBalance(card.getBalance() - sum);
            setTotalSum(getTotalSum() - sum);
            System.out.println("Please take your " + sum + ". Your balance: " + card.getBalance());
        } else {
            System.out.println("Sorry, there is not so much money in the ATM");
        }
    }

    private void depositMoney(Card card, int sum) {
        if (sum < 1000000) {
            card.setBalance(card.getBalance() + sum);
            setTotalSum(getTotalSum() + sum);
            System.out.println("Successfully. Your balance: " + card.getBalance());
        } else {
            System.out.println("The deposit amount must not exceed 1,000,000");
        }
    }

    public User introduction(String firstName, String lastName) throws IOException {
        return accountManager.createUser(firstName, lastName);
    }

    public boolean identification(User user) {
        boolean isUserExist;
        if (accountManager.findUser(user)) {
            System.out.print("We remember you.\nDo you want to create a new account(or use an existing one(s))?(yes/no): ");
            isUserExist = true;
        } else {
            System.out.print("You are not a customer of the bank. Do you want to create an account?(yes/no): ");
            isUserExist = false;
        }
        return isUserExist;
    }

    public Account registration(User user, boolean answer) {
        if (answer) {
            Account newAccount = accountManager.createAccount(user);
            getRegistrationInfo(newAccount);
            return newAccount;
        } else return null;
    }

    private void getRegistrationInfo(Account account) {
        System.out.println("The new account was successfully created. Here is the card number: " + account.getCard().getNumber() + " and password: " +
                account.getCard().getPassword() + ".\nDo not transfer the data to third parties.");
    }

    public Map<Integer, Card> getUserCards(User user) {
        System.out.println("Here is a list of your accounts");
        Map<Integer, Card> userCards = accountManager.getUserCards(user);
        accountManager.showUserCardsList(userCards);
        return userCards;
    }

    public Card chooseUserCard(Map<Integer, Card> userCards, int number) throws IOException {
        return accountManager.chooseCard(userCards, number);
    }

    public boolean logIn(Card card, String potentialPassword) {
        return card.getPassword().equals(potentialPassword);
    }

    public void blockUserCard(Card card) {
        accountManager.blockCard(card);
    }

    public boolean checkBlocking(Card card) {
        boolean isCardBlocked = accountManager.isCardBlocked(card);
        if (isCardBlocked) {
            System.out.println("Your card " + card.getNumber() + " has been blocked. We recommend calling the bank.");
        }
        return isCardBlocked;
    }

    public void personalAccount(User user, Card card) {
        System.out.println("Welcome to your user account " + user.toString() + "!\nYour card number is " + card.getNumber() + ". Your card balance: " + card.getBalance() + ".\nWhat do you want to do?\n" +
                "1. Withdraw money from the card\n2. Top up the card balance(no more than 1 000 000)\n3. View the list of my accounts\n4. Create a new account \n5. Exit");
    }

    public void moneyOperations(Card card, int number, int sumOfMoney) {
        switch (number) {
            case 1:
                withdrawMoney(card, sumOfMoney);
                break;
            case 2:
                depositMoney(card, sumOfMoney);
                break;
            default:
                System.out.println("There is no such number");
                break;
        }
    }

    public void userOperations(User user, int number) {
        switch (number) {
            case 3:
                Map<Integer, Card> userCards = accountManager.getUserCards(user);
                accountManager.showUserCardsList(userCards);
                break;
            case 4:
                Account newAccount = accountManager.createAccount(user);
                getRegistrationInfo(newAccount);
                break;
            case 5:
                break;
            default:
                System.out.println("There is no such number");
                break;
        }
    }
}

