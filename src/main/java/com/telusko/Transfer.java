package com.telusko;
import java.sql.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/TransferServlet")
public class Transfer extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
	 HttpSession session=req.getSession();
	 double amt=Double.parseDouble(req.getParameter("amount"));
	 String rec_account_str=(String)req.getParameter("recipient_account_no");
	 long rec_account=Long.parseLong(rec_account_str);
	 session.setAttribute("recipient_no", rec_account);
	 session.setAttribute("amt", amt);
	 String sender_account_str=(String)session.getAttribute("account_no");
	 long sender_account_no=Long.parseLong(sender_account_str);
	 String pin=req.getParameter("transaction_pin");
	 try(Connection con=BankingSystem.getconnection())
	 {
		 AccountManager ob=new AccountManager(con);
		 int rowsAffected=ob.transferMoney(sender_account_no,rec_account,amt,pin);
	     if(rowsAffected==1)
	     {
	    	double balance=(double)session.getAttribute("balance")-amt;
	    	session.setAttribute("balance", balance);
	    	res.sendRedirect("transferSuccessfull.jsp"); 
	     }
	     else
	     {
	    	res.sendRedirect("transferFailed.jsp"); 
	     }
	 }
	 catch(SQLException e)
	 {
		 res.getWriter().println("Unable To Establish Connection To Database! "+e.getMessage());
	 }
	}

}
