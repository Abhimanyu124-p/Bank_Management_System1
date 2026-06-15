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

    public int transferMoney(long sender_account_no,long rec_account,double amt,String pin) throws SQLException {
        try {
            this.con.setAutoCommit(false);
            String query = "select * from accounts where account_number=?;";
            try(PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setLong(1, rec_account);
            try(ResultSet rs = stmt.executeQuery()){
            if (rs.next()) {
                String query1 = "select * from accounts where account_number=? and pin_no=?;";
                try(PreparedStatement stmt1 = this.con.prepareStatement(query1)){
                stmt1.setLong(1, sender_account_no);
                stmt1.setString(2, pin);
                try(ResultSet rs1 = stmt1.executeQuery()){
                if (rs1.next()) {
                    if (rs1.getDouble("balance") >= amt) {
                        String debit_query = "update accounts set balance=balance-? where account_number=?";
                        String credit_query = "update accounts set balance=balance+? where account_number=?";
                        try(PreparedStatement debit_stmt = con.prepareStatement(debit_query);
                        PreparedStatement credit_stmt =con.prepareStatement(credit_query)){
                        debit_stmt.setLong(2, sender_account_no);
                        debit_stmt.setDouble(1, amt);
                        credit_stmt.setLong(2, rec_account);
                        credit_stmt.setDouble(1, amt);
                        int rowsAffected = debit_stmt.executeUpdate();
                        int rowsAffected1 = credit_stmt.executeUpdate();
                        if (rowsAffected1 > 0 && rowsAffected > 0) {
                            System.out.println("Rs. " + amt + " TRANSFERRED SUCCESSFULLY TO ACCOUNT NO.: " + rec_account);
                            this.con.commit();
                            this.con.setAutoCommit(true);
                            return 1;
                          }
                        System.out.println("TRANSACTION FAILED!");
                        con.rollback();
                        con.setAutoCommit(true);
                        return -1;
                     }
                    }
                    else {
                        System.out.println("INSUFFICIENT BALANCE!");
                        con.setAutoCommit(true);
                        return -1;
                    }
                } else {
                    System.out.println("INVALID SECURITY PIN!!");
                    con.setAutoCommit(true);
                    return -1;
                 }
                }
              }
            }
            else {
                System.out.println("RECEIVER'S ACCOUNT DOES NOT EXIST!!");
                con.setAutoCommit(true);
                return -1;
            }
           }
          }
         }
         catch (SQLException e) {
            System.out.println(e.getMessage());
          }

        con.setAutoCommit(true);
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
