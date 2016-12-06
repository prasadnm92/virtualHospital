/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sendsms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class sendsms extends HttpServlet {

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
            out.println("<title>Servlet sendsms</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sendsms at " + request.getContextPath() + "</h1>");
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
        try {
            //processRequest(request, response);

            boolean fetch = fetchFromJsp(request);
        } catch (ParserConfigurationException | SAXException ex) {
            Logger.getLogger(sendsms.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("/adminpersonal.jsp").forward(request, response);
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

    private boolean fetchFromJsp(HttpServletRequest request) throws ParserConfigurationException, SAXException, IOException {
        
        boolean temp1[] = new boolean[3];
        
        String temp[] = request.getParameterValues("morning");
        if(temp!=null){
            
          if(temp[1].equals("bf"))
                sendsms("MORNING","Beforefood");
            else if(temp[1].equals("af"))
                sendsms("MORNING","Afterfood");
        }
        
        
        temp = request.getParameterValues("afternoon");
        if(temp!=null){
      
            
            if(temp[1].equals("bf"))
                sendsms("AFTERNOON","Beforefood");
            else if(temp[1].equals("af"))
                sendsms("AFTERNOON","Afterfood");
        }
        
         temp = request.getParameterValues("evening");
        if(temp!=null){
            
           
            if(temp[1].equals("bf"))
                sendsms("EVENING","Beforefood");
            else if(temp[1].equals("af"))
                sendsms("EVENING","Afterfood");
        }
        
        return true;
        
    }

    private void sendsms(String time, String food) throws ParserConfigurationException, SAXException, IOException {
       
        sms s = new sms();
        File file = new File("D:\\webApplication\\prescription.xml");
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
       org.w3c.dom.Document doc;
       doc = docBuilder.parse(file);
       doc.getDocumentElement().normalize();
       
       //org.w3c.dom.Element rootElement = doc.getDocumentElement();
       
      NodeList patients = doc.getElementsByTagName("patient");
       
       for(int i=0 ; i < patients.getLength();i++){
           
           Node patient = patients.item(i);
           
           Node name = patient.getFirstChild();
           String ptntname = name.getTextContent();
           
           //System.out.println(ptntname);
           
           String tabletname = "%20";
           String mobno = null;
        //   Node mob = nNode.
           
           NodeList tablets = patient.getChildNodes();
           for(int j = 0 ;j < tablets.getLength() ; j++){
               
               Node tablet = tablets.item(j);
               switch (tablet.getNodeName().toString()) {
                   case "tablet":
                       String temp = tablet.getFirstChild().getTextContent();
                       NodeList child = tablet.getChildNodes();
                       for(int k = 0; k < child.getLength();k++){
                           
                           Node mae = child.item(k);
                         //  System.out.println(mae.getNodeName());
                           //System.out.println(mae.getTextContent());
                           if(mae.getNodeName().equals(time) && mae.getTextContent().equals(food))
                               tabletname = tabletname+"%20,"+temp;
                           
                       }  
                       break;
                   case "MobileNumber":
                       mobno = tablet.getTextContent();
             //          System.out.println(mobno);
                       break;
               }
               
           }
          System.out.println(mobno+tabletname+"\t"+ptntname);
          s.delegate(mobno , ptntname , food , tabletname );
       }
    
    }

}
