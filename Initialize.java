/*
 *

 The purpose of this file is to initialize the database by DELETING the tables ,recreating and populating it in Java at a local MySQL database server. 
     1) Execute each SQL statement in Java.  
     *
 * */
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;

public class Initialize  {
  private static Connection connect = null;
  private static Statement statement = null;
  //private static PreparedStatement preparedStatement = null;
  private static ResultSet resultSet = null;



 public static void main(String[] args) {

    String sql1 = "DROP TABLE IF EXISTS users";
	String sql2 = "DROP TABLE IF EXISTS Follow";
	String sql3 = "DROP TABLE IF EXISTS PPS";
	String sql4 = "DROP TABLE IF EXISTS Transaction";
    String sql5 = "CREATE TABLE IF NOT EXISTS users " +
                   "(emailid VARCHAR(50) not NULL, " +
				   " password VARCHAR(50) not NULL, "+
                   " fName VARCHAR(20), " + 
                   " lname VARCHAR(20), " +
                   " Address VARCHAR(50), " + 
                   " birthday date, " + 
                   " PPSbalance VARCHAR(50), " +
                   " Dollarbal VARCHAR(50), " +
                   " PRIMARY KEY ( emailid ))";  
	String sql6 = "CREATE TABLE IF NOT EXISTS Follow " +
                   "(emailid VARCHAR(50) not NULL, " +
                   " followerid VARCHAR(20), " + 
                   " followdate	datetime, " +
                   " FOREIGN KEY (emailid) REFERENCES Users(emailid ))"; 
	String sql7 = "CREATE TABLE IF NOT EXISTS PPS " +
                   "(ppsprice INTEGER)"; 
	String sql8 = "CREATE TABLE IF NOT EXISTS Transaction " +
                   "(transid VARCHAR(50) not NULL, " +
				   " transname VARVHAR(50), " +
				   " dollaramt INTEGER, " +
				   " PPSamt INTEGER, " +
				   " PPSbal INTEGER, " +
				   " fromuser VARCHAR(50), " +
				   " touser VARCHAR(50), "+
                   " PRIMARY KEY (transid), " +
                   " FOREIGN KEY (fromuser) REFERENCES Users(emailid), "+
				   " FOREIGN KEY (touser) REFERENCES Users(emailid))"; 
    String sql9 = "insert into  users(emailid, password, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (?, ?, ?, ?, ? ,? , ?, ?)";
    String sql10 = "insert into users(emailid, password, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"root\",\"pass\" , , , , , \"1T\",\"1M\")";
    String sql11 = "insert into users(emailid, password, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj1782@wayne.edu\",\"pass123\" ,\"Priya\" ,\"Pichai\" ,\"123,Troy\" ,\"26-Sep-1984\" , \"1T\",\"1M\")";
    //String sql11 = "UPDATE Student set Address=\"123 New Main Street, Troy, MI 48083\" WHERE Name=\"Shiyong Lu\"";
    

    try {
      System.out.println("Select a table and then print out its content.");
      // This will load the MySQL driver, each DB has its own driver
      // Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/testdb?"
              + "useSSL=false&user=john&password=pass1234");

        

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();

      statement.executeUpdate(sql1);
      statement.executeUpdate(sql2);
	  statement.executeUpdate(sql3);
	  statement.executeUpdate(sql4);


      //preparedStatement = connect
      //    .prepareStatement(sql3);
      //preparedStatement.setString(1, "Shiyong Lu");
      //preparedStatement.setString(2, "123 Main Street, Troy, MI 48083");
      //preparedStatement.setString(3, "Senior");
      //preparedStatement.executeUpdate();
      
      statement.executeUpdate(sql5);
	  statement.executeUpdate(sql9);
	  statement.executeUpdate(sql10);


      // see the results 
	  System.out.println("After the insert statement for Users is executed.");
      resultSet = statement.executeQuery("select * from users");
      writeResultSet(resultSet);
	  
	  System.out.println("After the select statement for Follow is executed.");
	  statement.executeUpdate(sql6);
	  resultSet = statement.executeQuery("select * from Follow");
      writeResultSet(resultSet);
	  
	  System.out.println("After the select statement for PPS is executed.");
	  statement.executeUpdate(sql7);
	  resultSet = statement.executeQuery("select * from PPS");
      writeResultSet(resultSet);
	  
	  System.out.println("After the select statement for Transaction is executed.");
	  statement.executeUpdate(sql8);
	  resultSet = statement.executeQuery("select * from Transaction");
      writeResultSet(resultSet);

      //System.out.println("After the update statement is executed.");
      //statement.executeUpdate(sql5);
      // see the results 
      //resultSet = statement.executeQuery("select * from Student");
      //writeResultSet(resultSet);

      //System.out.println("After the delete statement is executed.");
      //statement.executeUpdate(sql6);
      // see the results 
      //resultSet = statement.executeQuery("select * from Student");
      //writeResultSet(resultSet);

      
    } catch (Exception e) {
         System.out.println(e);
    } finally {
      close();
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private static void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    System.out.println("print result from a table..");
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      Integer id = resultSet.getInt("emailid");
      String name = resultSet.getString("fName" + "lname");
      String address = resultSet.getString("Address");
      String birthday = resultSet.getString("birthday");
	  String PPSbal = resultSet.getString("PPSbal");
	  String Dollarbal = resultSet.getString("Dollarbal");
      System.out.println("emailid: " + id);
      System.out.println("name: " + name);
      System.out.println("address: " + address);
      System.out.println("birthday: " + birthday);
	  System.out.println("PPSbal: " + PPSbal);
	  System.out.println("Dollarbal: " + Dollarbal);
	  System.out.println("");
	  String follower = resultSet.getString("followerid");
	  String followdate = resultSet.getString("followdate");
	  System.out.println("followerid: " + follower);
	  System.out.println("followdate: " + followdate);
	  System.out.println("");
	  Integer ppsprice = resultSet.getInt("ppsprice");
	  System.out.println("ppsprice: " + ppsprice);
	  System.out.println("");
	  Integer transid = resultSet.getInt("transid");
	  String transname = resultSet.getString("transname");
	  Integer dollaramt = resultSet.getInt("dollaramt");
	  Integer PPSamt = resultSet.getInt("PPSamt");
	  Integer PPSbal1 = resultSet.getInt("PPSbal");
	  String fromuser = resultSet.getString("fromuser");
	  String touser = resultSet.getString("touser");
	  System.out.println("transid: " + transid);
	  System.out.println("transname: " + transname);
	  System.out.println("dollaramt: " + dollaramt);
	  System.out.println("PPSamt: " + PPSamt);
	  System.out.println("PPSbal: " + PPSbal1);
	  System.out.println("fromuser: " + fromuser);
	  System.out.println("touser: " + touser);
	  System.out.println("");
	  
	  
    }
  }

  // You need to close the resultSet
  private static void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
} 
