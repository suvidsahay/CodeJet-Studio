package user;

public class User {
    private String username, password, Name;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void register(String name, String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public boolean registerUser() {
        return true;
    }

    public boolean loginUser () {
        return true;
    }

}
