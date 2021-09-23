import java.io.*;

public class FileWorker {
    private static final String accountsFileName = "usersInformation.txt";
    private static final String blockedCardsFileName = "blockedCards.txt";
    private String accountsInfo = "";
    private String blockedCardsInfo = "";

    public String readAccountInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFileName))) {
            while (reader.ready()) {
                accountsInfo += reader.readLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in the file name");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountsInfo;
    }

    public void writeAccountInfo(Account account) {
        String accountRecord = account.toString();
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(accountsFileName, true)))) {
            writer.write(accountRecord + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanUpAccountsList() {
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(accountsFileName)))) {
            writer.write("");
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }
    }

    public void cleanUpBlockedCardsList() {
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(blockedCardsFileName)))) {
            writer.write("");
        } catch (Exception e) {
            System.err.println("Error in file cleaning: " + e.getMessage());
        }
    }

    public String readBlockedCardsInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(blockedCardsFileName))) {
            while (reader.ready()) {
                blockedCardsInfo += reader.readLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error in the file name");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockedCardsInfo;
    }

    public void writeBlockedCardsInfo(BlockedCard card) {
        String blockedCardRecord = card.toString();
        try (BufferedWriter writer = new BufferedWriter((new FileWriter(blockedCardsFileName, true)))) {
            writer.write(blockedCardRecord + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
