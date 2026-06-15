package com.telusko;
import java.sql.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/DepositServlet")
public class Credit extends HttpServlet{
 protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
 {
  HttpSession session=req.getSession();
  res.setContentType("text/html");
  String pin=req.getParameter("transaction_pin");
  String accountNoStr = (String) session.getAttribute("account_no");
  long account_no = Long.parseLong(accountNoStr);
  double amt=0.0;
  try {
	  amt=Double.parseDouble(req.getParameter("amount"));
  }
  catch(NumberFormatException | NullPointerException e)
  {
	res.getWriter().println("Enter Valid Amount!"+e.getMessage());
	return;
  }
  try(Connection con=BankingSystem.getconnection())
  {
	AccountManager ob=new AccountManager(con);
	int account_id=ob.creditMoney(account_no, amt, pin);
	if(account_id==-1)
	{
		res.sendRedirect("transactionFailed.jsp");
	}
	else
	{   double balance=amt+(double)session.getAttribute("balance");
	    session.setAttribute("amt", amt);
		session.setAttribute("balance",balance);
		res.sendRedirect("transactionSuccessfull.jsp");
	}
  }
  catch(SQLException e)
  {
	  res.getWriter().println("<h1>Unable to establish Database Connection</h1>"+e.getMessage());
  }
  
 }
}
