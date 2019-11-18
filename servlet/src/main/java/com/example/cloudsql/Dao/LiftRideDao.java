package com.example.cloudsql.Dao;
import com.example.cloudsql.DBCPDataSource;
import com.example.cloudsql.Model.LiftRideModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represent LiftRideDao with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class LiftRideDao {
  private Connection conn;
  private PreparedStatement preparedStatement;

  public LiftRideDao() {
    //System.out.println(2);
    try {
      conn = DBCPDataSource.getConnection();
      preparedStatement = null;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void createLiftRide(LiftRideModel newLiftRide) {
    String insertQueryStatement = "INSERT IGNORE INTO LiftRides (skierId, resortId, seasonId, dayId, time, liftId, PK) " +
        "VALUES (?,?,?,?,?,?,?) " ;
    try {
      preparedStatement = conn.prepareStatement(insertQueryStatement);
      preparedStatement.setInt(1, newLiftRide.getSkierId());
      preparedStatement.setInt(2, newLiftRide.getResortId());
      preparedStatement.setInt(3, newLiftRide.getSeasonId());
      preparedStatement.setInt(4, newLiftRide.getDayId());
      preparedStatement.setInt(5, newLiftRide.getTime());
      preparedStatement.setInt(6, newLiftRide.getLiftId());
      preparedStatement.setString(7, newLiftRide.getPK());
      // execute insert SQL statement
      preparedStatement.executeUpdate();
      preparedStatement.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
