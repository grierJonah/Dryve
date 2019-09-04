package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import model.Users;

/**
 * used as the Data Access Object for the User table
 * CREATE
 * UPDATE
 * DELETE
 */
public class UsersDao {
  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static UsersDao instance = null;
  protected UsersDao() {
    connectionManager = new ConnectionManager();
  }
  public static UsersDao getInstance() {
    if(instance == null) {
      instance = new UsersDao();
    }
    return instance;
  }


  /**
   * Save a user instance by storing it into the mysql server
   * @param user the user object we want to save to the sql database
   * @return the user if the INSERT statement has been successful
   * @throws SQLException
   */
  public Users create(Users user) throws SQLException {
    String insertUser = "INSERT INTO Users(UserName, Password, FirstName, LastName, Email) VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertUser);

      insertStmt.setString(1, user.getUserName());
      insertStmt.setString(2, user.getPassword());
      insertStmt.setString(3, user.getFirstName());
      insertStmt.setString(4, user.getLastName());
      insertStmt.setString(5, user.getEmail());

      insertStmt.executeUpdate();

      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  public Users updateUserName(Users user, String newUserName) throws SQLException {
    String updateUser = "UPDATE Users SET UserName=? WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;

    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateUser);


      updateStmt.setString(1, newUserName);
      updateStmt.setString(2, user.getUserName());

      updateStmt.executeUpdate();

      user.setUserName(newUserName);

      return user;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
      connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  /**
   * Delete the User instance.
   * This runs a DELETE statement in the MySQL database.
   */
  public Users delete(Users user) throws SQLException {
    String deleteUser = "DELETE FROM Users WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteUser);

      deleteStmt.setString(1, user.getUserName());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Persons instance.
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }


  /**
   * Get the Persons record by fetching it from your MySQL instance.
   * This runs a SELECT statement and returns a single Persons instance.
   * String userName, String password, String firstName, String lastName, String email
   */
  public Users getUserByUserName(String userName) throws SQLException {
//	List<Users> users = new ArrayList<Users>();
	String selectUser = "SELECT UserName,Password,FirstName,LastName,Email FROM Users WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);


      selectStmt.setString(1, userName);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String resultUserName = results.getString("UserName");
        String password = results.getString("Password");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");

        Users user = new Users(resultUserName, password, firstName, lastName, email);
       return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return null;
  }



  public List<Users> getUsersByUserName(String userName) throws SQLException {
	List<Users> users = new ArrayList<Users>();
    String selectUser = "SELECT UserName,Password,FirstName,LastName,Email FROM Users WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);


      selectStmt.setString(1, userName);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        String resultUserName = results.getString("UserName");
        String password = results.getString("Password");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");

        Users user = new Users(resultUserName, password, firstName, lastName, email);
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return users;
  }

  /**
   * Get the matching Users records by fetching from your MySQL instance.
   * This runs a SELECT statement and returns a list of matching Users.
   */
  public List<Users> getPersonsFromFirstName(String firstName) throws SQLException {
    List<Users> users = new ArrayList<Users>();
    String selectPersons =
        "SELECT UserName,Password,FirstName,LastName,Email FROM Users WHERE FirstName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPersons);
      selectStmt.setString(1, firstName);
      results = selectStmt.executeQuery();

      while(results.next()) {
        String userName = results.getString("UserName");
        String password = results.getString("Password");

        String resultFirstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");

        Users user = new Users(userName, password, resultFirstName, lastName, email);
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return users;
  }
}
