package com.telusko;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Users {
    private Connection con;
    public Users(Connection con) {
        this.con = con;
    }

    public String login(String mail,String pass) {
        try {
            String query = "select * from users where email=? and password=? ;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, mail);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("LOGGED IN SUCCESSFULLY...");
                return mail;
            } else {
                System.out.println("INVALID CREDENTIALS!!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int register(String name,String mail,String pass) {
        if (user_exist(mail)) {
            System.out.println("USER ALREADY EXIST FOR THIS EMAIL ADDRESS!!");
            return 0;
        } else {
            String query = "Insert into users(full_name,email,password) values(?,?,?);";

            try {
                PreparedStatement stmt = this.con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, mail);
                stmt.setString(3, pass);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("USER REGISTERED SUCCESSFULLY..");
                    return rowsAffected;
                } else {
                    System.out.println("REGISTRATION FAILED!!");
                    return 0;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
       
    }

    public boolean user_exist(String email) {
        try {
            String query = "select * from users where email=?;";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}

