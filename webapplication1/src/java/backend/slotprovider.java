/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import appointment.Appointment;
import appointment.Days.Day;
import backend.prescriptionstore;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author user
 */
public class slotprovider extends HttpServlet {

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
            out.println("<title>Servlet Slotsstore</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Slotsstore at " + request.getContextPath() + "</h1>");
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
        Appointment ap = new Appointment();
        try {
            boolean fetch = fetchFromJsp(ap,request);
            // boolean db = storeInDb(ap);
        } catch (SQLException ex) {
          //  Logger.getLogger(provideslots.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         request.getRequestDispatcher("/doctorpersonal.jsp").forward(request, response); 
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

    private boolean fetchFromJsp(Appointment ap, HttpServletRequest request) throws SQLException {
        
        String license = request.getParameter("license");
        
        String days[] = request.getParameterValues("Days");
        ap.setNumberOfDays(days.length);
        DateTimeFormatter formatter;
        formatter = DateTimeFormat.forPattern("HH:mm");
        Day d;
        String from;
        String to;
        
        if(days != null){
            for (String day : days) {
                switch(day){
                    
                    case "Mon":
                        
                         d = Day.MONDAY;
                         from = request.getParameter("monfrom");
                         to = request.getParameter("monto");
                         ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                         
                        break;
                        
                     case "Tue":
                         d = Day.TUESDAY;
                         from = request.getParameter("tuefrom");
                         to = request.getParameter("tueto");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;   
                         
                         case "Wed":
                         d = Day.WEDNESDAY;
                         from = request.getParameter("wedfrom");
                         to = request.getParameter("wedto");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;   
                             
                          case "Thur":
                         d = Day.THURSDAY;
                         from = request.getParameter("thufrom");
                         to = request.getParameter("thuto");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;      
                             
                             case "Fri":
                         d = Day.FRIDAY;
                         from = request.getParameter("frifrom");
                         to = request.getParameter("frito");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;   
                                 
                                 case "Sat":
                         d = Day.SATURDAY;
                         from = request.getParameter("satfrom");
                         to = request.getParameter("satto");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;   
                                     
                                     case "Sun":
                         d = Day.SUNDAY;
                         from = request.getParameter("sunfrom");
                         to = request.getParameter("sunto");
                        ap.storeInMAp(d, LocalTime.parse(from, formatter), LocalTime.parse(to,formatter));
                        break;   
                        
                }
            }
        }
           
        storeInDb(ap,license);
        return true;
       
    }

    private void storeInDb(Appointment ap , String license) throws SQLException {
       
        Connection connect = null;
         PreparedStatement statement = null;
         try {
             //ResultSet resultSet = null;
             Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
         }
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");    
   
      statement = connect.prepareStatement("Delete from appointments where doctor_id =?");
      statement.setString(1, license);
      int execute = statement.executeUpdate();
      
      
      System.out.println(license+" delete executed : "+execute);
      
     statement = connect.prepareStatement("INSERT INTO appointments(doctor_id,serialized_object) VALUES(?,?)");
     statement.setString(1 , license);
     //FileInputStream fis;
   //  fis = new FileInputStream(prescription);
     statement.setObject(2, ap);
     execute = statement.executeUpdate();
         
     System.out.println("db done");
    // return execute;
        
    }

}