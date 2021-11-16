/**

 The purpose of this file is to initialize the database by DELETING the tables ,recreating and populating it in Java at a local MySQL database server. 
     1) Execute each SQL statement in Java.  
     *
 * */
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import java.sql.Date;
//import java.util.Date
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.People;
import java.text.ParseException;
//import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/Initialize")

public class Initialize  {
  private static Connection connect = null;
  private static Statement statement = null;
  private static PreparedStatement preparedStatement = null;
  private static ResultSet resultSet = null;
  
  protected void connect_func() throws SQLException {
      if (connect == null || connect.isClosed()) {
          try {
              Class.forName("com.mysql.jdbc.Driver");
          } catch (ClassNotFoundException e) {
              throw new SQLException(e);
          }
          connect = (Connection) DriverManager
			      .getConnection("jdbc:mysql://127.0.0.1:3306/testdb?"
			          + "useSSL=false&user=john&password=pass1234");
          System.out.println(connect);
      }
  }
  
  public String totaldeposit(String fromUser) throws SQLException {
   	  System.out.println("Inside Initialize totaldeposit ");
	  //List<Transact> totaldeposit = new ArrayList<Transact>();        
      connect_func();      
   	  String sqldeposit = "select fromuser,sum(dollaramt) as total from transaction where fromuser= ? group by fromuser";       

   	  System.out.println(sqldeposit);
   	  System.out.println(fromUser);
          
      //statement =  (Statement) connect.createStatement();
      //ResultSet resultSet = statement.executeQuery(sqldeposit);
      preparedStatement = (PreparedStatement) connect.prepareStatement(sqldeposit);
      preparedStatement.setString(1, fromUser);
      ResultSet resultSet = preparedStatement.executeQuery();
      System.out.println(resultSet);
      resultSet.next();
   	  System.out.println("Print resultSet.getString = ");
      System.out.println(resultSet.getString("fromUser"));
      System.out.println(resultSet.getString(2));
      String result = resultSet.getString(2);
      preparedStatement.close();

      System.out.println("result = ");
      System.out.println(result);

      return result;     
  }

  public String dollarBalancet(String fromUser) throws SQLException {
   	  System.out.println("Inside Initialize totaldeposit ");
	  //List<Transact> totaldeposit = new ArrayList<Transact>();        
      connect_func();      
   	  String sqldeposit = "select emailid, dollarbal from users where emailid= ?";       

   	  System.out.println(sqldeposit);
   	  System.out.println(fromUser);
          
      //statement =  (Statement) connect.createStatement();
      //ResultSet resultSet = statement.executeQuery(sqldeposit);
      preparedStatement = (PreparedStatement) connect.prepareStatement(sqldeposit);
      preparedStatement.setString(1, fromUser);
      ResultSet resultSet = preparedStatement.executeQuery();
      System.out.println(resultSet);
      resultSet.next();
   	  System.out.println("Print resultSet.getString = ");
      System.out.println(resultSet.getString("emailid"));
      System.out.println(resultSet.getString(2));
      String result = resultSet.getString(2);
      preparedStatement.close();

      System.out.println("result = ");
      System.out.println(result);

      return result;     
  }

	public List<Transact> listtotalpps(String fromUser, String toUser) throws SQLException {
		  System.out.println("Inside listtotalpps..........");
	 List<Transact> listPps= new ArrayList<Transact>();        
	 connect_func();      
	 String sqlpps = "select emailid,PPSbalance from users where emailid IN ( ?, ?)";      
	 preparedStatement = (PreparedStatement) connect.prepareStatement(sqlpps);
	 preparedStatement.setString(1, fromUser);
	 preparedStatement.setString(2, toUser);
		  ResultSet resultSet = preparedStatement.executeQuery();
	 System.out.println("preparedStatement.executeQuery called");
	  
	 while (resultSet.next()) {
	     String emailid = resultSet.getString("emailid");
	     int totalpps = resultSet.getInt("PPSbalance");
	  	  System.out.println(emailid);
	  	  System.out.println(totalpps);                   
	     Transact totalPPS = new Transact(emailid,totalpps);
	     listPps.add(totalPPS);
	 }        
	 resultSet.close();
	 preparedStatement.close();         
	 disconnect();        
	 return listPps;
	}
	
	public String totalpps(String fromUser, String toUser) throws SQLException {
	 String sqlpps = "select emailid, PPSbalance from users where emailid IN ( ?, ?)";       
	 connect_func();      
	 preparedStatement = (PreparedStatement) connect.prepareStatement(sqlpps);
	 preparedStatement.setString(1, fromUser);
	 preparedStatement.setString(2, toUser);
	 ResultSet resultSet = preparedStatement.executeQuery();
	 resultSet.next();
		  //System.out.println(resultSet.getString("fromUser"));
	 String result;
	 result = resultSet.getString(1);
	 preparedStatement.close();
	 return result;     
	}


