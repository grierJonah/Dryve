package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ratings;

public class RatingsDao {

  protected ConnectionManager connectionManager;

  // Single pattern: instantiation is limited to one object.
  private static RatingsDao instance = null;
  protected RatingsDao() {
    connectionManager = new ConnectionManager();
  }
  public static RatingsDao getInstance() {
    if(instance == null) {
      instance = new RatingsDao();
    }
    return instance;
  }



  /**
   * Save a Rating instance by storing it into the mysql server
   * @param rating the user object we want to save to the sql database
   * @return the user if the INSERT statement has been successful
   *
   * int ratingId, int rating, String userName, int parkingId
   * @throws SQLException
   */
  public Ratings create(Ratings rating) throws SQLException {
    String insertRating = "INSERT INTO Ratings(Rating, UserName, ParkingId) VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;

    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRating);



      insertStmt.setInt(1, rating.getRating());
      insertStmt.setString(2, rating.getUserName());
      insertStmt.setInt(3, rating.getParkingId());


      insertStmt.executeUpdate();


      resultKey = insertStmt.getGeneratedKeys();
      int ratingId = -1;
      if(resultKey.next()) {
        ratingId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      rating.setRatingId(ratingId);

      return rating;
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
   * Delete the Rating instance.
   * This runs a DELETE statement in the MySQL database.
   */
  public Ratings delete(Ratings rating) throws SQLException {
    String deleteRating = "DELETE FROM Ratings WHERE RatingId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;

    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRating);

      deleteStmt.setInt(1, rating.getRatingId());
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
   * Get the matching Ratings records by fetching from your MySQL instance.
   * This runs a SELECT statement and returns a list of matching Ratings.
   * int ratingId, int rating, String userName, int parkingId
   */
  public List<Ratings> getRatingsByUser(String userName) throws SQLException {
    List<Ratings> ratings = new ArrayList<Ratings>();
    String selectRatings =
        "SELECT RatingId,Rating,UserName,ParkingId FROM Ratings WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRatings);

      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      while(results.next()) {
        int ratingId = results.getInt("RatingId");
        int foundRating = results.getInt("Rating");

        String foundUserName = results.getString("UserName");
        int foundParkingId = results.getInt("ParkingId");

        Ratings rating = new Ratings(ratingId, foundRating, foundUserName, foundParkingId);
        ratings.add(rating);
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
    return ratings;
  }

  /**
   * Get the matching Ratings records by fetching from your MySQL instance.
   * This runs a SELECT statement and returns a list of matching Ratings found via parkingId.
   * int ratingId, int rating, String userName, int parkingId
   */
  public List<Ratings> getRatingsParkingId(int parkingId) throws SQLException {
    List<Ratings> ratings = new ArrayList<Ratings>();
    String selectRatings =
        "SELECT RatingId,Rating,UserName,ParkingId FROM Ratings WHERE RatingId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRatings);

      selectStmt.setInt(1, parkingId);
      results = selectStmt.executeQuery();

      while(results.next()) {
        int foundRatingId = results.getInt("RatingId");
        int foundRating = results.getInt("Rating");

        String foundUserName = results.getString("UserName");
        int foundParkingId = results.getInt("ParkingId");

        Ratings rating = new Ratings(foundRatingId, foundRating, foundUserName, foundParkingId);
        ratings.add(rating);
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
    return ratings;
  }


}
