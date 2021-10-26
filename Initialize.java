/**

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



 public static void main() {
	 
	 System.out.println("Enter Main");

    String sql1 = "DROP TABLE IF EXISTS users";
	String sql2 = "DROP TABLE IF EXISTS Follow";
	String sql3 = "DROP TABLE IF EXISTS PPS";
	String sql4 = "DROP TABLE IF EXISTS Transaction";
    String sql5 = "CREATE TABLE IF NOT EXISTS users " +
                   "(emailid VARCHAR(50) not NULL, " +
				   " password VARCHAR(50) not NULL, "+
				   " password2 VARCHAR(50) not NULL, "+
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
				   " transname VARCHAR(50), " +
				   " dollaramt INTEGER, " +
				   " PPSamt INTEGER, " +
				   " PPSbal INTEGER, " +
				   " fromuser VARCHAR(50), " +
				   " touser VARCHAR(50), "+
                   " PRIMARY KEY (transid), " +
                   " FOREIGN KEY (fromuser) REFERENCES Users(emailid), "+
				   " FOREIGN KEY (touser) REFERENCES Users(emailid))"; 
     //String sql9 = "insert into  users(emailid, password, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (?, ?, ?, ?, ? ,? , ?, ?)";
     String sql10 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"root\",\"pass1234\",\"pass1234\",\"root\" ,\"user\" ,\"143,Troy\" ,\"2015-12-17\" , \"1,000,000,000,000\",\"1000000\")";
     String sql11 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj1786@wayne.edu\",\"pass123\" ,\"pass123\" ,\"Priya\" ,\"Pichai\" ,\"123,Troy\" ,\"1984-09-26\" , \"1000\",\"1000\")";
	 String sql12 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hi9643@wayne.edu\",\"pass1234\" ,\"pass1234\" ,\"Jon\" ,\"Sch\" ,\"124,Novi\" ,\"2015-12-17\" , \"1234\",\"1234\")";
	 String sql13 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj171@wayne.edu\",\"pass12\" ,\"pass12\" ,\"Dave\" ,\"Mill\" ,\"1276,Troy\" ,\"2015-12-17\" , \"1567\",\"1567\")";
	 String sql14 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj178@wayne.edu\",\"pass13\" ,\"pass13\" ,\"Kathy\" ,\"Miller\" ,\"13,Troy\" ,\"2015-12-17\" , \"1465\",\"1465\")";
	 String sql15 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj175@wayne.edu\",\"pass14\" ,\"pass14\" ,\"Laks\" ,\"Narayanan\" ,\"12,Novi\" ,\"2015-12-17\" , \"14578\",\"13456\")";
	 String sql16 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj176@wayne.edu\",\"pass15\" ,\"pass15\" ,\"Cheng\" ,\"Yang\" ,\"1245,Troy\" ,\"2015-12-17\" , \"12356\",\"16789\")";
	 String sql17 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj172@wayne.edu\",\"pass1235\" ,\"pass1235\" ,\"Preeti\" ,\"Mano\" ,\"167,Troy\" ,\"2015-12-17\" , \"134\",\"178\")";
	 String sql18 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj173@wayne.edu\",\"pass1237\" ,\"pass1237\" ,\"Somu\" ,\"Kris\" ,\"183,Troy\" ,\"2015-12-17\" , \"16789\",\"15432\")";
	 String sql19 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"hj174@wayne.edu\",\"pass1236\" ,\"pass1236\" ,\"Kris\" ,\"Shree\" ,\"192,Troy\" ,\"2015-12-17\" , \"1789\",\"1678\")";
    //String sql20 = "UPDATE Student set Address=\"123 New Main Street, Troy, MI 48083\" WHERE Name=\"Shiyong Lu\"";
	 String sql20 = "insert into pps(ppsprice) values (\"1000000\")";
	 String sql21 = "insert into follow(emailid, followerid, followdate) values (\"hj174@wayne.edu\",\"hj176@wayne.edu\" ,\"2015-12-17\")";
	 String sql22 = "insert into follow(emailid, followerid, followdate) values (\"hj176@wayne.edu\",\"hj178@wayne.edu\" ,\"2010-09-16\")";
	 String sql23 = "insert into follow(emailid, followerid, followdate) values (\"hj178@wayne.edu\",\"hj175@wayne.edu\" ,\"2011-08-15\")";
	 String sql24 = "insert into follow(emailid, followerid, followdate) values (\"hj175@wayne.edu\",\"hj176@wayne.edu\" ,\"2012-07-14\")";
	 String sql25 = "insert into follow(emailid, followerid, followdate) values (\"hj171@wayne.edu\",\"hj174@wayne.edu\" ,\"2015-05-13\")";
	 String sql26 = "insert into follow(emailid, followerid, followdate) values (\"hj174@wayne.edu\",\"hj173@wayne.edu\" ,\"2014-06-12\")";
	 String sql27 = "insert into follow(emailid, followerid, followdate) values (\"hj173@wayne.edu\",\"hj172@wayne.edu\" ,\"2013-04-11\")";
	 String sql28 = "insert into follow(emailid, followerid, followdate) values (\"hj172@wayne.edu\",\"hj171@wayne.edu\" ,\"2016-03-10\")";
	 String sql29 = "insert into follow(emailid, followerid, followdate) values (\"hi9643@wayne.edu\",\"hj173@wayne.edu\" ,\"2017-02-09\")";
	 String sql30 = "insert into follow(emailid, followerid, followdate) values (\"hj1786@wayne.edu\",\"hj178@wayne.edu\" ,\"2014-01-08\")";
	 String sql31 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123456\",\"Buy\" ,\"1000\" ,\"200\" ,\"800\" ,\"hj174@wayne.edu\" , \"hj176@wayne.edu\")";
     String sql32 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123455\",\"Sell\" ,\"1000\" ,\"300\" ,\"700\" ,\"hj175@wayne.edu\" , \"hj176@wayne.edu\")";
	 String sql33 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123457\",\"Withdraw\" ,\"100\" ,\"0\" ,\"0\" ,\"hj171@wayne.edu\" , \"hj171@wayne.edu\")";
	 String sql34 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123458\",\"Deposit\" ,\"1000\" ,\"0\" ,\"0\" ,\"hj174@wayne.edu\" , \"hj174@wayne.edu\")";
	 String sql35 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123459\",\"Transfer\" ,\"500\" ,\"200\" ,\"100\" ,\"hj173@wayne.edu\" , \"hj178@wayne.edu\")";
	 String sql36 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123460\",\"Buy\" ,\"900\" ,\"200\" ,\"700\" ,\"hj174@wayne.edu\" , \"hj178@wayne.edu\")";
	 String sql37 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123461\",\"Sell\" ,\"200\" ,\"200\" ,\"100\" ,\"hj174@wayne.edu\" , \"hj176@wayne.edu\")";
	 String sql38 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123462\",\"Buy\" ,\"800\" ,\"100\" ,\"600\" ,\"hj174@wayne.edu\" , \"hj176@wayne.edu\")";
	 String sql39 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123463\",\"Withdraw\" ,\"250\" ,\"0\" ,\"0\" ,\"hj1786@wayne.edu\" , \"hj1786@wayne.edu\")";
	 String sql40 = "insert into transaction(transid, transname, dollaramt, PPSamt, PPSbal, fromuser, touser) values (\"123464\",\"Deposit\" ,\"150\" ,\"200\" ,\"800\" ,\"hj173@wayne.edu\" , \"hj173@wayne.edu\")";
    

    try {
    	System.out.println("Enter Try");
      //System.out.println("Select a table and then print out its content.");
      // This will load the MySQL driver, each DB has its own driver
      // Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/testdb?"
              + "useSSL=false&user=john&password=pass1234");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      statement.executeUpdate(sql2);
      System.out.println("sql2 executed");

      statement.executeUpdate(sql3);
      System.out.println("sql3 executed");
	  statement.executeUpdate(sql4);
      System.out.println("sql4 executed");
	  statement.executeUpdate(sql1);
      System.out.println("sql5 executed");


      //preparedStatement = connect
      //    .prepareStatement(sql3);
      //preparedStatement.setString(1, "Shiyong Lu");
      //preparedStatement.setString(2, "123 Main Street, Troy, MI 48083");
      //preparedStatement.setString(3, "Senior");
      //preparedStatement.executeUpdate();
      
      statement.executeUpdate(sql5);
	  //statement.executeUpdate(sql9);
	  statement.executeUpdate(sql6);
	  System.out.println("sql16 executed");
	  statement.executeUpdate(sql7);
	  System.out.println("sql17 executed");
	  statement.executeUpdate(sql8);
	  System.out.println("sql18 executed");

      // see the results 
	  
	  System.out.println("After the insert statement for Users is executed.");
	  System.out.println("sql9 executed");
	  statement.executeUpdate(sql10);
	  System.out.println("sql10 executed");
	  statement.executeUpdate(sql11);
	  System.out.println("sql11 executed");
	  statement.executeUpdate(sql12);
	  System.out.println("sql12 executed");
	  statement.executeUpdate(sql13);
	  System.out.println("sql13 executed");
	  statement.executeUpdate(sql14);
	  System.out.println("sql14 executed");
	  statement.executeUpdate(sql15);
	  System.out.println("sql15 executed");
	  statement.executeUpdate(sql16);
	  System.out.println("sql16 executed");
	  statement.executeUpdate(sql17);
	  System.out.println("sql17 executed");
	  statement.executeUpdate(sql18);
	  System.out.println("sql18 executed");
	  statement.executeUpdate(sql19);
	  System.out.println("sql19 executed");
      resultSet = statement.executeQuery("select * from users");
      writeResultSet_table1(resultSet);
	  
	  System.out.println("After the insert statement for Follow is executed.");
	  statement.executeUpdate(sql21);
	  System.out.println("sql21 executed");
	  statement.executeUpdate(sql22);
	  System.out.println("sql22 executed");
	  statement.executeUpdate(sql23);
	  System.out.println("sql23 executed");
	  statement.executeUpdate(sql24);
	  System.out.println("sql24 executed");
	  statement.executeUpdate(sql25);
	  System.out.println("sql25 executed");
	  statement.executeUpdate(sql26);
	  System.out.println("sql26 executed");
	  statement.executeUpdate(sql27);
	  System.out.println("sql27 executed");
	  statement.executeUpdate(sql28);
	  System.out.println("sql28 executed");
	  statement.executeUpdate(sql29);
	  System.out.println("sql29 executed");
	  statement.executeUpdate(sql30);
	  System.out.println("sql30 executed");	  
	  resultSet = statement.executeQuery("select * from Follow");
      writeResultSet_table2(resultSet);
	  
	  System.out.println("After the insert statement for PPS is executed.");
	  statement.executeUpdate(sql20);
	  resultSet = statement.executeQuery("select * from PPS");
      writeResultSet_table3(resultSet);
	  
	  System.out.println("After the insert statement for Transaction is executed.");
	  statement.executeUpdate(sql31);
	  System.out.println("sql31 executed");
	  statement.executeUpdate(sql32);
	  System.out.println("sql32 executed");
	  statement.executeUpdate(sql33);
	  System.out.println("sql33 executed");
	  statement.executeUpdate(sql34);
	  System.out.println("sql34 executed");
	  statement.executeUpdate(sql35);
	  System.out.println("sql35 executed");
	  statement.executeUpdate(sql36);
	  System.out.println("sql36 executed");
	  statement.executeUpdate(sql37);
	  System.out.println("sql37 executed");
	  statement.executeUpdate(sql38);
	  System.out.println("sql38 executed");
	  statement.executeUpdate(sql39);
	  System.out.println("sql39 executed");
	  statement.executeUpdate(sql40);
	  System.out.println("sql40 executed");	
	 
	  resultSet = statement.executeQuery("select * from Transaction");
      writeResultSet_table4(resultSet);

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

  private static void writeResultSet_table1(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    System.out.println("print result from a table..");
    while (resultSet.next()) {
      System.out.println("Inside resultSet.next()");

    	// It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String id = resultSet.getString("emailid");
      String name = resultSet.getString("fName");
      String address = resultSet.getString("Address");
      String birthday = resultSet.getString("birthday");
	  String PPSbal = resultSet.getString("PPSbalance");
	  String Dollarbal = resultSet.getString("Dollarbal");
      System.out.println("emailid: " + id);
      System.out.println("name: " + name);
      System.out.println("address: " + address);
      System.out.println("birthday: " + birthday);
	  System.out.println("PPSbal: " + PPSbal);
	  System.out.println("Dollarbal: " + Dollarbal);
	  System.out.println("");
	  //String follower = resultSet.getString("followerid");
	  //String followdate = resultSet.getString("followdate");
	 // System.out.println("followerid: " + follower);
	 // System.out.println("followdate: " + followdate);
	  System.out.println("");
	  //Integer ppsprice = resultSet.getInt("ppsprice");
	  //System.out.println("ppsprice: " + ppsprice);
	  System.out.println("");
	  //Integer transid = resultSet.getInt("transid");
	  //String transname = resultSet.getString("transname");
	  //Integer dollaramt = resultSet.getInt("dollaramt");
	  //Integer PPSamt = resultSet.getInt("PPSamt");
	  //Integer PPSbal1 = resultSet.getInt("PPSbal");
	  //String fromuser = resultSet.getString("fromuser");
	  //String touser = resultSet.getString("touser");
	  //System.out.println("transid: " + transid);
	  //System.out.println("transname: " + transname);
	  //System.out.println("dollaramt: " + dollaramt);
	  //System.out.println("PPSamt: " + PPSamt);
	  //System.out.println("PPSbal: " + PPSbal1);
	  //System.out.println("fromuser: " + fromuser);
	  //System.out.println("touser: " + touser);
	  //System.out.println("");;
    }
  }

  private static void writeResultSet_table2(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    System.out.println("print result from a table..");
    while (resultSet.next()) {
      System.out.println("Inside resultSet.next()");

    	// It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);

	  String follower = resultSet.getString("followerid");
	  String followdate = resultSet.getString("followdate");
	  System.out.println("followerid: " + follower);
	  System.out.println("followdate: " + followdate);
	  System.out.println("");

    }
  }

  private static void writeResultSet_table3(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    System.out.println("print result from a table..");
    while (resultSet.next()) {
      System.out.println("Inside resultSet.next()");

    	// It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);

	  Integer ppsprice = resultSet.getInt("ppsprice");
	  System.out.println("ppsprice: " + ppsprice);
	  System.out.println("");
    }
  }

  private static void writeResultSet_table4(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    System.out.println("print result from a table..");
    while (resultSet.next()) {
      System.out.println("Inside resultSet.next()");

    	// It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
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
	  System.out.println("");;
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
