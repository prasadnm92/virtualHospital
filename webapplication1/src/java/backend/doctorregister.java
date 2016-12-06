/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class doctorregister extends HttpServlet {

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
            out.println("<title>Servlet doctorregister</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet doctorregister at " + request.getContextPath() + "</h1>");
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
      
        boolean licensevalid;
        licensevalid = verifylicense(request,response);
        
        if(licensevalid){
           
            
            
           char[] password;
           
           int noOfCAPSAlpha = 2;
           int noOfDigits = 2;
           int noOfSplChars = 1;
           int minLen = 6;
           int maxLen = 8;
           
           password = generatePswd(minLen, maxLen,noOfCAPSAlpha, noOfDigits, noOfSplChars);
           
           boolean mailsent;
           boolean dbstore,xmlValid;
            
           String paswd = String.valueOf(password);
           
           dbstore = storeindb(request,response,password);
           
           xmlValid = storeinXml(paswd,request.getParameter("license"));
           
           //if(dbstore) 
               mailsent = sendmail(paswd,request.getParameter("license"),request.getParameter("email"));
               
          
           
           request.getRequestDispatcher("/index.html").forward(request, response);
           return;
        }
        else{
            
            request.getRequestDispatcher("/pregister.jsp").forward(request, response);
        }
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

    private boolean verifylicense(HttpServletRequest request, HttpServletResponse response) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

   

    char[]  generatePswd(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
        
        String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
        String NUM     = "0123456789";
        String SPL_CHARS   = "!@#$%^&*_/";
        
        if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        
        if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
            throw new IllegalArgumentException("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
           
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen ) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < noOfSplChars; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for(int i = 0; i < len; i++) {
            if(pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }
    @SuppressWarnings("empty-statement")
    int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }

    boolean sendmail(String password, String license, String mailid) {
                
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
					return new PasswordAuthentication("virtualhospitalteam","finalyearproject");
				}
			});
 
		try {
                        
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("vathsa.cool@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailid));
			message.setSubject("Virtual Hospital Confirmation Email");
                       
                        String text1 = "Welcome Doctor,\n" ;
                        String text2 = "   You recently registered for the Virtual Hospital webserivce.";
                        String text3 = "Please login to your account using following credentials"; 
                        String text4 = "\t Doctor ID : " +license+"\n";
			String text5 = "\t Password  : " +password+"\n";
                        String text6 = "\n\n Thank You,\n";
			String text7 = "Virtual Hospital Team";
                        
                        String finaltext = text1+text2+text3+text4+text5+text6+text7;
                        
			message.setText(finaltext);
                        
                        
                        Transport.send(message);
 
			System.out.println("Done");
                        return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
                //return true;
               
    }

    private boolean storeindb(HttpServletRequest request, HttpServletResponse response, char[] password) throws IOException, ServletException {
        try {
            String  name;
            String license;
            String qualification;
            String specialization;
            String dob;
            String practicestarted;
            int ps;
            int yof;
            String url;
            String mobilenumber;
            //int mobilenumber;
            String email;
            String address;
            String sex;
            String department;
            
            String pswd = String.valueOf(password);
            
          int curyear = Calendar.getInstance().get(Calendar.YEAR);
            
            name = request.getParameter("name");
            license = request.getParameter("license");
            qualification =  request.getParameter("qualification");
            specialization = request.getParameter("specialization");
            dob =  request.getParameter("dob");
            sex = request.getParameter("sex");
            practicestarted =  request.getParameter("yof");
            ps = Integer.parseInt(practicestarted);
            yof = curyear - ps;
            
            url =  request.getParameter("url");
            
            mobilenumber = request.getParameter("phone");
            
           // mobilenumber = Integer.parseInt(strmobilenumber);
           
            
            email = request.getParameter("email");
            
            
            address = request.getParameter("address");
            
         
            department = request.getParameter("department");
            
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //Date rdate = dateFormat.format(date);
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
            } catch (SQLException ex) {
                Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            try (PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into doctors values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
               
                pst.setString(1,name);
                pst.setString(2,pswd);
                pst.setInt(3,0);
                //pst.setInt(4,mobilenumber);
                pst.setInt(4,yof);
                pst.setString(5,license);
                pst.setString(6,department);
                pst.setString(7,sex);
                pst.setString(8,qualification);
                pst.setString(9,specialization);
                pst.setString(10,dob);
                pst.setString(11,mobilenumber);
                pst.setString(12,email);
                pst.setString(13,address);
                pst.setString(14, url);
                
                 int i = pst.executeUpdate();
               // con.commit();
                String msg=" ";
                PrintWriter pw = response.getWriter();
                if(i!=0){
                    msg="Record has been inserted";
                    
                    pw.println("<font size='6' color=blue>" + msg + "</font>");
                    
                 //   request.getRequestDispatcher("/index.jsp").forward(request, response); 
                }
                else{
                    msg="failed to insert the data";
                    pw.println("<font size='6' color=blue>" + msg + "</font>");
                   // request.getRequestDispatcher("/pregister.jsp").forward(request, response); 
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return (true);
    }

    private boolean storeinXml(String paswd, String username) {
        
        String type = "doctor";
        
          String outputfile = "D:\\webApplication\\login.xml";
          File xmlFile = new File(outputfile);
        
                 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(doctorregister.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                org.w3c.dom.Document doc = null;
        try {
            doc = docBuilder.parse(xmlFile);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(doctorregister.class.getName()).log(Level.SEVERE, null, ex);
        }
               doc.getDocumentElement().normalize();
		org.w3c.dom.Element rootElement = doc.getDocumentElement();
                
		// user elements
		org.w3c.dom.Element user = doc.createElement("user");
		rootElement.appendChild(user);
 
 
		// name elements
		org.w3c.dom.Element name = doc.createElement("username");
		name.appendChild(doc.createTextNode(username));
		user.appendChild(name);
                
                org.w3c.dom.Element pswd = doc.createElement("password");
		pswd.appendChild(doc.createTextNode(paswd));
		user.appendChild(pswd);
                
               org.w3c.dom.Element typ = doc.createElement("type");
		typ.appendChild(doc.createTextNode(type));
		user.appendChild(typ);
                
                // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(doctorregister.class.getName()).log(Level.SEVERE, null, ex);
        }
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile);
        try {
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(doctorregister.class.getName()).log(Level.SEVERE, null, ex);
        }
 
		System.out.println("File saved!");
        
        
        
        
        
        
        return true;
        
    }
        
    }

   


    



