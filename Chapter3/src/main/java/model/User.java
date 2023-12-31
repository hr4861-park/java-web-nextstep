package model;

public class User {

  private String userId;
  private String password;
  private String name;
  private String email;

  private User(final String userId, final String password, final String name, final String email) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
  }

  public static User of(
      final String userId, final String password, final String name, final String email) {
    return new User(userId, password, name, email);
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
