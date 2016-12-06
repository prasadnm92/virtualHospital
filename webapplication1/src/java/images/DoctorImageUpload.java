/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package images;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author user
 */
@MultipartConfig
public class DoctorImageUpload extends HttpServlet {

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
            out.println("<title>Servlet DoctorImageUpload</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DoctorImageUpload at " + request.getContextPath() + "</h1>");
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
   
    String filename = request.getSession(false).getAttribute("did").toString();
    BufferedImage img = ImageIO.read(request.getPart("file").getInputStream());
    String outputfile = this.getServletContext().getRealPath(filename+".jpg");
    System.out.println(outputfile);
    File file = new File(outputfile);
    file.createNewFile();
    ImageIO.write(img, "jpg", file);
    System.out.println("done");
    request.getRequestDispatcher("/doctorpersonal.jsp").forward(request, response); 
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
  
    String filename = request.getSession(false).getAttribute("did").toString();
    BufferedImage img = ImageIO.read(request.getPart("file").getInputStream());
    String outputfile = this.getServletContext().getRealPath(filename+".jpg");
    System.out.println(outputfile);
    File file = new File(outputfile);
    file.createNewFile();
    ImageIO.write(img, "jpg", file);
    System.out.println("done");
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
}