package com.telusko;
import java.sql.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/registerServlet")
public class Register extends HttpServlet{
	@Override protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		String name=req.getParameter("username");
		String email=req.getParameter("email");
		String pass=req.getParameter("password");
		String confirm_pass=req.getParameter("confirm-password");
		if(pass.equals(confirm_pass))
		{
			try (Connection con = BankingSystem.getconnection()) {
            if (con != null) {
                Users user = new Users(con);
                int rowsAffected = user.register(name, email, pass);

                if (rowsAffected == 1) {
                    out.println("<h1>User Registered Successfully!</h1>");
                    out.println("<input type='button' value='Go to Login' onclick=\"window.location.href='login.html';\">");
                } else {
                    out.println("<h1>User already exists!</h1>");
                }
            } else {
                out.println("<h1>Unable to establish connection!</h1>");
            }
        } catch (SQLException e) {
            out.println("<h1>Database Error: " + e.getMessage() + "</h1>");
        }
		}
		else {
			out.println("<h1>Passwords do not match!</h1>");
			return;
		}
	}

}
