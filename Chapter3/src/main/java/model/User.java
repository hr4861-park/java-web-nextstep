package model;

public class User {

  private String userId;
  private String password;
  private String name;
  private String email;

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
