import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        ATM atm = ATM.getAtm();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<Card, User> accounts;

        try {
            accounts = atm.initialization();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Sorry, the app is temporarily unavailable. Try again later.");
            return;
        }

        System.out.print("Welcome! Please introduce yourself.\nEnter your first name: ");
        String firstName = reader.readLine();
        System.out.print("Enter your last name: ");
        String lastName = reader.readLine();

        User atmUser = null;
        try {
            atmUser = atm.introduction(firstName, lastName);
        } catch (IOException e) {
            System.out.println("Incorrect input");
            return;
        }

        boolean isUserExist = atm.identification(atmUser);

        boolean answer = false;
        boolean isAnswerReceived = false;
        while (!isAnswerReceived) {
            switch (reader.readLine()) {
                case "yes":
                    answer = true;
                    isAnswerReceived = true;
                    break;
                case "no":
                    isAnswerReceived = true;
                    break;
                default:
                    System.out.println("There is no such answer");
                    break;
            }
        }
        if (!answer && !isUserExist) return;

        atm.registration(atmUser, answer);

        Map<Integer, Card> userCards = atm.getUserCards(atmUser);

        System.out.print("Enter the number from this list to get asses the card(without a dot): ");
        boolean isNumberCorrect = false;
        Card atmUserCard = null;
        while (!isNumberCorrect) {
            try {
                int number = Integer.parseInt(reader.readLine());
                atmUserCard = atm.chooseUserCard(userCards, number);
                isNumberCorrect = true;
            } catch (Exception e) {
                System.out.println("Incorrect number");
            }
        }

        if (atm.checkBlocking(atmUserCard)) return;

        System.out.print("Enter the password(you have three attempts): ");
        int numberOfAttempts = 0;
        boolean isLoginCompleted;

        while (numberOfAttempts < 3) {
            String potentialPassword = reader.readLine();
            isLoginCompleted = atm.logIn(atmUserCard, potentialPassword);

            if (isLoginCompleted) {
                atm.personalAccount(atmUser, atmUserCard);
                int number = 0;
                while (number != 5) {
                    try {
                        number = Integer.parseInt(reader.readLine());
                    } catch (IOException e) {
                        System.out.println("Incorrect input");
                    }
                    switch (number) {
                        case 1, 2:
                            System.out.print("Please enter the amount of money: ");
                            int sumOfMoney = 0;
                            try {
                                sumOfMoney = Integer.parseInt(reader.readLine());
                            } catch (IOException e) {
                                System.out.println("Incorrect input");
                            }
                            atm.moneyOperations(atmUserCard, number, sumOfMoney);
                            break;
                        case 3, 4:
                            atm.userOperations(atmUser, number);
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("There is no such number");
                            break;
                    }
                }
                break;
            } else {
                numberOfAttempts++;
                if (numberOfAttempts == 3) {
                    atm.blockUserCard(atmUserCard);
                    break;
                }
                System.out.print("The password is incorrect. Try again(number of attempts - " + (3 - numberOfAttempts) + "): ");
            }
        }

        atm.termination(accounts);
    }
}
