package com.telusko;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
@WebServlet("/logoutservlet")
public class Logout extends HttpServlet{

@Override protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException
{
 HttpSession session=req.getSession();
 if(session !=null)
 {
	 session.invalidate();
 }
 res.sendRedirect("Dashboard.jsp");
 }

}
