package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Accidents;

public class AccidentsDao {

	//test
	
	ConnectionManager connectionManager;
	private static AccidentsDao instance = null;
	protected AccidentsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static AccidentsDao getInstance() {
		if (instance == null) {
			instance = new AccidentsDao();
		}
		return instance;
	}
	
	/**
	 * Allows an Accident to be created and stored in a MySQL Database.
	 * 
	 * @param accident 
	 * @return
	 */
	public Accidents create(Accidents accident) throws SQLException {
		String insertAccident = "INSERT INTO Accidents(X, Y, SeverityCode,"
				+ " CollisionType, PersonCount, PedCount, PedCycCount, VehCount) VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAccident,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setDouble(1, accident.getxLoc());
			insertStmt.setDouble(2, accident.getyLoc());
			insertStmt.setInt(3, accident.getSeverityCode());
			insertStmt.setString(4, accident.getCollisionType());
			insertStmt.setInt(5, accident.getPersonCount());
			insertStmt.setInt(6, accident.getPedCount());
			insertStmt.setInt(7,  accident.getPedCycCount());
			insertStmt.setInt(8, accident.getVehCount());
			
			insertStmt.executeUpdate();
			
			// Auto Increment KEY
			resultKey = insertStmt.getGeneratedKeys();
			int accidentId = -1;
			if (resultKey.next()) {
				accidentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key");
			}
			accident.setAccidentId(accidentId);
			
			return accident;
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

	public List<Accidents> getTenClosestAccidentsByLoc(double x, double y) throws SQLException {
		System.out.println("running getTenClosestAccidentsByLoc");

		List<Accidents> accidents = new ArrayList<>();
		String selectAccident = "SELECT * FROM Accidents";
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			stmt = connection.prepareStatement(selectAccident);

			results = stmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");

				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);

				accidents.add(accident);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (results != null) {
				results.close();
			}
		}

		Collections.sort(accidents, new Comparator<Accidents>() {
			@Override
			public int compare(Accidents a1, Accidents a2) {
				double dist1 = Math.pow(a1.getxLoc() - x, 2) + Math.pow(a1.getyLoc() - y, 2);
				double dist2 = Math.pow(a2.getxLoc() - x, 2) + Math.pow(a2.getyLoc() - y, 2);
				if (Math.abs(dist1 - dist2) < 0.000000001) return 0;
				return dist1 < dist2 ? -1 : 1;
			}
		});

		System.out.println("x loc accident 0 from the sorted list: " + accidents.get(0).getxLoc());
		return accidents.subList(0, 10);
	}
	
	public List<Accidents> getAccidentByAccidentId(int accidentId) throws SQLException {
		List<Accidents> accidents = new ArrayList<>();
		String selectAccident = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE accidentId=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectAccident);
			insertStmt.setInt(1, accidentId);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentBySeverityCode(int severityCode) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectSeverity = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE SeverityCode=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectSeverity);
			insertStmt.setInt(1, severityCode);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentByCollisionType(String collisionType) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectCollisionType = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE CollisionType=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectCollisionType);
			insertStmt.setString(1, collisionType);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentsByPersonsInvolved(int personCount) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectPersonCount = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE personCount=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectPersonCount);
			insertStmt.setInt(1, personCount);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentsByPedestriansInjured(int pedestrianCount) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectPedestrians = "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE PedCount=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectPedestrians);
			insertStmt.setInt(1, pedestrianCount);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentsByBicycleCount(int bicyclesInvolved) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectBicycle = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE PedCycCount=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectBicycle);
			insertStmt.setInt(1, bicyclesInvolved);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
	
	public List<Accidents> getAccidentsByVehiclesInvolved(int vehiclesCount) throws SQLException {
		List<Accidents> accidents = new ArrayList<Accidents>();
		String selectVehicles = 
				  "SELECT accidentId, X, Y, SeverityCode, CollisionType, PersonCount, PedCount, PedCycCount, VehCount "
				+ "FROM Accidents "
				+ "WHERE vehCount=?;";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(selectVehicles);
			insertStmt.setInt(1, vehiclesCount);
			
			results = insertStmt.executeQuery();
			while (results.next()) {
				int resultAccidentId = results.getInt("accidentId");
				double resultAccidentX = results.getDouble("X");
				double resultAccidentY = results.getDouble("Y");
				int resultSeverityCode = results.getInt("SeverityCode");
				String resultCollisionType = results.getString("CollisionType");
				int resultPersonCount = results.getInt("PersonCount");
				int resultPedCount = results.getInt("PedCount");
				int resultPedCycCount = results.getInt("PedCycCount");
				int resultVehicleCount = results.getInt("VehCount");
				
				Accidents accident = new Accidents(resultAccidentId, resultAccidentX, resultAccidentY, resultSeverityCode,
						resultCollisionType, resultPersonCount, resultPedCount, resultPedCycCount, resultVehicleCount);
				accidents.add(accident);
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
		return accidents;
	}
}