  public String totalwithdraw(String transName, String fromUser) throws SQLException {
      String sqlwithdraw = "select fromuser, sum(dollaramt) as total from transaction where fromuser= ? group by fromuser";       
      connect_func();      
      
      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlwithdraw);
      //preparedStatement.setString(1, transName);
      preparedStatement.setString(1, fromUser);
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
   	System.out.println("Print resultSet.getString = ");
      System.out.println(resultSet.getString("fromUser"));
      System.out.println(resultSet.getString(2));
      String result = resultSet.getString(2);
      preparedStatement.close();

      
     // resultSet = statement.executeQuery("select * from Transaction");
      //writeResultSet_table5(resultSet);
      //      disconnect();
      return result;     
  }
       
  public String totalbuy(String transName, String toUser) throws SQLException {
      String sqlbuy = "select touser, sum(PPSamt) as total from transaction where touser= ? group by touser";       
      connect_func();      
      
      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy);
      //preparedStatement.setString(1, transName);
      preparedStatement.setString(1, toUser);
      ResultSet resultSet = preparedStatement.executeQuery();
      resultSet.next();
   	System.out.println("Print resultSet.getString = ");
      System.out.println(resultSet.getString("toUser"));
      System.out.println(resultSet.getString(2));
      String result = resultSet.getString(2);
      preparedStatement.close();

      
     // resultSet = statement.executeQuery("select * from Transaction");
      //writeResultSet_table5(resultSet);
      //      disconnect();
      return result;     
  }
  
  
  protected void disconnect() throws SQLException {
      if (connect != null && !connect.isClosed()) {
      	connect.close();
      }
  }
  

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
                   " birthday DATE, " + 
                   " PPSbalance BIGINT, " +
                   " Dollarbal BIGINT, " +
                   " PRIMARY KEY ( emailid ))";  
	String sql6 = "CREATE TABLE IF NOT EXISTS Follow " +
                   "(emailid VARCHAR(50) not NULL, " +
                   " followerid VARCHAR(20), " + 
                   " followdate	datetime, " +
                   " FOREIGN KEY (emailid) REFERENCES Users(emailid ))"; 
	String sql7 = "CREATE TABLE IF NOT EXISTS PPS " +
                   "(ppsprice INTEGER)"; 
	String sql8 = "CREATE TABLE IF NOT EXISTS Transaction " +
                   "(transid INTEGER not NULL AUTO_INCREMENT, " +
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
     String sql10 = "insert into users(emailid, password, password2, fName, lname, Address, birthday, PPSbalance, Dollarbal) values (\"root\",\"pass1234\",\"pass1234\",\"root\" ,\"user\" ,\"143,Troy\" ,\"2015-12-17\" , \"1000000000000\",\"1000000\")";
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
	 String sql20 = "insert into pps(ppsprice) values (\"1\")";
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
      Class.forName("com.mysql.cj.jdbc.Driver");
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
 
 public boolean insertNewUser(People newPeople) throws SQLException {
 	connect_func();  
 	System.out.println("conn established");
 	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
 	String sql9 = "INSERT INTO users" +
	            "  (emailid, password,password2,fName, lname, address, birthday) VALUES " +
	            " (?, ?, ?, ?, ?, ?, ?);";
 	System.out.println("insert start");
 	System.out.println(sql9);
 	System.out.println(newPeople.password);
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql9);
		preparedStatement.setString(1, newPeople.emailid);
		preparedStatement.setString(2, newPeople.getpassword());
		preparedStatement.setString(3, newPeople.getpassword2());
		preparedStatement.setString(4, newPeople.fName);
		preparedStatement.setString(5, newPeople.lname);
		preparedStatement.setString(6, newPeople.address);
        java.util.Date utilDate;
        java.sql.Date sqlDate = null; 
//		try {
			utilDate = newPeople.birthday;
	        sqlDate = new java.sql.Date(utilDate.getTime());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		preparedStatement.setDate(7, sqlDate);
//		preparedStatement.executeUpdate();
		System.out.println(preparedStatement);
     boolean rowInserted = preparedStatement.executeUpdate() > 0;
     preparedStatement.close();
