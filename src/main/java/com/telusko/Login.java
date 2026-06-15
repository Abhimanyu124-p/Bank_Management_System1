package com.telusko;
import java.sql.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
//import jakarta.annotation.*;
@WebServlet("/LoginServlet")
public class Login extends HttpServlet{
	@Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
	{
		String email=req.getParameter("useremail");
		String pass=req.getParameter("password");
		try (Connection con = BankingSystem.getconnection()) {
            if (con != null) {
                Users user = new Users(con);
                String mail = user.login(email, pass);
                if (mail!=null) {
                	String query="select full_name,balance,account_number,branch_name,pin_no from users left join accounts on users.user_id=accounts.user_id where email=?;";
                	try(PreparedStatement stmt=con.prepareStatement(query)){
                	stmt.setString(1, mail);
                	try(ResultSet rs=stmt.executeQuery()){
                	if(rs.next())
                	{
                	String name=rs.getString("full_name");
                	double balance=rs.getDouble("balance");
                	String account_no=rs.getString("account_number");
                    System.out.println("Logged in to bank management system");
                    HttpSession session=req.getSession();
                    session.setAttribute("email",mail);
                    session.setAttribute("pin", rs.getString("pin_no"));
                    session.setAttribute("username",name);
                    session.setAttribute("branch",rs.getString("branch_name"));
                    session.setAttribute("balance",balance);
                    session.setAttribute("ifsc", "SBI2026");
                    session.setAttribute("account_no", account_no);
                    res.sendRedirect("Dashboard.jsp");
                    return;
                	}
                	else {
                		 res.setContentType("text/html");	
                		 res.getWriter().println("<h1>Unable to fetch data of user to system!</h1>");
                		}
                	}
                  }
		        }
                else {
                	String query="select email from users where email=?;";
                	try(PreparedStatement stmt=con.prepareStatement(query)){
                	stmt.setString(1,email);
                	try(ResultSet rs=stmt.executeQuery()){
                	res.setContentType("text/html");
                	if(rs.next())
                	{
                	  res.getWriter().println("<h1>Invalid Password!</h1>");
                	   res.getWriter().println("<h2>Please Enter Correct Password To Login</h2>");
                	}
                	else
                	{
                		res.getWriter().println("<h1>User Does Not Exist! Please Register To Our Bank Management System First</h1>");
                    	res.getWriter().println("<a href='register.html'>REGISTER</a>");	
                	}
                   }
                  }
                 }
                } 
               else {
            	   res.setContentType("text/html");
                res.getWriter().println("<h1>Unable to establish connection!</h1>");
                }
         } 
		catch (SQLException e) {
			res.setContentType("text/html");
            res.getWriter().println("<h1>Database Error: " + e.getMessage() + "</h1>");
        }
		}

	}