package dal;


import model.FavoriteParking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteParkingDao {
    protected ConnectionManager connectionManager;

    private static FavoriteParkingDao instance = null;
    private FavoriteParkingDao() {
        connectionManager = new ConnectionManager();
    }

    public static FavoriteParkingDao getInstance() {
        if (instance == null) {
            instance = new FavoriteParkingDao();
        }
        return instance;
    }

    public FavoriteParking create(FavoriteParking parking) throws SQLException {
        String insertFavParking = "INSERT INTO FavoriteParking(UserName,ParkingId) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(insertFavParking);
            statement.setString(1, parking.getUserName());
            statement.setInt(2, parking.getParkingId());
            statement.executeUpdate();

            ResultSet results = statement.getGeneratedKeys();
            int parkingId = -1;
            if (results.next()) {
                parkingId = results.getInt(1);
            } else {
                throw new SQLException();
            }
            parking.setFavoriteParkingId(parkingId);

            return parking;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connectionManager.closeConnection(connection);
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public FavoriteParking delete(FavoriteParking parking) throws SQLException {
        String delFavParking = "DELETE FROM FavoriteParking WHERE FavoriteParkingId=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(delFavParking);
            statement.setInt(1, parking.getFavoriteParkingId());
            statement.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connectionManager.closeConnection(connection);
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public List<FavoriteParking> getFavParkingByUserName(String userName) throws SQLException {
        List<FavoriteParking> favoriteParkings = new ArrayList<>();
        String findFavLocations = "SELECT * FROM FavoriteParking WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(findFavLocations);
            statement.setString(1, userName);
            results = statement.executeQuery();
            while (results.next()) {
                int locId = results.getInt("FavoriteParkingId");
                String resultUserName = results.getString("UserName");
                int parkingId = results.getInt("ParkingId");
                favoriteParkings.add(new FavoriteParking(locId, parkingId, resultUserName));
            }
            return favoriteParkings;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connectionManager.closeConnection(connection);
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

}
