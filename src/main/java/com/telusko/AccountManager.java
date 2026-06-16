package com.telusko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AccountManager {
    private Connection con;
    public AccountManager(Connection con) {
        this.con = con;
    }

    public int creditMoney(long account_no,double amt,String pin) throws SQLException {
        try {
        	con.setAutoCommit(false);
            if (account_no != 0L) {
                String query = "select account_id from accounts where account_number=? and pin_no=?;";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setLong(1, account_no);
                stmt.setString(2, pin);
                try(ResultSet rs = stmt.executeQuery()){
                    if (!rs.next()){
                    	System.out.println("Invalid Security Pin");
                        con.rollback();
                    	return -1;
                    }
                	int account_id=rs.getInt("account_id");
                    String query2 = "update accounts set balance=balance+? where account_number=?;";
                    try(PreparedStatement stmt1 = con.prepareStatement(query2)){
                    stmt1.setDouble(1, amt);
                    stmt1.setLong(2, account_no);
                    int rowsAffected = stmt1.executeUpdate();
                    if (rowsAffected > 0) {
                    	
                    	String type="credit";
                		String remarks="Credited by you";
                		String query1="insert into transactions(account_id,type,amount,remarks) values(?,?,?,?);";
                		try(PreparedStatement stmt2=con.prepareStatement(query1)){
                			stmt2.setInt(1, account_id);
                			stmt2.setString(2,type);
                			stmt2.setDouble(3,amt);
                			stmt2.setString(4, remarks);
                			int rowsAffected1=stmt2.executeUpdate();
                			if(rowsAffected1>0)
                			{
                			  System.out.println("Rs. " + amt + " CREDITED SUCCESSFULLY..");
                              con.commit();
                              return account_id;
                			}
                		}
                    }
               }
                System.out.println("TRANSACTION FAILED!!");
                con.rollback();
                con.setAutoCommit(true);
                return -1;
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Absolute fallback protection recovery
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }

        con.setAutoCommit(true);
        return -1;
    }

    public int debitMoney(long account_no,double amt,String pin) throws SQLException {
    	 try {
         	con.setAutoCommit(false);
             if (account_no != 0L) {
                 String query = "select account_id from accounts where account_number=? and pin_no=?;";
                 PreparedStatement stmt = con.prepareStatement(query);
                 stmt.setLong(1, account_no);
                 stmt.setString(2, pin);
                 try(ResultSet rs = stmt.executeQuery()){
                     if (!rs.next()){
                     	System.out.println("Invalid Security Pin");
                         con.rollback();
                     	return -1;
                     }
                 	int account_id=rs.getInt("account_id");
                     String query2 = "update accounts set balance=balance-? where account_number=?;";
                     try(PreparedStatement stmt1 = con.prepareStatement(query2)){
                     stmt1.setDouble(1, amt);
                     stmt1.setLong(2, account_no);
                     int rowsAffected = stmt1.executeUpdate();
                     if (rowsAffected > 0) {
                     	
                 		String query1="insert into transactions(account_id,type,amount,remarks) values(?,?,?,?);";
                 		try(PreparedStatement stmt2=con.prepareStatement(query1)){
                 			stmt2.setInt(1, account_id);
                 			stmt2.setString(2,"Debit");
                 			stmt2.setDouble(3,amt);
                 			stmt2.setString(4,"Withdrawn Successfully");
                 			int rowsAffected1=stmt2.executeUpdate();
                 			if(rowsAffected1>0)
                 			{
                 			  System.out.println("Rs. " + amt + " DEBITED SUCCESSFULLY..");
                               con.commit();
                               return account_id;
                 			}
                 		}
                     }
                }
                 System.out.println("TRANSACTION FAILED!!");
                 con.rollback();
                 con.setAutoCommit(true);
                 return -1;
             }
             }
         } catch (SQLException e) {
             e.printStackTrace();
             try {
                 if (con != null) {
                     con.rollback(); // Absolute fallback protection recovery
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
             throw e;
         }

         con.setAutoCommit(true);
         return -1;
    }

    public int transferMoney(long sender_account_no, long rec_account, double amt, String pin) throws SQLException {
        try {
            // 1. Start ACID Transaction
            this.con.setAutoCommit(false);

            // 2. Validate Receiver Account
            String query = "select * from accounts where account_number=?;";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setLong(1, rec_account);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("RECEIVER'S ACCOUNT DOES NOT EXIST!!");
                        con.setAutoCommit(true);
                        return -1;
                    }
                    int rec_account_id = rs.getInt("account_id");

                    // 3. Validate Sender Credentials (Account & PIN)
                    String query1 = "select * from accounts where account_number=? and pin_no=?;";
                    try (PreparedStatement stmt1 = this.con.prepareStatement(query1)) {
                        stmt1.setLong(1, sender_account_no);
                        stmt1.setString(2, pin);
                        try (ResultSet rs1 = stmt1.executeQuery()) {
                            if (!rs1.next()) {
                                System.out.println("INVALID SECURITY PIN!!");
                                con.setAutoCommit(true);
                                return -1;
                            }

                            int send_account_id = rs1.getInt("account_id");
                            double senderBalance = rs1.getDouble("balance");

                            // 4. Balance Check
                            if (senderBalance < amt) {
                                System.out.println("INSUFFICIENT BALANCE!");
                                con.setAutoCommit(true);
                                return -1;
                            }

                            // 5. Prepare SQL Execution Statements
                            String debit_query = "update accounts set balance=balance-? where account_number=?;";
                            String credit_query = "update accounts set balance=balance+? where account_number=?;";
                            String transfer_query = "insert into transactions(account_id,type,amount,remarks) values(?,?,?,?);";
                            String receive_query = "insert into transactions(account_id,type,amount,remarks) values(?,?,?,?);";

                            try (PreparedStatement debit_stmt = con.prepareStatement(debit_query);
                                 PreparedStatement credit_stmt = con.prepareStatement(credit_query); 
                                 PreparedStatement transfer_stmt = con.prepareStatement(transfer_query);
                                 PreparedStatement receive_stmt = con.prepareStatement(receive_query)) {
                                
                                // Bind Balance Updates
                                debit_stmt.setDouble(1, amt);
                                debit_stmt.setLong(2, sender_account_no);
                                
                                credit_stmt.setDouble(1, amt);
                                credit_stmt.setLong(2, rec_account);
                                
                                // Bind Sender Transaction History Record
                                transfer_stmt.setInt(1, send_account_id);
                                transfer_stmt.setString(2, "Transfer");
                                transfer_stmt.setDouble(3, amt);
                                transfer_stmt.setString(4, "Rs. " + amt + " transferred successfully to Account No. " + rec_account); // Fixed: setString
                                
                                // Bind Receiver Transaction History Record
                                receive_stmt.setInt(1, rec_account_id);
                                receive_stmt.setString(2, "Credit");
                                receive_stmt.setDouble(3, amt);
                                receive_stmt.setString(4, "Rs. " + amt + " received from Account No. " + sender_account_no); // Fixed: setString

                                // Execute Balance Changes
                                int rowsAffected = debit_stmt.executeUpdate();
                                int rowsAffected1 = credit_stmt.executeUpdate();
                                
                                // Execute Logs
                                int rowsAffected2 = transfer_stmt.executeUpdate(); // Fixed: Removed from try() condition
                                int rowsAffected3 = receive_stmt.executeUpdate();

                                // 6. Final Transaction Validation Verification
                                if (rowsAffected > 0 && rowsAffected1 > 0 && rowsAffected2 > 0 && rowsAffected3 > 0) {
                                    System.out.println("Rs. " + amt + " TRANSFERRED SUCCESSFULLY TO ACCOUNT NO.: " + rec_account);
                                    this.con.commit(); // Save everything permanently
                                    this.con.setAutoCommit(true);
                                    return 1;
                                } else {
                                    System.out.println("TRANSACTION FAILED! ROLLING BACK...");
                                    con.rollback(); // Safe undo
                                    this.con.setAutoCommit(true);
                                    return -1;
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Encountered: " + e.getMessage());
            try {
                if (con != null) {
                    con.rollback(); // Fallback safety rollback
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // Guarantee clean state restoration
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void getBalance(long account_no,String pin) throws SQLException {
        
        try {
            String query = "Select balance from accounts where account_number=? and security_pin=?;";
            PreparedStatement stmt = this.con.prepareStatement(query);
            stmt.setLong(1, account_no);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("AVAILABLE BALANCE: Rs." + balance);
            } else {
                System.out.println("INVALID PIN!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
