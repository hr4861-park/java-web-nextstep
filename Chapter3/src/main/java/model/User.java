package model;

import java.util.Map;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    private User(final String userId, final String password, final String name, final String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User fromMap(final Map<String, String> body) {
        return new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{"
            + "userId='"
            + userId
            + '\''
            + ", password='"
            + password
            + '\''
            + ", name='"
            + name
            + '\''
            + ", email='"
            + email
            + '\''
            + '}';
    }
}
