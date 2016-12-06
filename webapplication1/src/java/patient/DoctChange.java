/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patient;

import backend.patientregister;
import backend.smapledb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class DoctChange extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet chandeDoct</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet chandeDoct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
        
        String patientUserName = request.getSession(false).getAttribute("patientusername").toString();
        
        String Dept = request.getParameter("department");
        String Doct = request.getParameter("docname");
        System.out.println(Dept+"______________________________________________-----");
         System.out.println(Doct+"______________________________________________-----");
        
         if(Dept == null || Dept.isEmpty())
             Dept = null;
         if(Doct.isEmpty() || Doct == null)
             Doct = null;
         
         String currentdid = null;
                
        if(Dept != null && Doct == null ){
            
            System.out.println("1");
            currentdid = getDid(patientUserName);
            System.out.println(currentdid);
            reduceOne(currentdid);
            String did = null;
            try {
                did = assignDoctor(Dept);
                System.out.println(did);
            } catch (SQLException ex) {
                Logger.getLogger(DoctChange.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateInDb(Dept , did , patientUserName);
            
        }else if(Dept == null && Doct != null){
            System.out.println("2");
            currentdid = getDid(patientUserName);
            reduceOne(currentdid);
            increaseOne(Doct);
            updateInDb(Doct , patientUserName);
            //reflect it in doctor
        }else if(Dept != null && Doct != null){
            
             System.out.println("3");
            currentdid = getDid(patientUserName);
            System.out.println("3.1");
            reduceOne(currentdid);
            System.out.println("3.2");
            increaseOne(Doct);
            System.out.println("3.3");
            updateInDb(Dept , Doct ,patientUserName);
            System.out.println("3.4");
        }
        
          request.getRequestDispatcher("/personal.jsp").forward(request, response);
            
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

     private String assignDoctor(String department) throws SQLException {
       
             String doctor;
             doctor = null;
             
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
             
            
             PreparedStatement st = null;
             st = con.prepareStatement(GetDoctor, TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             st.setString(1, department);
             
             ResultSet results;
             results = st.executeQuery();
            
             int min = 100;
             int seniority = 0 ;
             String mindoctorname = "DinahLance";
             String did = null;
           
             
         try {
             while(results.next()){
                 
                 int noofpatients = results.getInt("patientsenrolled");
                 int yearsofexperience = results.getInt("yearsofexperience");
                 
                 String id = results.getString("did");
                 String doctorname = results.getString("name");
                 //System.out.println(doctorname);
                 
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

    private String getDid(String Username) {
        
        ResultSet rs;
        String did = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT did FROM patients WHERE username=?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, Username);
        
        
        rs = st.executeQuery();
        
        if(rs.first())
            did = rs.getString(1);
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    return did;
    }

   
    private void reduceOne(String currentdid) {
        
         ResultSet rs;
        String did = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT patientsenrolled FROM doctors WHERE did=?";
        java.sql.PreparedStatement st;
       // st = con.prepareStatement(CHECK_USER);
         st = con.prepareStatement(CHECK_USER, TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.setString(1, currentdid);
        
        
        rs = st.executeQuery();
        
        
        if(rs.next()){
                    // String id = rs.getString(1);
                     int no = rs.getInt("patientsenrolled");
                     no--;
                     rs.updateInt("patientsenrolled", no);
                     rs.updateRow();
                 }
     
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
        
            
    }

    private void updateInDb(String Dept, String did, String patientUserName) {
        
        
        
        ResultSet rs;
     
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "UPDATE patients SET department = ? , did = ? where username =?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, Dept);
        st.setString(2, did);
        st.setString(3, patientUserName);
        
        
        int count = st.executeUpdate();
       
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
        
        
    }

    private void increaseOne(String Doct) {
       
        System.out.println("entered1");
          ResultSet rs;
        String did = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT patientsenrolled,did FROM doctors WHERE did=?";
        java.sql.PreparedStatement st;
       // st = con.prepareStatement(CHECK_USER);
           st = con.prepareStatement(CHECK_USER ,TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        st.setString(1, Doct);
        
        
        rs = st.executeQuery();
        System.out.println("entered");
        
        if(rs.next()){
                    // String id = rs.getString(1);
                     if(rs.getString(2).equals(Doct)){
                         
                     int no = rs.getInt("patientsenrolled");
                     no++;
                     rs.updateInt("patientsenrolled", no);
                     rs.updateRow();
                     System.out.println("hello");
                         
                     }
                     
                 }
     
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
        
        e.printStackTrace();
    }
        
        
        
    }

    private void updateInDb(String Doct, String patientUserName) {
         
        ResultSet rs;
     
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "UPDATE patients SET did = ? where username =?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        //st.setString(1, Dept);
        st.setString(1, Doct);
        st.setString(2, patientUserName);
        
        
        int count = st.executeUpdate();
       
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    }

}
