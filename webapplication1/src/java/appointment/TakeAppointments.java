/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import backend.patientregister;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 *
 * @author user
 */
public class TakeAppointments extends HttpServlet {

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
            out.println("<title>Servlet TakeAppointments</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TakeAppointments at " + request.getContextPath() + "</h1>");
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
       // processRequest(request, response);
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
        
        boolean isSuccess = false;
        try {
            // processRequest(request, response);

           isSuccess = fetchFromJsp(request , response);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            Logger.getLogger(TakeAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(isSuccess)
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

    private boolean fetchFromJsp(HttpServletRequest request , HttpServletResponse response) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, ServletException {
       
        String patientName = request.getParameter("user");
        
        String previousConsultation = request.getParameter("group");
        
        String reasonForAppoint = request.getParameter("reason");
        
        String dayOfAppoint = request.getParameter("day");
        
        String AppointmentTime = request.getParameter(dayOfAppoint);
        
        if(patientName == null){
            String strErrMsg = "Enter proper Username";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;            
        }
        
        if(previousConsultation == null){
            String strErrMsg = "Enter whether you have previously visited or not";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;            
        } 
        if(reasonForAppoint == null){
            String strErrMsg = "Enter the Reason For Appointment!";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;            
        }
        if(dayOfAppoint == null){
            String strErrMsg = "Enter the Day Of Appointment!";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;            
        }
        if(AppointmentTime == null){
            String strErrMsg = "Choose the proper slot for Appointment!";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;            
        }
        String did = fetchdoctor(patientName);
        
        Appointment a = fetchfromdb(did);
        
        boolean slotExists = a.checkExists(AppointmentTime, Days.Day.valueOf(dayOfAppoint));
        if(!slotExists){
            
             String strErrMsg = "Sorry!! This slot is already blocked.Please select another slot";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/patientappointment.jsp").forward(request,response);
             return false;
        }
            
        
        a.updateMap(AppointmentTime, Days.Day.valueOf(dayOfAppoint), patientName);
       
        updateInDb(a,did);
        
        String mailid = fetchEmail(did);
        sendMail(did , patientName ,  dayOfAppoint , AppointmentTime ,reasonForAppoint , previousConsultation , mailid);
        
        mailid = fetchEmailP(patientName);
        sendMail(did , patientName ,  dayOfAppoint , AppointmentTime ,reasonForAppoint , previousConsultation , mailid);
        
        int index = AppointmentTime.lastIndexOf(":");
        String at2 = AppointmentTime.substring(0, index);
        
        storeInDb(patientName, at2 , dayOfAppoint , did);
        return true;
       
       
    }

    
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

    private void updateInDb(Appointment a, String did) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       
         ResultSet rs;
       
  
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "UPDATE appointments set serialized_object = ? where doctor_id =?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        
        st.setObject(1, a);
        st.setString(2, did);
        
        
        int count =  st.executeUpdate();
   
    }

    private void sendMail(String did, String patientName, String dayOfAppoint, String AppointmentTime, String reasonForAppoint, String previousConsultation , String mailid) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
      
        
        LocalDate now = new LocalDate();
            LocalDate date;
            
             if(dayOfAppoint.equals("MONDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.MONDAY);
             else if(dayOfAppoint.equals("TUESDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.TUESDAY);
             else if(dayOfAppoint.equals("WEDNESDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.WEDNESDAY);
             else if(dayOfAppoint.equals("THURSDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.THURSDAY);
             else if(dayOfAppoint.equals("FRIDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.FRIDAY);
             else if(dayOfAppoint.equals("SATURDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.SATURDAY);
             else 
                 date = now.withDayOfWeek(DateTimeConstants.SUNDAY);
             
                 
              String date1 = date.toString();
        
        
        
        
        
         Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("virtualhospitalteam@gmail.com","finalyearproject");
				}
			});
                
                
              //  String mailid = fetchEmail(did);
                
                
 
		try {
                        
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vathsa.cool@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailid));
			message.setSubject("Virtual Hospital Confirmation Email");
                       
                      //  String text1 = "Welcome Doctor,\n" ;
                        String text2 = "   You have an appointment being booked.\n";
                        String text3 = " Booked by the  :"+patientName; 
                        String text4 = "\t Appointment Day   : " +dayOfAppoint+"\n";
			String text5 = "\t Appointment Time  : " +AppointmentTime+"\n";
                        String reason = "\t Reason for the Appointment is as Follows:\n"+"\t"+reasonForAppoint;
                        String text8 =  "\t Appointment Date  : " +date1+"\n";
                        String text6 = "\n\n Thank You,\n";
			String text7 = "Virtual Hospital Team";
                        
                        String finaltext = text2+text3+text4+text5+reason+text6+text7;
                        
			message.setText(finaltext);
                        
                        
                        Transport.send(message);
 
			System.out.println("Done");
                    //    return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
        
        
    }

    private String fetchEmail(String did) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       
        String email = null;
        
         
         ResultSet rs;
         Appointment a = null;
 
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT email FROM doctors WHERE did = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, did);
        
        
        rs = st.executeQuery();
        if(rs.next())
            email = rs.getString(1);
        
        return email;
    }

    private void storeInDb(String patientName, String AppointmentTime, String appointmentday , String did) throws SQLException {
        String date1 = null;
        try {
            
            Date d = new Date();
            LocalDate now = new LocalDate();
            LocalDate date;
            
             if(appointmentday.equals("MONDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.MONDAY);
             else if(appointmentday.equals("TUESDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.TUESDAY);
             else if(appointmentday.equals("WEDNESDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.WEDNESDAY);
             else if(appointmentday.equals("THURSDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.THURSDAY);
             else if(appointmentday.equals("FRIDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.FRIDAY);
             else if(appointmentday.equals("SATURDAY"))
                 date = now.withDayOfWeek(DateTimeConstants.SATURDAY);
             else 
                 date = now.withDayOfWeek(DateTimeConstants.SUNDAY);
             
                 
              date1 = date.toString();
            
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (    InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(TakeAppointments.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TakeAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
            } catch (SQLException ex) {
                Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
            }
            PreparedStatement pst = null;
        try {
            pst = (PreparedStatement) con.prepareStatement("insert into patient_appointment values(?,?,?,?,?)");
        } catch (SQLException ex) {
            Logger.getLogger(TakeAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.setString(1,patientName);
        } catch (SQLException ex) {
            Logger.getLogger(TakeAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
                pst.setString(2,AppointmentTime);
                pst.setString(3,appointmentday );
                pst.setString(4, did);
                pst.setString(5, date1);
                
               int i = pst.executeUpdate();
    
    }
    
     private String fetchEmailP(String username) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       
        String email = null;
        
         
         ResultSet rs;
         Appointment a = null;
 
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT email FROM patients WHERE username = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, username);
        
        
        rs = st.executeQuery();
        if(rs.next())
            email = rs.getString(1);
        
        return email;
    }
    
    
    
}