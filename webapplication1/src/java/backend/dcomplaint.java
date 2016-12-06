/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.io.IOException;
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
public class dcomplaint extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {     
           
          
               boolean mailsent;
              
               mailsent = sendmail(request);
          
               request.getRequestDispatcher("doctorpersonal.jsp").forward(request, response);
               
        
            
            
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

   
  
   
   

    boolean sendmail(HttpServletRequest request ) {
                
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
			message.setSubject("Complaint Email");
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
                        String text1 = "Type:"+"\n"+"Doctor";
                        String finaltext = request.getParameter("complaint");
                        
			message.setText("Name:"+request.getParameter("dname")+"\n"+text1+finaltext);
                        
                        
                        Transport.send(message);
 
			System.out.println("Done");
                        return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
                //return true;
               
    }

    
     
}

   


    



