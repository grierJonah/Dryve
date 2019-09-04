package dal;

import model.FavoriteLocations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteLocationsDao{
    protected ConnectionManager connectionManager;

    private static FavoriteLocationsDao instance = null;

    private FavoriteLocationsDao() {
        connectionManager = new ConnectionManager();
    }

    public static FavoriteLocationsDao getInstance() {
        if (instance == null) {
            instance = new FavoriteLocationsDao();
        }
        return instance;
    }

    public FavoriteLocations create(FavoriteLocations favoriteLocation) throws SQLException {
        String insertFavLocation = "INSERT INTO FavoriteLocations(UserName) VALUES(?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(insertFavLocation);
            statement.setString(1, favoriteLocation.getUserName());
            statement.executeUpdate();

            ResultSet results = statement.getGeneratedKeys();
            int favLocationId = -1;
            if (results.next()) {
                favLocationId = results.getInt(1);
            } else {
                throw new SQLException();
            }
            favoriteLocation.setFavoriteLocId(favLocationId);

            return favoriteLocation;
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

    public FavoriteLocations delete(FavoriteLocations favoriteLocation) throws SQLException {
        String delFavLocation = "DELETE FROM FavoriteLocations WHERE FavoriteLocId=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(delFavLocation);
            statement.setInt(1, favoriteLocation.getFavoriteLocId());
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

    public List<FavoriteLocations> getFavoriteLocationsByUserName(String userName) throws SQLException {
        List<FavoriteLocations> locations = new ArrayList<>();
        String findFavLocations = "SELECT * FROM FavoriteLocations WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(findFavLocations);
            statement.setString(1, userName);
            results = statement.executeQuery();
            while (results.next()) {
                int locId = results.getInt("FavoriteLocId");
                String resultUserName = results.getString("UserName");
                locations.add(new FavoriteLocations(locId, resultUserName));
            }
            return locations;
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
