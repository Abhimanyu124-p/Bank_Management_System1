package com.telusko;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitTicketServlet")
public class TicketServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        // 1. Grab data from the JSP Form (including hidden fields)
        String accountNo = session.getAttribute("account_no").toString();
        String userEmail = request.getParameter("email");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        // -------------------------------------------------------------
        // [NOTE: Save the data to your DB here using JDBC as usual]
        // -------------------------------------------------------------

        // 2. Configure Email Server Settings (e.g., Using Gmail's SMTP Server)
        String host = "smtp.gmail.com";
        final String adminEmail = "abhimanyukumar07051@gmail.com";      // Your bank's system email
        final String adminPassword = "hhqh bysg fxan pqae";   // Your email app password

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Ensures security Encryption

        // 3. Create an Authenticated Email Session
        Session mailSession = Session.getInstance(properties, new AuthenticatedMail(adminEmail, adminPassword));

        try {
            // 4. Draft the Email Message
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(adminEmail));
            // Send the email to your bank's support desk inbox
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("abhimanyukumar07051@gmail.com"));
            
            // Email Subject Line
            message.setSubject("🚨 New Support Ticket: " + category);
            
            // Email Body Content
            String emailBody = "<h3>New Support Request Received</h3>"
                    + "<p><strong>Customer Email:</strong> " + userEmail + "</p>"
                    + "<p><strong>Account Number:</strong> " + accountNo + "</p>"
                    + "<p><strong>Category:</strong> " + category + "</p>"
                    + "<p><strong>Issue Description:</strong> " + description + "</p>";
                    
            message.setContent(emailBody, "text/html");

            // 5. Fire off the Email!
            Transport.send(message);
            
            // Redirect user to a success page
            response.sendRedirect("Dashboard.jsp?msg=TicketSubmitted");

        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().println("System saved your ticket, but failed to alert admin via email.");
        }
    }
}

// Simple Helper Class to bundle credentials for security authentication
class AuthenticatedMail extends Authenticator {
    private String email;
    private String password;
    public AuthenticatedMail(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(email, password);
    }
}