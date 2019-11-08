package ConnectionManager;

import java.sql.*;
import org.apache.commons.dbcp2.*;

/**
 * Represent DBCPDataSource with their details-- . *
 *
 * @author Yiwen Zhang
 */
public class DBCPDataSource {
  private static BasicDataSource dataSource = new BasicDataSource();

  // NEVER store sensitive information below in plain text!
  private static final String HOST_NAME = "distributedssystemcs6500.c0zfmgsvs3cu.us-east-1.rds.amazonaws.com";
  private static final String PORT = "3306";
  private static final String DATABASE = "innodb";
  private static final String USERNAME = "yiwenzhang";
  private static final String PASSWORD = "Zyw921019";
  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static {
    // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
    String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);
    dataSource.setUrl(url);
    dataSource.setDriverClassName(JDBC_DRIVER);
    dataSource.setUsername(USERNAME);
    dataSource.setPassword(PASSWORD);
    dataSource.setMinIdle(0);
    dataSource.setMaxIdle(-1);
    dataSource.setMaxTotal(-1);
    dataSource.setMaxOpenPreparedStatements(100);
  }

  public static Connection getConnection() throws SQLException, ClassNotFoundException {
    return dataSource.getConnection();
  }
}
