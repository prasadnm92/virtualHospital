/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import backend.patientregister;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class AppointmentAjax extends HttpServlet {

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
            out.println("<title>Servlet AppointmentAjax</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppointmentAjax at " + request.getContextPath() + "</h1>");
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
        
        String username = request.getSession(false).getAttribute("patientusername").toString();
        
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
             GetDoctor = "SELECT appointment_time FROM patient_appointment WHERE patientname = ?";
             
             PreparedStatement st = null;
        try {
            st = con.prepareStatement(GetDoctor);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st.setString(1, username);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             ResultSet results = null;
        try {
            results = st.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
        String time = null;
        try {
            if(results.first())
                try {
                    time = results.getString(1);
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentAjax.class.getName()).log(Level.SEVERE, null, ex);
                }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JSONObject obj = new JSONObject();
	obj.put("time", time);
	//obj.put("age", new Integer(100));
 
	//JSONArray list = new JSONArray();
	//list.add("msg 1");
	//list.add("msg 2");
	//list.add("msg 3");
 
	//obj.put("messages", list);
        
        //processRequest(request, response);
        response.setContentType("application/json");
// Get the printwriter object from response to write the required json object to the output stream      
        PrintWriter out = response.getWriter();
// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
        out.print(obj);
        out.flush();
        
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
        processRequest(request, response);
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

}
