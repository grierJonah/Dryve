package model;

public class Users {
    //  UserName VARCHAR(255),
//  Password VARCHAR(255) NOT NULL,
//  FirstName VARCHAR(255) NOT NULL,
//  LastName VARCHAR(255) NOT NULL,
//  Email VARCHAR(255) NOT NULL,
    protected String userName;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;


    public Users(String userName, String password, String firstName, String lastName, String email) {
      this.userName = userName;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
    }

  public Users(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

}
