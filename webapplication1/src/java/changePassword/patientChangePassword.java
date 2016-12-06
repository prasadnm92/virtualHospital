/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changePassword;

import backend.doctorregister;
import backend.patientregister;
import backend.smapledb;
import java.io.File;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class patientChangePassword extends HttpServlet {

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
            out.println("<title>Servlet doctorChangePassword</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet doctorChangePassword at " + request.getContextPath() + "</h1>");
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
        
         String username = request.getParameter("username");
         String password = request.getParameter("paswd");
         
         boolean validuser = false;
        try {
            validuser = checkUser(username , password);
        } catch (SQLException ex) {
            Logger.getLogger(patientChangePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         if(validuser){
             
             String password1 = request.getParameter("paswd1");
             String password2 = request.getParameter("paswd2");
             
             boolean samepassword = password1.equals(password2);
             
              boolean change , xmlChange;
             if(samepassword){
                 try {
                     change = changepswd(password1 , username);
                 } catch (SQLException ex) {
                     Logger.getLogger(patientChangePassword.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 xmlChange = cahngeInXml(password1 , username);
                 request.getRequestDispatcher("/personal.jsp").forward(request, response);
             }
                  
                  
             else{
                 String strErrMsg = "passwords donot match!!";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/changepaswd.jsp").forward(request, response);
                 
             }
                 
             
         }else{
              String strErrMsg = "Username and password donot match!!";
              request.setAttribute("Msg", strErrMsg);
              request.getRequestDispatcher("/changepaswd.jsp").forward(request, response);
             
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

    private boolean checkUser(String username, String password) throws SQLException {
        
        
    boolean valid = false;
    ResultSet rs;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT * FROM patients WHERE username = ? AND password = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, username);
        st.setString(2, password);
        
        
        rs = st.executeQuery();
        
       
            valid = rs.first();
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    return valid;
        
        
    }

    private boolean changepswd(String password1 , String did) throws SQLException {
          
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
             
             String GetPatient;
             GetPatient = "Update patients set password=? where username=?"; 
            
             
             PreparedStatement st = null;
             st = con.prepareStatement(GetPatient);
             st.setString(1, password1);
             st.setString(2,did);
             
             ResultSet results;
             int count = st.executeUpdate();
           
             if(count>1)
                return true;
             else
                 return false;
    }
    
    
    
    
     private boolean cahngeInXml(String password1, String username) {
       
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
                
		
                NodeList child = rootElement.getElementsByTagName("user");
                for(int i=0 ; i<child.getLength() ; i++){
                    
                Node usr = child.item(i);
                NodeList child2 = usr.getChildNodes();
                
                for(int j=0 ; j<child2.getLength();j++)
                {
                    Node usrnme = child2.item(j);
                    Node paswd = child2.item(j+1);
                    if(usrnme.getTextContent().equals(username)){
                        
                        paswd.setTextContent(password1);
                        
                    }
                        
                    
                }
               // if(child2.item(1).getTextContent().equals(username))
                 //   child2.item(2).setNodeValue(password1);
                  
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
                
                    
                }
        
        return true;
    }

}
