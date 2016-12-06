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
import java.sql.ResultSet;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.util.Date;
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
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class patientregister extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param message
     * @param redirect
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response ,String message,String redirect)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>virtualHospital</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+ message +"</h1>");
            out.println("<p>click this link to redirect</p>");
            out.println("<p><a href=\""+redirect+"\">link</a></p>");
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
        
        //boolean datavalid;
        //datavalid = false;
        //datavalid = storeindatabase(request,response);
        
       // processRequest(request, response,"hai");
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
         
        boolean datavalid;
        boolean xmlValid = false;
        datavalid = false;
        
       // String hiddenvalue = request.getParameter("hiddenValue");
      //  if(hiddenvalue.equals("true") && hiddenvalue != null){
        String pswd1 = request.getParameter("password1");
        String pswd2 = request.getParameter("password2");
        String username =  request.getParameter("username");
        boolean exists = checkUsernameExists(username);
        if(pswd1.equals(pswd2) && !exists){
            try {
                datavalid = storeindatabase(request,response);
                if(datavalid)
                     xmlValid = storeinxml(request,response);
                
                 request.getRequestDispatcher("/payment.jsp?"+request.getParameter("username")).forward(request, response); 
            } catch (    SQLException | TransformerException | SAXException | ParserConfigurationException ex) {
                Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
             String strErrMsg = "Username already exists!!!";
             request.setAttribute("Msg", strErrMsg);
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

    private boolean storeindatabase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {
            String  name;
            String strage;
            int age;
            String strmobilenumber;
            //int mobilenumber;
            String email;
            String address;
            String symptoms;
            String department;
            String username;
  
            String sex;
            
            String doctorassigned;
            
            
            name = request.getParameter("Name");
            
            
            strage = request.getParameter("age");
            
            age = Integer.parseInt(strage);
            
            sex = request.getParameter("sex");
            
            
            strmobilenumber = request.getParameter("number");
            
           // mobilenumber = Integer.parseInt(strmobilenumber);
           
            
            email = request.getParameter("email");
            
            
            address = request.getParameter("address");
            
            
            symptoms = request.getParameter("symptoms");
            
            department = request.getParameter("Department");
            doctorassigned = assigndoctor(department);
          
            
            username = request.getParameter("username");
            
            String pswd1 = request.getParameter("password1");
            //String pswd2 = request.getParameter("password2");
            
       
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
           // java.sql.Time sqlTime = new java.sql.Time(new java.util.Date().getTime());
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
            } catch (SQLException ex) {
                Logger.getLogger(patientregister.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            try (PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into patients values(?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
               
                pst.setString(1,name);
                pst.setInt(2,age);
                pst.setString(3,sex);
                
                pst.setString(4,email);
                pst.setString(5,address);
                pst.setString(6,symptoms);
                pst.setString(7,department);
                pst.setString(8,username);
                pst.setString(9,pswd1);
                pst.setString(10,doctorassigned);
                pst.setString(11,strmobilenumber);
                pst.setDate(12, sqlDate);
                pst.setString(13,null);
               
                
                int i = pst.executeUpdate();
               // con.commit();
                
               // PrintWriter pw = response.getWriter();
                if(i!=0){
                   
                    
                   // request.getRequestDispatcher("/payment.jsp?"+username).forward(request, response); 
                }
                else{
                   
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

    private String assigndoctor(String department) throws SQLException {
       
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

    private boolean storeinxml(HttpServletRequest request, HttpServletResponse response) throws TransformerConfigurationException, TransformerException, SAXException, ParserConfigurationException, IOException {
        
        
        String username = request.getParameter("username");
        String password = request.getParameter("password1");
        String type = "patient";
        
          String outputfile = "D:\\webApplication\\login.xml";
          File xmlFile = new File(outputfile);
        
                 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
                org.w3c.dom.Document doc;
                 doc = docBuilder.parse(xmlFile);
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
		pswd.appendChild(doc.createTextNode(password));
		user.appendChild(pswd);
                
               org.w3c.dom.Element typ = doc.createElement("type");
		typ.appendChild(doc.createTextNode(type));
		user.appendChild(typ);
                
                // write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile);
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
        
        
        
        
        
        
        return true;
        
    }

    private boolean checkUsernameExists(String username) {
         boolean userexists = false;
         ResultSet rs;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       // Statement stmt =  con.createStatement();
        //query = "select * from doctors;";
       //  query = "SELECT did, password FROM user WHERE username="" + did + "" AND password="" + password + "";";
        //rs = stmt.executeQuery(query); 
        
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT * FROM patients WHERE username = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, username);
        
        
        rs = st.executeQuery();
        
        userexists = rs.first(); 
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    return userexists;
    }
        
        
    }


