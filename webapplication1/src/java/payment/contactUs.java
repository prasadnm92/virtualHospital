/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package payment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
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

/**
 *
 * @author user
 */
public class contactUs extends HttpServlet {

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
            out.println("<title>Servlet contactUs</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet contactUs at " + request.getContextPath() + "</h1>");
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
      //  processRequest(request, response);
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        
        sendMail(name,email,message);
        
          request.getRequestDispatcher("index.html").forward(request, response);
        
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

    private void sendMail(String name, String email, String message1) {
       
        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
                                @Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("virtualhospitalteam@gmail.com","finalyearproject");
				}
			});
 
		try {
                        
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("virtualhospitalteam@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("virtualhospitaladmn@gmail.com"));
			message.setSubject("Contact Us Email");
                       /*
                        String text1 = "Welcome Doctor,\n" ;
                        String text2 = "   You recently registered for the Virtual Hospital webserivce.";
                        String text3 = "Please login to your account using following credentials";                         
                        String text6 = "\n\n Thank You,\n";
			String text7 = "Virtual Hospital Team";
                       
                         boolean doctor;
                        if(request.getSession().getAttribute("doctorname")!=null)
                             doctor = true;
                        else
                            doctor = false;
                        if()
                               */
                        String text1 = "Type:"+"\n"+"contact US";
                        
                        
			message.setText("Name:"+name+"\n"+text1+"\n"+"Email:"+email+"\n"+"Message:"+message1+"\n");
                        
                        
                        Transport.send(message);
 
			System.out.println("Done");
                      //  return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
        
        
    }

}
