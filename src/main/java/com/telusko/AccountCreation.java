package com.telusko;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Accountservlet")
public class AccountCreation extends HttpServlet {

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        
        // 1. Ensure session safety
        HttpSession session = req.getSession(true);
        
        String name = req.getParameter("fullname");
        String pin = req.getParameter("pin");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email");
        String phone_no = req.getParameter("phone");
        String type = req.getParameter("accountType");
        String branch = req.getParameter("branch");
        
        double ini_deposit = 0.0;
        try {
            ini_deposit = Double.parseDouble(req.getParameter("deposit"));
        } catch (NumberFormatException | NullPointerException e) {
            showErrorPage(req, res, "Invalid deposit amount entered.");
            return;
        }

        try (Connection con = BankingSystem.getconnection()) {
            if (con == null) {
                showErrorPage(req, res, "Unable to establish database connection!");
                return;
            }

            String checkQuery = "SELECT users.user_id FROM users JOIN accounts ON users.user_id = accounts.user_id WHERE phone_no = ? OR email = ?";
            try (PreparedStatement stmt = con.prepareStatement(checkQuery)) {
                stmt.setString(1, phone_no);
                stmt.setString(2, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        showErrorPage(req, res, "An account already exists with this email or phone number!");
                        return;
                    }
                }
            }

            // 3. Extract the existing user_id mapped to this registration email
            int userid = -1;
            String userQuery = "SELECT user_id FROM users WHERE email = ?";
            try (PreparedStatement stmt1 = con.prepareStatement(userQuery)) {
                stmt1.setString(1, email);
                try (ResultSet rs1 = stmt1.executeQuery()) {
                    if (!rs1.next()) {
                        showErrorPage(req, res, "User Profile not found! Please register a primary user account first.");
                        return;
                    }
                    userid = rs1.getInt("user_id");
                }
            }

            // 4. Generate Account Number and Insert Record
            Accounts ob = new Accounts(con);
            long account_no = (long)(ob.generateAccountNumber());
            
            String insertQuery = "INSERT INTO accounts(user_id, account_number, balance, branch_name, ifsc_code, account_type, phone_no, gender, pin_no) VALUES(?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement stmt2 = con.prepareStatement(insertQuery)) {
                stmt2.setInt(1, userid);
                stmt2.setLong(2, account_no);
                stmt2.setDouble(3, ini_deposit);
                stmt2.setString(4, branch); 
                stmt2.setString(5, "SBI2026"); 
                stmt2.setString(6, type); 
                stmt2.setString(7, phone_no); 
                stmt2.setString(8, gender);
                stmt2.setString(9, pin);
                
                int rowsAffected = stmt2.executeUpdate();
                if (rowsAffected > 0) {
                    // Save down freshly generated attributes safely into the session state
                    session.setAttribute("account_no", account_no);
                    session.setAttribute("branch", branch);
                    session.setAttribute("ifsc", "SBI2026");
                    session.setAttribute("type", type);
                    session.setAttribute("pin", pin);
                    session.setAttribute("balance", ini_deposit);
                    session.setAttribute("phone_no", phone_no);
                    
                    res.sendRedirect("AccountCreated.jsp");
                    return;
                } else {
                    showErrorPage(req, res, "Account creation database tracking failed.");
                }
            }
            
        } catch (SQLException e) {
            showErrorPage(req, res, "Database error processed: " + e.getMessage());
        }
    }

    // Helper method to eliminate raw HTML string printing inside your core processing blocks
    private void showErrorPage(HttpServletRequest req, HttpServletResponse res, String message) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<div style=\"color: red;position: absolute;margin-bottom: 99vh;\"");
        out.println("<strong>Error:</strong> " + message);
        out.println("</div>");
        
        RequestDispatcher rd = req.getRequestDispatcher("CreateAccount.html");
        rd.include(req, res);
    }
}
