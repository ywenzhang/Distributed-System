package Dao;

import ConnectionManager.DBCPDataSource;
import Model.VerticalDayModel;

import java.sql.*;

/**
 * Represent VerticalDayDao with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class VerticalDayDao {
  private Connection conn;
  private PreparedStatement preparedStatement;

  public VerticalDayDao() {
    try {
      conn = DBCPDataSource.getConnection();
      //System.out.println(conn);
      preparedStatement = null;
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void createOrUpdateVerticalDay(VerticalDayModel verticalDay) {
    String insertQueryStatement = "INSERT INTO VerticalDay (PK, total) " +
        "VALUES (?,?) " + "ON DUPLICATE KEY UPDATE total=total+VALUES(total)";
    try {
      preparedStatement = conn.prepareStatement(insertQueryStatement);
      preparedStatement.setString(1, verticalDay.getPk());
      preparedStatement.setInt(2, verticalDay.getTotal());
      // execute insert SQL statement
      preparedStatement.executeUpdate();
      preparedStatement.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int getVerticalDay(String PrimaryKey){
    String selectQueryStatement = "SELECT total FROM VerticalDay WHERE VerticalDay.PK = ?" ;
    try {
      preparedStatement = conn.prepareStatement(selectQueryStatement);
      preparedStatement.setString(1, PrimaryKey);
      // execute insert SQL statement
      ResultSet rs = preparedStatement.executeQuery();
      while(rs.next()){
        int ans = rs.getInt("total");
        preparedStatement.close();
        conn.close();
        return ans;
      }
      rs.close();
      preparedStatement.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

}
