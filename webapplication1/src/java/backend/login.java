/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class login{
public boolean loginCheck(String did, String password){
    String query;
    boolean login = false;
    ResultSet rs = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
        Connection con = null;
    try {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
    } catch (SQLException ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        Statement stmt = null;
    try {
        stmt = con.createStatement();
    } catch (SQLException ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
        query = "select * from doctors;";
    try {
        //query = "SELECT did, password FROM doctors WHERE did = " + did + " AND password=" + password+";";
        rs = stmt.executeQuery(query); 
    } catch (SQLException ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
    try { 
        login = rs.first();
    } catch (SQLException ex) {
        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
    }
 
    return login;
}
   
    /**
     *
     * @param args
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * 
     */
    public static void main(String args[]) throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException{
        
        login obj = new login();
        boolean login;
        login = obj.loginCheck("vhcar001", "DinahLance");
        System.out.println(login);
    }
}