//     disconnect();
     return rowInserted;
 }     
 
 public boolean depositamt(Transact newtransact) throws SQLException {
	 	connect_func();  
	 	System.out.println("conn established");
	 	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	 	String sql = "INSERT INTO transaction" +
		            "  (transname, dollaramt,PPSamt,PPSbal, fromuser, touser) VALUES " +
		            " (?, ?, ?, ?, ?, ?);";
	 	System.out.println("insert start");
	 	System.out.println(sql);
	 	
			preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, newtransact.transname);
			preparedStatement.setInt(2, newtransact.dollaramt);
			preparedStatement.setInt(3, newtransact.ppsamt);
			preparedStatement.setInt(4, newtransact.ppsbal);
			preparedStatement.setString(5, newtransact.fuser);
			preparedStatement.setString(6, newtransact.tuser);
	        java.util.Date utilDate;
	        java.sql.Date sqlDate = null; 
//			try {
//				utilDate = newPeople.birthday;
//		        sqlDate = new java.sql.Date(utilDate.getTime());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

			//preparedStatement.setDate(7, sqlDate);
//			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
	     boolean rowInserted = preparedStatement.executeUpdate() > 0;
	     preparedStatement.close();
//	     disconnect();
	     return rowInserted;
	 }     
 
 
 public boolean withdrawamt(Transact newtransact) throws SQLException {
	 	connect_func();  
	 	System.out.println("conn established");
	 	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	 	String sql = "INSERT INTO transaction" +
		            "  (transname, dollaramt,PPSamt,PPSbal, fromuser, touser) VALUES " +
		            " (?, -?, ?, ?, ?, ?);";
	 	System.out.println("insert start");
	 	System.out.println(sql);
	 	
			preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
			preparedStatement.setString(1, newtransact.transname);
			preparedStatement.setInt(2, newtransact.dollaramt);
			preparedStatement.setInt(3, newtransact.ppsamt);
			preparedStatement.setInt(4, newtransact.ppsbal);
			preparedStatement.setString(5, newtransact.fuser);
			preparedStatement.setString(6, newtransact.tuser);
	        java.util.Date utilDate;
	        java.sql.Date sqlDate = null; 
//			try {
//				utilDate = newPeople.birthday;
//		        sqlDate = new java.sql.Date(utilDate.getTime());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

			//preparedStatement.setDate(7, sqlDate);
//			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
	     boolean rowInserted = preparedStatement.executeUpdate() > 0;
	     preparedStatement.close();
//	     disconnect();
	     return rowInserted;
	 }     
 
 public boolean buypps(Transact newtransact) throws SQLException {
	 	connect_func();  
	 	System.out.println("conn established");
	 	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	 	String sqlbuy = "INSERT INTO transaction" +
		            "  (transname, dollaramt,PPSamt,PPSbal, fromuser, touser) VALUES " +
		            " (?, ?, ?, ?, ?, ?);";

	 	System.out.println("buy start");
	 	System.out.println(sqlbuy);
	 	
	 	
			preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy);
			preparedStatement.setString(1, newtransact.transname);
			preparedStatement.setInt(2, newtransact.dollaramt);
			preparedStatement.setInt(3, newtransact.ppsamt);
			preparedStatement.setInt(4, newtransact.ppsbal);
			preparedStatement.setString(5, newtransact.fuser);
			preparedStatement.setString(6, newtransact.tuser);
			
			
	        java.util.Date utilDate;
	        java.sql.Date sqlDate = null; 
//			try {
//				utilDate = newPeople.birthday;
//		        sqlDate = new java.sql.Date(utilDate.getTime());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

			//preparedStatement.setDate(7, sqlDate);
//			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
	     boolean rowInserted = preparedStatement.executeUpdate() > 0;
	     preparedStatement.close();
