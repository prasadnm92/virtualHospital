/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class drLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
     */
    //static String username;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet drLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("</h1>" + "<h1>Servlet drLogin at loginsuccessful");
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
        
       
      //  processRequest(request, response);
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
        
        boolean login = false;
        int count;  
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        System.out.println(username);
        System.out.println(password);
        
        boolean userexists;
        userexists = checkuserexists(username);
        
        try {
            if(userexists)
                login = loginCheck(username,password);
            else
            {
                 String strErrMsg = "Check your username. or Register if not registered";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/dlogin.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(drLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(login);
        if(login){
           
     ResultSet rs = null;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT name FROM doctors WHERE did = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, username);
        
        
        rs = st.executeQuery();
        
        boolean records = rs.next();
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
            
           
                String doctorname = null;
            try {
                doctorname = rs.getString(1);
            } catch (SQLException ex) {
                Logger.getLogger(drLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
                 request.setAttribute("Msg", doctorname);
                // request.getRequestDispatcher("/dlogin.jsp").forward(request, response);
                 
                 HttpSession session = request.getSession(true);
                 session.setAttribute("did", username);
                 session.setAttribute("doctorname",doctorname);
                 session.setMaxInactiveInterval(30*60);
                 
                 Cookie userName = new Cookie("doctorname", username);
                 userName.setMaxAge(30*60);
               
                 response.addCookie(userName);
            
                 request.getRequestDispatcher("/doctorpersonal.jsp").forward(request, response); 
        }
            
        else{
                 String strErrMsg = "Username and Password donot match";
                 request.setAttribute("Msg", strErrMsg);
                 request.getRequestDispatcher("/dlogin.jsp").forward(request, response); 
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
    
    
    
    public boolean loginCheck(String did, String password) throws SQLException{

    boolean login = false;
    ResultSet rs;
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       // Statement stmt =  con.createStatement();
        //query = "select * from doctors;";
       //  query = "SELECT did, password FROM user WHERE username="" + did + "" AND password="" + password + "";";
        //rs = stmt.executeQuery(query); 
        
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT * FROM doctors WHERE did = ? AND password = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, did);
        st.setString(2, password);
        
        
        rs = st.executeQuery();
        
        login = rs.first(); 
        
      
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
    return login;
}

    private boolean checkuserexists(String username) {
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
        CHECK_USER = "SELECT * FROM doctors WHERE did = ?";
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
