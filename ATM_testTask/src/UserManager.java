import java.io.IOException;
import java.util.ArrayList;

public class UserManager {
    private static UserManager userManager;
    private FileWorker fileWorker = new FileWorker();
    ArrayList<User> usersList = new ArrayList<>();

    private UserManager() {
    }

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public ArrayList<User> createUsersList() {
        String[] records = fileWorker.readAccountInfo().split("\n");
        String[] record;
        for (String info : records) {
            record = info.trim().split("\s");
            usersList.add(new User(record[0], record[1]));
        }
        return usersList;
    }

    public User createUser(String firstName, String lastName) throws IOException {
        if (isValid(firstName) && isValid(lastName)) {
            return new User(firstName, lastName);
        } else throw new IOException();
    }

    private boolean isValid(String name) {
        return (name == "" || name.isEmpty() || name.length() < 2 || !name.matches("^[a-zA-Z]*$")) ? false : true;
    }
}
