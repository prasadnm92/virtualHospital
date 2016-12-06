/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import appointment.Days.Day;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.LocalTime;

/**
 *
 * @author user
 */
public class viewappointments {

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
            out.println("<title>Servlet viewappointments</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet viewappointments at " + request.getContextPath() + "</h1>");
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
   

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   
    @SuppressWarnings("empty-statement")
  //  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    //       throws ServletException, IOException {
      public Appointment fetchAppointment(String patientname) throws IOException
      {
        //processRequest(request, response);
        
        String patient = patientname;
        String did = fetchdoctor(patient);
        Day d;
        
        Appointment ap = fetchfromdb(did);
       
        
        //String apday = request.getParameter("day");
         return ap;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
  
    public String fetchdoctor(String patient) {
        
        ResultSet rs;
        String did = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT did FROM patients WHERE username=?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, patient);
        
        
        rs = st.executeQuery();
        
        if(rs.first())
            did = rs.getString(1);
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    return did;
        
    }

    public Appointment fetchfromdb(String did) throws IOException {
        
         ResultSet rs;
         Appointment a = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT serialized_object FROM appointments WHERE doctor_id = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, did);
        
        
        rs = st.executeQuery();
        rs.next();
        
         byte[] buf = rs.getBytes(1);
        ObjectInputStream objectIn = null;
        if (buf != null)
             objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
 
        Object appointmentObject = objectIn.readObject();
         a = (Appointment)appointmentObject;
        
        rs.close();
  
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
   
        return a;
    
    }

}
