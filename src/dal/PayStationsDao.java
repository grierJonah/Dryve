package dal;

import model.PayStations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayStationsDao {
    private ConnectionManager connectionManager;

    private static PayStationsDao instance = null;

    private PayStationsDao() {
        this.connectionManager = new ConnectionManager();
    }

    public static PayStationsDao getInstance() {
        if (instance == null) {
            instance = new PayStationsDao();
        }
        return instance;
    }

    public PayStations create(PayStations payStation) throws SQLException {
        String insertPayStation = "INSERT INTO PayStations(X,Y,Neighborhood,SubArea,Side,UnitDescription,PayByPhone,WeekDayRate_1Hour," +
                "WeekDayRate_2Hour,WeekDayRate_3Hour,SaturdayRate_1Hour,SaturdayRate_2Hour,SaturdayRate_3Hour," +
                "SaturdayStartTime,SaturdayEndTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(insertPayStation);
            statement.setDouble(1, payStation.getxLoc());
            statement.setDouble(2, payStation.getyLoc());
            statement.setString(3, payStation.getNeighborhood());
            statement.setString(4, payStation.getSubArea());
            statement.setString(5, payStation.getSide());
            statement.setString(6, payStation.getUnitDescription());
            statement.setInt(7, payStation.getPayByPhone());
            statement.setDouble(8, payStation.getWeekDayRate1Hour());
            statement.setDouble(9, payStation.getWeekDayRate2Hour());
            statement.setDouble(10, payStation.getWeekDayRate3Hour());
            statement.setDouble(11, payStation.getSaturdayRate1Hour());
            statement.setDouble(12, payStation.getSaturdayRate2Hour());
            statement.setDouble(13, payStation.getSaturdayRate3Hour());
            statement.executeUpdate();

            ResultSet results = statement.getGeneratedKeys();
            int payId = -1;
            if (results.next()) {
                payId = results.getInt(1);
            } else {
                throw new SQLException();
            }
            payStation.setPayStationId(payId);
            return payStation;
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

    public PayStations delete(PayStations payStation) throws SQLException {
        String deletePayStation = "DELETE FROM PayStations WHERE PayStationId=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(deletePayStation);
            statement.setInt(1, payStation.getPayStationId());
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

    public List<PayStations> getPayStationsByNeighborhood(String neighborhood) throws SQLException {
        List<PayStations> payStations = new ArrayList<>();
        String findFavLocations = "SELECT * FROM PayStations WHERE Neighborhood=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            statement = connection.prepareStatement(findFavLocations);
            statement.setString(1, neighborhood);
            results = statement.executeQuery();

            while (results.next()) {
                int id = results.getInt("PayStationId");
                double x = results.getDouble("X");
                double y = results.getDouble("Y");
                String resultNeighborhood = results.getString("Neighborhood");
                String subArea = results.getString("SubArea");
                String side = results.getString("Side");
                String unitDescription = results.getString("UnitDescription");
                int payByPhone = results.getInt("PayByPhone");
                double week1 = results.getDouble("WeekDayRate_1Hour");
                double week2 = results.getDouble("WeekDayRate_2Hour");
                double week3 = results.getDouble("WeekDayRate_3Hour");
                double sat1 = results.getDouble("SaturdayRate_1Hour");
                double sat2 = results.getDouble("SaturdayRate_2Hour");
                double sat3 = results.getDouble("SaturdayRate_3Hour");
                Date satStart = results.getDate("SaturdayStartTime");
                Date satEnd = results.getDate("SaturdayEndTime");
                payStations.add(new PayStations(id, x, y, resultNeighborhood, subArea, side, unitDescription, payByPhone,
                        week1, week2, week3, sat1, sat2, sat3, satStart, satEnd));
            }
            return payStations;
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
