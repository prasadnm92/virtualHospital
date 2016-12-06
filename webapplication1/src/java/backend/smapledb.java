/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class smapledb {
    
     private String assigndoctor(String department) throws SQLException {
       
             String doctor;
             doctor = "vhcar001";
             
             try {
                 Class.forName("com.mysql.jdbc.Driver");
             } catch (ClassNotFoundException exk) {
                // Logger.getLogger(smapledb.class.getName()).log(Level.SEVERE, null, exk);
                // exk.printStackTrace();
             }
             
             Connection con = null;
             try {
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
             } catch (SQLException m) {
                 Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, m);
             }
             
             String GetDoctor;
             GetDoctor = "SELECT * FROM doctors WHERE department=?";
             
             /*
             
             CHECK_USER = "SELECT * FROM doctors WHERE did = ? AND password = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, did);
        st.setString(2, password);
             */
             
             PreparedStatement st = null;
             st = con.prepareStatement(GetDoctor, TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             st.setString(1, department);
             
             ResultSet results;
             results = st.executeQuery();
            
             int min = 100;
             int seniority = 0 ;
             String mindoctorname = "DinahLance";
             String did = null;
             /*
             Boolean records = results.next();
             if(!records){
                 System.out.println("no record");
                 System.exit(0);
             }
                 
             
             did = results.getString("name");
             */
         try {
             while(results.next()){
                 
                 int noofpatients = results.getInt("patientsenrolled");
                 int yearsofexperience = results.getInt("yearsofexperience");
                 
                 String id = results.getString("did");
                 String doctorname = results.getString("name");
                 System.out.println(doctorname);
                 
                 if(noofpatients < min){
                     min = noofpatients;
                     mindoctorname = doctorname;
                     seniority = yearsofexperience;
                     did = id;
                 }
                 else if(noofpatients == min){
                     if(yearsofexperience < seniority){
                         seniority =  yearsofexperience;
                         mindoctorname = doctorname;
                         did = id;
                     }
                 }
             }
         } catch (SQLException ex) {
             Logger.getLogger(smapledb.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        results.beforeFirst();
         try {
             while(results.next()){
                 String id = results.getString("did");
                 if(id.equals(did)){
                     int no = results.getInt("patientsenrolled");
                     no++;
                     results.updateInt("patientsenrolled", no);
                     results.updateRow();
                 }
             }
         } catch (SQLException enx) {
             Logger.getLogger(smapledb.class.getName()).log(Level.SEVERE, null, enx);
         }
             doctor = did;
             return doctor;
    }

    /**
     *
     * @param args
     * @throws java.sql.SQLException
     */
    public static void main(String args[]) throws SQLException{
         
         smapledb obj = new smapledb();
         String doctor;
         String dept ="cardiology"; 
         doctor = obj.assigndoctor(dept);
         System.out.println(doctor);
     }
}
    