//	     disconnect();
//	     return rowInserted;
		     
		     String sqlbuy2 = "UPDATE users SET PPSbalance = PPSbalance - ? WHERE emailid = 'root'";    
		     System.out.println(sqlbuy2);
		      connect_func();      
		      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy2);
		      preparedStatement.setInt(1, newtransact.ppsamt);
		      //preparedStatement.setString(2, newtransact.fuser);
		      System.out.println(preparedStatement);
		      preparedStatement.executeUpdate();
		      preparedStatement.close();
		      
		      String sqlbuy3 = "UPDATE users SET PPSbalance = PPSbalance + ? WHERE emailid = ?";    
			     System.out.println(sqlbuy3);
			      connect_func();      
			      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy3);
			      preparedStatement.setInt(1, newtransact.ppsamt);
			      preparedStatement.setString(2, newtransact.tuser);
			      System.out.println(preparedStatement);
			      preparedStatement.executeUpdate();
			      preparedStatement.close();
			      
			      String sqlbuy4 = "UPDATE pps SET ppsprice = ppsprice + ? WHERE ppsprice like '%%'";    
				     System.out.println(sqlbuy4);
				      connect_func();      
				      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy4);
				      preparedStatement.setInt(1, newtransact.ppsamt);
				      //preparedStatement.setString(2, newtransact.tuser);
				      System.out.println(preparedStatement);
				      preparedStatement.executeUpdate();
				      preparedStatement.close();
				      
				      String sqlbuy5 = "UPDATE users SET Dollarbal = Dollarbal-(SELECT * FROM pps WHERE ppsprice LIKE '%%') WHERE emailid = ?";    
					     System.out.println(sqlbuy5);
					      connect_func();      
					      preparedStatement = (PreparedStatement) connect.prepareStatement(sqlbuy5);
					      //preparedStatement.setInt(1, newtransact.ppsamt);
					      preparedStatement.setString(1, newtransact.tuser);
					      System.out.println(preparedStatement);
					      preparedStatement.executeUpdate();
					      preparedStatement.close();
					  			  				  			  
		     return rowInserted;
		 }
     

 public boolean transferpps(Transact newtransact) throws SQLException {
	 	connect_func();  
	 	System.out.println("conn established");
	 	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	 	String sqltransfer = "INSERT INTO transaction" +
		            "  (transname, dollaramt,PPSamt,PPSbal, fromuser, touser) VALUES " +
		            " (?, ?, ?, ?, ?, ?);";
	 	System.out.println("insert start");
	 	System.out.println(sqltransfer);
	 	
			preparedStatement = (PreparedStatement) connect.prepareStatement(sqltransfer);
			preparedStatement.setString(1, newtransact.transname);
			preparedStatement.setInt(2, newtransact.dollaramt);
			preparedStatement.setInt(3, newtransact.ppsamt);
			preparedStatement.setInt(4, newtransact.ppsbal);
			preparedStatement.setString(5, newtransact.fuser);
			preparedStatement.setString(6, newtransact.tuser);
	        java.util.Date utilDate;
	        java.sql.Date sqlDate = null; 
//			try {
//				utilDate = newPeople.birthday;
//		        sqlDate = new java.sql.Date(utilDate.getTime());
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}

			//preparedStatement.setDate(7, sqlDate);
//			preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
	     boolean rowInserted = preparedStatement.executeUpdate() > 0;
	     preparedStatement.close();
//	     disconnect();
	     
	     String sqltransferupd1 = "UPDATE users SET PPSbalance = PPSbalance - ? WHERE emailid = ?";    
	     System.out.println(sqltransferupd1);
	      connect_func();      
	      preparedStatement = (PreparedStatement) connect.prepareStatement(sqltransferupd1);
	      preparedStatement.setInt(1, newtransact.ppsamt);
	      preparedStatement.setString(2, newtransact.fuser);
	      System.out.println(preparedStatement);
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	      
	      String sqltransferupd2 = "UPDATE users SET PPSbalance = PPSbalance + ? WHERE emailid = ?";    
		     System.out.println(sqltransferupd2);
		      connect_func();      
		      preparedStatement = (PreparedStatement) connect.prepareStatement(sqltransferupd2);
		      preparedStatement.setInt(1, newtransact.ppsamt);
		      preparedStatement.setString(2, newtransact.tuser);
		      System.out.println(preparedStatement);
		      preparedStatement.executeUpdate();
		      preparedStatement.close();
	     return rowInserted;
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
      Date birthday = resultSet.getDate("birthday");
	  String PPSbal = resultSet.getString("PPSbalance");
	  String Dollarbal = resultSet.getString("Dollarbal");
      System.out.println("emailid: " + id);
      System.out.println("name: " + name);
      System.out.println("address: " + address);
      System.out.println("birthday: " + birthday);
	  System.out.println("PPSbal: " + PPSbal);
	  System.out.println("Dollarbal: " + Dollarbal);
	  System.out.println("");
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
  
/*  private static void writeResultSet_table5(ResultSet resultSet) throws SQLException {
	    // ResultSet is initially before the first data set
	    System.out.println("print dollaramt after deposit ..");
	   
	    	// It is possible to get the columns via name
	      // also possible to get the columns via the column number
	      // which starts at 1
	      // e.g. resultSet.getSTring(2);

		  String fromUser = resultSet.getString("fromUser");
		  Integer dollaramt = resultSet.getInt("sum(dollaramt)");
		  System.out.println("fromUser: " + fromUser);
		  System.out.println("sum(dollaramt): " + dollaramt);
		  System.out.println("");

	    }
*/	  

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
 
