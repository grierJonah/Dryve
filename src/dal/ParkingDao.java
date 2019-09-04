package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Parking;

public class ParkingDao {
	
	int parkingId;
	String facilityName;
	String facilityAddress;
	Time hrsOpenWeek;
	Time hrsOpenSat;
	Time hrsOpenSun;
	double rate1hr;
	double rate2hr;
	double rate3hr;
	double rateAllDay;
	int capacity;
	
	protected ConnectionManager connectionManager;
	private static ParkingDao instance = null;
	protected ParkingDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static ParkingDao getInstance() {
		if (instance == null) {
			instance = new ParkingDao();
		}
		return instance;
	}
	
	public Parking create(Parking parking) throws SQLException {
		String insertParking = "INSERT INTO Parking(FacilityName, FacilityAddress, HRSOpenWeek,"
				+ "HRSOpenSAT, HRSOpenSUN, Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertParking);
			insertStmt.setString(1, parking.getFacilityName());
			insertStmt.setString(2, parking.getFacilityAddress());
			insertStmt.setTime(3, new Time(parking.getHrsOpenWeek().getTime()));
			insertStmt.setTime(4, new Time(parking.getHrsOpenSat().getTime()));
			insertStmt.setTime(5,  new Time(parking.getHrsOpenSun().getTime()));
			insertStmt.setDouble(6, parking.getRate1HR());
			insertStmt.setDouble(7, parking.getRate2HR());
			insertStmt.setDouble(8, parking.getRate3HR());
			insertStmt.setDouble(9, parking.getRateAllDay());
			insertStmt.setInt(10, parking.getCapacity());
			
			insertStmt.executeUpdate();
			
			// Auto Increment Key
			resultKey = insertStmt.getGeneratedKeys();
			int parkingId = -1;
			if (resultKey.next()) {
				parkingId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key");
			}
			parking.setParkingId(parkingId);
			return parking;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Parking delete(Parking parking) throws SQLException {
		String deleteParking = "DELETE FROM Parking WHERE ParkingId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteParking);
			deleteStmt.setInt(1, parking.getParkingId());
			deleteStmt.executeUpdate();
			
			return null;
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	public List<Parking> getParkingByWeeklyHours(Time hour) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE HRSOpenWeek=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setTime(1, hour);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
	
	public List<Parking> getParkingOneHourRate(Double rate) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE Rate_1HR<=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setDouble(1, rate);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
	
	public List<Parking> getParkingTwoHourRate(Double rate) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE Rate_2HR<=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setDouble(1, rate);;
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
	
	public List<Parking> getParkingThreeHourRate(Double rate) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE Rate_3HR=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setDouble(1, rate);;
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
	
	public List<Parking> getParkingAllDayRate(Double rate) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE Rate_ALLDAY=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setDouble(1, rate);;
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
	
	public List<Parking> getParkingByCapacity(int capacity) throws SQLException {
		List<Parking> parking = new ArrayList<Parking>();
		String selectParking = 
				  "SELECT ParkingId, FacilityName, FacilityAddress, HRSOpenWeek, HRSOpenSAT, HRSOpenSUN,"
				+ " Rate_1HR, Rate_2HR, Rate_3HR, Rate_ALLDAY, Capacity "
				+ "FROM Parking "
				+ "WHERE Capacity=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectParking);
			insertStmt.setInt(1, capacity);;
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultParkingId = results.getInt("ParkingId");
				String resultFacilityName = results.getString("FacilityName");
				String resultAddress = results.getString("FacilityAddress");
				Time resultHrsOpenWeek = results.getTime("HRSOpenWeek");
				Time resultHrsOpenSAT = results.getTime("HRSOpenSAT");
				Time resultHrsOpenSUN = results.getTime("HRSOpenSUN");
				double resultRate1hr = results.getDouble("Rate_1HR");
				double resultRate2hr = results.getDouble("Rate_2HR");
				double resultRate3hr = results.getDouble("Rate_3HR");
				double resultRateAllDay = results.getDouble("Rate_ALLDAY");
				int resultCapacity = results.getInt("Capacity");
				
				Parking newParking = new Parking(resultParkingId, resultFacilityName, resultAddress, resultHrsOpenWeek, resultHrsOpenSAT,
						resultHrsOpenSUN, resultRate1hr, resultRate2hr, resultRate3hr, resultRateAllDay, resultCapacity);
				parking.add(newParking);
			}
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
			if (results != null) {
				results.close();
			}
		}
		return parking;
	}
}
