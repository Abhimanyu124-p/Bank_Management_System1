package com.telusko;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewTransactions")
public class transactionServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account_no") == null) {
            response.sendRedirect("login.html");
            return;
        }
        
        String accountNo = (String) session.getAttribute("account_no");
        List<Map<String, Object>> transactionList = new ArrayList<>();
        
        String query = "SELECT type, amount, timestamp,remarks FROM transactions join accounts on transactions.account_id=accounts.account_id WHERE account_number = ? ORDER BY timestamp DESC";
        
        try (Connection con = BankingSystem.getconnection())
        {     
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            
            stmt.setString(1, accountNo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // 💡 LOOPING THROUGH THE RESULTSET VALUES
                while (rs.next()) {
                    Map<String, Object> transaction = new HashMap<>();
                    transaction.put("type", rs.getString("type"));
                    transaction.put("amount", rs.getDouble("amount"));
                    transaction.put("date", rs.getTimestamp("timestamp"));
                    transaction.put("description", rs.getString("remarks"));
                    transactionList.add(transaction);
                }
            }
            
            // Pass the populated list downstream to the JSP file
            request.setAttribute("transactions", transactionList);
            request.getRequestDispatcher("viewTransactions.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error loading statement logs."+e.getMessage());
        }
    }
        catch(SQLException e)
        {
          response.getWriter().println("Unable to Establish Connection to Database"+e.getMessage());   	
        }
    }
}
