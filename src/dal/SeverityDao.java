package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Severity;


public class SeverityDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static SeverityDao instance = null;
  protected SeverityDao() {
    connectionManager = new ConnectionManager();
  }
  public static SeverityDao getInstance() {
    if(instance == null) {
      instance = new SeverityDao();
    }
    return instance;
  }

  /**
   * Save a severity instance by storing it into the mysql server
   * @param severity the severity object we want to save to the sql database
   * @return the user if the INSERT statement has been successful
   * int severityId, int severityCode, String severityDesc
   * @throws SQLException
   */
  public Severity create(Severity severity) throws SQLException {
    String insertSeverity = "INSERT INTO Severity(SeverityCode, SeverityDesc) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;

    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertSeverity);



      insertStmt.setInt(1, severity.getSeverityCode());
      insertStmt.setString(2, severity.getSeverityDesc());

      insertStmt.executeUpdate();

      return severity;
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


  /**
   * update the severityCode of a Severity object on the MySQL Database
   * @param severity the severity object we want to save to the sql database
   * @param newSeverityCode the new severityCode
   * @return the updated Severity
   * @throws SQLException
   */
  public Severity updateSeverityCode(Severity severity, int newSeverityCode) throws SQLException {
    String updateUser = "UPDATE Severity SET SeverityCode=? WHERE SeverityId=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;

    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateUser);


      updateStmt.setInt(1, newSeverityCode);
      updateStmt.setInt(2, severity.getSeverityId());

      updateStmt.executeUpdate();

      severity.setSeverityCode(newSeverityCode);

      return severity;
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
   * Delete the Severity instance.
   * This runs a DELETE statement in the MySQL database.
   */
  public Severity delete(Severity severity) throws SQLException {
    String deleteSeverity = "DELETE FROM Severity WHERE SeverityId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteSeverity);

      deleteStmt.setInt(1, severity.getSeverityId());
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
  public Severity getSeverityById(int severityId) throws SQLException {
    String selectUser = "SELECT SeverityId,SeverityCode,SeverityDesc FROM Severity WHERE SeverityId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);


      selectStmt.setInt(1, severityId);
      // Note that we call executeQuery(). This is used for a SELECT statement
      // because it returns a result set. For more information, see:
      // http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
      // http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
      results = selectStmt.executeQuery();
      // You can iterate the result set (although the example below only retrieves
      // the first record). The cursor is initially positioned before the row.
      // Furthermore, you can retrieve fields by name and by type.
      if(results.next()) {
        int resultSeverityId = results.getInt("SeverityId");
        String resultSevDesc = results.getString("SeverityDesc");
        int resultSevCode = results.getInt("SeverityCode");


        Severity severity = new Severity(resultSeverityId, resultSevCode, resultSevDesc);
        return severity;
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
}
