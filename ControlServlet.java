package bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.People;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
//import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * ControlServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Initialize initialize;

    public static class TotalDeposit {
		public String fUser;
    	public String totalAmount;
    	public TotalDeposit(String fuser2, String tDeposit) {
			// TODO Auto-generated constructor stub
    		this.fUser = fuser2;
        	this.totalAmount = tDeposit;
		}
    }
    
    public static class TotalWithdraw {
		public String fUser;
    	public String totalAmount;
    	public TotalWithdraw(String fuser2, String tWithdraw) {
			// TODO Auto-generated constructor stub
    		this.fUser = fuser2;
        	this.totalAmount = tWithdraw;
		}
    }
    
    public void init() {
        initialize = new Initialize();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost started: 000000000000000000000000000");
        doGet(request, response);
        System.out.println("doPost finished: 11111111111111111111111111");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doGet started: 000000000000000000000000000");

        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {

            case "/new":
                System.out.println("The action is: new");
                showNewForm(request, response);
                break;
            case "/insert":
                System.out.println("The action is: insert");
            	   insertPeople(request, response);
                break;
            case "/deposit":
                System.out.println("The action is: deposit");
            	   depositamt(request, response);
                break;
            case "/withdraw":
                System.out.println("The action is: withdraw");
            	   withdrawamt(request, response);
                break;
            case "/buy":
                System.out.println("The action is: buy");
            	   buypps(request, response);
                break;
//            case "/sell":
//                System.out.println("The action is: sell");
//            	   sellpps(request, response);
//                break; 
            case "/transfer":
                System.out.println("The action is: transfer");
            	   transferpps(request, response);
                break;
            default:
                System.out.println("Not sure which action, we will treat it as the dollaramt action");           	
                break;

            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        System.out.println("doGet finished: 111111111111111111111111111111111111");
    }

    private String totaldeposit(String transName, String fromUser)
    {
        System.out.println("totaldeposit started: 00000000000000000000000000000000000");

        String totalDeposit = null;
        try {
        totalDeposit = initialize.totaldeposit(fromUser);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return totalDeposit;
        
    }
    private String totalwithdraw(String transName, String fromUser) throws SQLException
    {
        System.out.println("totalwithdraw started: 00000000000000000000000000000000000");

        String totalWithdraw = null;
        totalWithdraw = initialize.totalwithdraw(transName, fromUser);
        return totalWithdraw;
        
    }
    
    private String totalbuy(String transName, String toUser) throws SQLException
    {
        System.out.println("totalbuy started: 00000000000000000000000000000000000 why does it get stuck here?");

        String totalBuy = null;
        totalBuy = initialize.totalbuy(transName, toUser);
        return totalBuy;
        
    }
    // to insert a people
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("showNewForm started: 000000000000000000000000000");

        RequestDispatcher dispatcher = request.getRequestDispatcher("Newuser.jsp");
        dispatcher.forward(request, response);
        System.out.println("The user sees the Newuser page now.");

        System.out.println("showNewForm finished: 1111111111111111111111111111111");
    }
    
    //to deposit money
    private void depositform(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("showNewForm started: 000000000000000000000000000");

        RequestDispatcher dispatcher = request.getRequestDispatcher("Action.jsp");
        dispatcher.forward(request, response);
        System.out.println("The user sees the Action page now.");

        System.out.println("depositform finished: 1111111111111111111111111111111");
    }

    private void buyppsform(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("showNewForm started: 000000000000000000000000000");

        RequestDispatcher dispatcher = request.getRequestDispatcher("buy.jsp");
        dispatcher.forward(request, response);
        System.out.println("The user sees the Buy page now.");

        System.out.println("Buyform finished: 1111111111111111111111111111111");
    }
    
    private void transferform(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("showNewForm started: 000000000000000000000000000");

        RequestDispatcher dispatcher = request.getRequestDispatcher("transfer.jsp");
        dispatcher.forward(request, response);
        System.out.println("The user sees the transfer page now.");

        System.out.println("Transferform finished: 1111111111111111111111111111111");
    }

    // after the data of a people are inserted, this method will be called to insert the new people into the DB

    private void insertPeople(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("insertPeople started: 000000000000000000000000000");
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String emailid=request.getParameter("emailid");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String fName = request.getParameter("fName");
        String lname = request.getParameter("lname");
        String address = request.getParameter("address");
        String birthString = request.getParameter("birthday");
        Date birthday = null;
		try {
			birthday = format.parse(birthString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        System.out.println("emailid:" + emailid + "password:" + password + "password2:" + password2 +"fName:" + fName + "lname:" + lname + ", address: "+address + ", birthday:" + birthday);
        People newPeople;
        newPeople = new People(emailid,password,password2,fName,lname, address, birthday);
        initialize.insertNewUser(newPeople);

        response.sendRedirect("SuccessRegistration.jsp");

        System.out.println("insertPeople finished: 11111111111111111111111111");
    }


    private void depositamt(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("inserttransaction started: 000000000000000000000000000");
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        //String transname=request.getParameter("transname");
        String transname = "Deposit";
        System.out.println(transname);
        int dollaramt = Integer.parseInt(request.getParameter("dollaramt"));
        System.out.println(dollaramt);
        String fuser = request.getParameter("fuser");
        System.out.println(fuser);
        //String tuser = request.getParameter("touser");      
        Transact newTransact;
        newTransact = new Transact(fuser);
        
        newTransact.transname = transname;
        newTransact.dollaramt = dollaramt;
        newTransact.ppsamt = 0;
        newTransact.ppsbal = 0;

        initialize.depositamt(newTransact);

		/*Retrieve Total Deposit*/
        String tDeposit;
        tDeposit = totaldeposit(transname, fuser);
          
          request.setAttribute("User", fuser);       
          request.setAttribute("totalDeposit", tDeposit);       
          RequestDispatcher dispatcher = request.getRequestDispatcher("deposit.jsp");       
          try {
        	 dispatcher.forward(request, response);
       	  } catch (ServletException e) {
        	 // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 } catch (IOException e) {
        	  // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 }
          
        System.out.println("depositamt finished: 11111111111111111111111111");
    }
    
    private void withdrawamt(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("inserttransaction started: 000000000000000000000000000");
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        //String transname=request.getParameter("transname");
        String transname = "Withdraw";
        System.out.println(transname);
        int dollaramt = Integer.parseInt(request.getParameter("dollaramt"));
        System.out.println(dollaramt);
       
        String fuser = request.getParameter("fuser");
        System.out.println(fuser);
        //String tuser = request.getParameter("touser");
        
        
        Transact newTransact;
        newTransact = new Transact(fuser);
        
        newTransact.transname = transname;
        newTransact.dollaramt = dollaramt;
        newTransact.ppsamt = 0;
        newTransact.ppsbal = 0;
        System.out.println("newTransact Creted");
        
        String totalDeposit = null;
        try {
        	totalDeposit = initialize.dollarBalancet(fuser);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        int dollarBalance = Integer.parseInt(totalDeposit);
        System.out.println(dollarBalance);
        System.out.println(dollaramt);

        if (dollarBalance >= dollaramt )
        {
	        initialize.withdrawamt(newTransact);
	
			/*Retrieve Total Withdraw*/
	        String tWithdraw;
	        tWithdraw = totalwithdraw(transname, fuser);
	          
	          request.setAttribute("User", fuser);       
	          request.setAttribute("totalwithdraw", tWithdraw);       
	          RequestDispatcher dispatcher = request.getRequestDispatcher("withdraw.jsp");       
	          try {
	        	 dispatcher.forward(request, response);
	       	  } catch (ServletException e) {
	        	 // TODO Auto-generated catch block
	        	  e.printStackTrace();
	        	 } catch (IOException e) {
	        	  // TODO Auto-generated catch block
	        	  e.printStackTrace();
	        	 }
	        System.out.println("withdrawamt finished");
        }
        else
        {
        	System.out.println("withdrawamt Failed: No sufficient Money");
        	response.sendRedirect("withdrawfail.jsp");
        }
    }
    


	//to buy pps from root
    private void buypps(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("Buy transaction started: 000000000000000000000000000");
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String transname = "Buy";
        System.out.println(transname);
       // int dollaramt = Integer.parseInt(request.getParameter("dollaramt"));
        //int dollaramt= tDeposit;
        //System.out.println(dollaramt);
        int ppsamt = Integer.parseInt(request.getParameter("ppsamt"));
        System.out.println(ppsamt);
        //int ppsbal = Integer.parseInt(request.getParameter("ppsbal"));
        //System.out.println(ppsbal);
        String fuser = request.getParameter("fuser");
        System.out.println(fuser);
        String tuser = request.getParameter("tuser");
        System.out.println(tuser);
        
        Transact newTransact;
        newTransact = new Transact(fuser);
        
        newTransact.transname = transname;
        newTransact.dollaramt = 0;
        newTransact.ppsamt = ppsamt;
        //newTransact.ppsbal = ppsbal;
        newTransact.tuser = tuser;
        newTransact.fuser = "root";
        
        initialize.buypps(newTransact);

		/*Retrieve Total Buy*/
        String tBuy;
        tBuy = totalbuy(transname, tuser);
          
          request.setAttribute("User", tuser);       
          request.setAttribute("totalBuy", tBuy);       
          RequestDispatcher dispatcher = request.getRequestDispatcher("buy_printout.jsp");       
          try {
        	 dispatcher.forward(request, response);
       	  } catch (ServletException e) {
        	 // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 } catch (IOException e) {
        	  // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 }
          
        System.out.println("depositamt finished: 11111111111111111111111111");
    }

    private void transferpps(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        System.out.println("Transfer transaction started: 000000000000000000000000000");
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String transname = "Transfer";
        System.out.println(transname);
       // int dollaramt = Integer.parseInt(request.getParameter("dollaramt"));
        //int dollaramt= tDeposit;
        //System.out.println(dollaramt);
        int ppsamt = Integer.parseInt(request.getParameter("ppsamt"));
        System.out.println(ppsamt);
        //int ppsbal = Integer.parseInt(request.getParameter("ppsbal"));
        //System.out.println(ppsbal);
        String fuser = request.getParameter("fuser");
        System.out.println(fuser);
        String tuser = request.getParameter("tuser");
        System.out.println(tuser);
        
        Transact newTransact;
        newTransact = new Transact(fuser);
        
        newTransact.transname = transname;
        newTransact.dollaramt = 0;
        newTransact.ppsamt = ppsamt;
        newTransact.ppsbal = 0;
        newTransact.fuser = fuser;
        newTransact.tuser = tuser;

        initialize.transferpps(newTransact);
        
        List<Transact> listPps = initialize.listtotalpps(fuser, tuser);
        request.setAttribute("listPps", listPps);    

		/*Retrieve Total Deposit*/
        //String tpps;
        //tpps = totalpps(fuser, tuser);
          
         /* request.setAttribute("User", fuser);
          request.setAttribute("tuser", tuser);
          request.setAttribute("totalpps", tpps);       */
          RequestDispatcher dispatcher = request.getRequestDispatcher("ppsbal.jsp");       
          try {
        	 dispatcher.forward(request, response);
       	  } catch (ServletException e) {
        	 // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 } catch (IOException e) {
        	  // TODO Auto-generated catch block
        	  e.printStackTrace();
        	 }
          
        System.out.println("Transfer PPS finished: 11111111111111111111111111");
    }





}
