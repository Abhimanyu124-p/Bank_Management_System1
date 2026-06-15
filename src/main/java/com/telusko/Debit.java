package com.telusko;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

@WebServlet("/WithdrawServlet")
public class Debit extends HttpServlet{

	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		HttpSession session=req.getSession();
		  res.setContentType("text/html");
		  String pin=req.getParameter("transaction_pin");
		  String accountStr=(String)session.getAttribute("account_no");
		  long account_no=Long.parseLong(accountStr);
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
			int account_id=ob.debitMoney(account_no, amt, pin);
			if(account_id==-1)
			{
				res.sendRedirect("transactionFailed.jsp");
			}
			else
			{  
				double balance=(double)session.getAttribute("balance")-amt;
				session.setAttribute("amt", amt);
			    session.setAttribute("balance", balance);
				res.sendRedirect("withdrawSuccessfull.jsp");
			}
		  }
		  catch(SQLException e)
		  {
			  res.getWriter().println("<h1>Unable to establish Database Connection</h1>"+e.getMessage());
		  }
	}
}
