/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.lang.Object;
import com.itextpdf.text.Chunk;
import java.lang.String;

/**
 *
 * @author vathsa
 */
public class prescriptionstore extends HttpServlet {
    
    
    public String docName;
    public String email;
    public String mobNo;
    public String fileNamePdf;
    private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;
	@Override
	public void init() throws ServletException{
		//DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_PRE_FILE");
		//fileFactory.setRepository(filesDir);
		//this.uploader = new ServletFileUpload(fileFactory);
	}

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
        Font blueFont = new Font(Font.FontFamily.HELVETICA, 14,Font.NORMAL, BaseColor.BLUE);
        Font redFont = new Font(Font.FontFamily.COURIER, 12,Font.NORMAL, BaseColor.RED);
        Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);


    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet prescriptionstore</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet prescriptionstore at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
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
        
        
        //processRequest(request, response);
        backend.presccription p = new backend.presccription();
        
        File file = new File("D:\\webApplication\\prescription.xml");
        
        boolean fetch = fetchFromJSP(p , request , response);
        
        File prescription = null;
         try {
             prescription = writeToPdf(p);
         } catch (BadElementException ex) {
             Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
         }
     
         try {
             boolean db = storeInDB(p,prescription);
         } catch ( SQLException | FileNotFoundException ex) {
             Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
         }
        
         try {
             if(file.exists())
                 System.out.println("--------------------");
             
             boolean xml  = writeToXml(p,file);
         } catch ( TransformerException | SAXException | ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
           //  Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
             ex.printStackTrace();
         }
         
       // ` HttpSession http = request.getSession();
         //String doctorname = http.getAttribute("doctorname").toString();
         //System.out.println(doctorname);
        try {
            boolean storeInServer = writeToServer(p,prescription,request);
        } catch (Exception ex) {
            Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
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

    private File writeToPdf(presccription p) throws BadElementException, IOException {
        //throw new UnsupportedOperationException("Not supported yet.");
        
//To change body of generated methods, choose Tools | Templates.
        Date today = new Date();
        //int year = today.getDay();
        int year = today.getYear();
        int month = today.getMonth();
        int date1 = today.getDate();
        fileNamePdf = p.getPatient()+year+month+date1;
        File file = new File("D://#JP//"+fileNamePdf+".pdf");  
        
       
        
         try {
               Document document = new Document();
               PdfWriter.getInstance(document, new FileOutputStream(file));
               document.open();
               addMetaData(document,p);
               addTitlePage(document,p);
               addContent(document,p);
               addDeclaration(document,p);
               document.close();
    } catch (DocumentException | FileNotFoundException e) {
    }
         
        
         
       sendmail(file, p); 
        
        return file;
    }
    
    public void addMetaData(Document document,presccription p) {
    document.addTitle("Virtual Hospital");
    document.addSubject("Prescription");
    document.addKeywords("precription");
    document.addAuthor(p.getDoctor());
    //document.addCreator("Lars Vogel");
  }
   
    public void  addTitlePage(Document document,presccription p)
      throws DocumentException, BadElementException, IOException {
        
         
    ResultSet rs;
    String name = null;
    String qualification = null;
    
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT name,qualification,email,phone FROM doctors WHERE did = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, p.getDoctor());
        
        rs = st.executeQuery();
        
       if( rs.first()){
           name = rs.getString(1);
           docName = name;
           qualification = rs.getString(2);
           email = rs.getString(3);
           mobNo = rs.getString(4);
       }
     } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
  
    Paragraph preface = new Paragraph();
    // We add one empty line
    addEmptyLine(preface, 1);
    // Lets write a big header
    
    Image logo;
         logo = Image.getInstance("D:\\#JP\\WebApplication1\\web\\images\\letterHead.gif");
    PdfPTable letterHead = new PdfPTable(2);
    
    
    PdfPCell c1 = new PdfPCell();
    c1.addElement(logo);
    c1.setBorder(2);
    letterHead.addCell((c1));
    
    PdfPCell c2 = new PdfPCell(new Phrase(name.toUpperCase()+"\n\t"+qualification.toUpperCase()+"\n\t"+email+"\n\t"+mobNo));
    c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
	c2.setBorder(2);
    letterHead.addCell(c2);
    
    preface.add(letterHead);
    document.add(preface);
         
    Paragraph patDetail = new Paragraph();
    
    addEmptyLine(patDetail, 2);

    /*Paragraph p3 = new Paragraph("Patient Name:"+p.getPatient().toUpperCase()+"\nAge:"+p.getAge()+"\n"+"Sex:"+p.getSex(),blueFont);*/

Paragraph p3 = new Paragraph();

Chunk ch1 = new Chunk("Patient Name: ",blueFont);
ch1.setUnderline(0.5f, -1.5f);
Chunk ch2 = new Chunk(p.getPatient().toUpperCase(),smallBold);

Chunk ch3 = new Chunk("\nAge: ",blueFont);
ch3.setUnderline(0.5f, -1.5f);

String pAge;
        pAge = String.valueOf(p.getAge());
Chunk ch4 = new Chunk(pAge,smallBold);

Chunk ch5 = new Chunk("\nSex: ",blueFont);
ch5.setUnderline(0.5f, -1.5f);
Chunk ch6 = new Chunk(p.getSex()+"\n",smallBold);

p3.add(ch1);
p3.add(ch2);
p3.add(ch3);
p3.add(ch4);
p3.add(ch5);
p3.add(ch6);



    p3.setAlignment(Element.ALIGN_RIGHT);
    patDetail.add(p3);
    document.add(patDetail);
    
  }
    

  
   public void addContent(Document document,presccription p) throws DocumentException {
   

    Paragraph subPart = new Paragraph();
    addEmptyLine(subPart,2);
    
    createPrescription(subPart,p);
    // now add all this to the document
    document.add(subPart);
  }
  
   public void createPrescription(Paragraph subPart, presccription p)
   {
      Paragraph p01 = new Paragraph("List of Medicines:\n\n", subFont);
      subPart.add(p01);
      int n=0;
      Set<Map.Entry<String,tablets>>set = p.prescriptionMap.entrySet();
      int serialNo=1;
      for(Map.Entry<String,tablets>temp:set){
          tablets t = temp.getValue();
          String tname = null;
          String dos = " ";
          String quant = t.getQuantity();
          int noOfDays = p.getNumberOfDays();
          tname = t.getName();
         
          for(int i=0 ; i<3 ;i++){
              
              if(t.dose[i]!=null){
                  Dosage d = t.dose[i];
                   dos = dos+"-  1 ";
                  if(d.getFood())
                      dos = dos+" (bf)  ";
                  else if(!d.getFood())
                      dos = dos+" (af)  ";
                      
              }else{
                  
                  dos = dos+"-  0  ";
              }
              n=noOfDays;    
          }
          String finaldos = serialNo+") "+tname.toUpperCase()+"\t\t\t"+dos+"\t\t\t[Quantity : "+quant+"]\n";
          Paragraph p00 = new Paragraph(finaldos, smallBold);
          serialNo++;
          
          subPart.add(p00);
      }
      subPart.add(new Paragraph("\nPrescription duration : "+n+" days", smallBold));
      subPart.add(new Paragraph("\n\nNote : \n\t"+p.getNote()+"\n", smallBold));
   }
  

public void addDeclaration(Document document, presccription p) throws DocumentException
   {

    Paragraph declaration = new Paragraph();
    addEmptyLine(declaration, 2);
    // Will create: Report generated by: _name, _date
    declaration.add(new Paragraph("*Prescription generated by: " + docName.toUpperCase() + ", On " + new Date() + "\n", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        smallBold));
   
    declaration.add(new Paragraph("**This prescription is provided by a valid doctor and authorised by Virtual Hospital**",
        redFont));

    document.add(declaration);
    
}

  public void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }

   
    private boolean storeInDB(presccription p, File prescription) throws SQLException, FileNotFoundException {
         
         Connection connect = null;
         PreparedStatement statement = null;
         try {
             //ResultSet resultSet = null;
             Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(prescriptionstore.class.getName()).log(Level.SEVERE, null, ex);
         }
      connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");    
         
     statement = connect.prepareStatement("INSERT INTO prescription VALUES(?,?)");
     statement.setString(1,p.getPatient());
     FileInputStream fis;
     fis = new FileInputStream(prescription);
     statement.setBinaryStream(2, fis);
     boolean execute = statement.execute();
         
         
     return execute;
         
         
    }

    private boolean writeToXml(presccription p, File xmlFile1) throws TransformerException, SAXException, IOException, ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException {
      
	 try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
                System.out.println("1");
                
                org.w3c.dom.Document doc;
                 doc = docBuilder.parse(xmlFile1);
               doc.getDocumentElement().normalize();
		org.w3c.dom.Element rootElement = doc.getDocumentElement();
                
		// patient elements
		org.w3c.dom.Element patient = doc.createElement("patient");
		rootElement.appendChild(patient);
 
                System.out.println("2");
		// name elements
              
		org.w3c.dom.Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(p.getPatient()));
		patient.appendChild(name);
                
                org.w3c.dom.Element age = doc.createElement("age");
		age.appendChild(doc.createTextNode(Integer.toString(p.getAge())));
		patient.appendChild(age);
                  
                org.w3c.dom.Element sex = doc.createElement("sex");
		sex.appendChild(doc.createTextNode(p.getSex()));
		patient.appendChild(sex);
                
                 org.w3c.dom.Element dateofprescription = doc.createElement("dateofprescription");
                 Date dt = new Date();
		dateofprescription.appendChild(doc.createTextNode(dt.toString()));
		patient.appendChild(dateofprescription);
                
                org.w3c.dom.Element numberOfDays = doc.createElement("numberOfDays");
                numberOfDays.appendChild(doc.createTextNode(Integer.toString(p.getNumberOfDays())));
                patient.appendChild(numberOfDays);
                
                System.out.println("3");
              
    ResultSet rs;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT mobile_number FROM patients WHERE username = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1, p.getPatient().toString());
        
        
        rs = st.executeQuery();
        String mobileNumber = null;
        if(rs.first())
            mobileNumber = rs.getString(1);
        
        org.w3c.dom.Element mobileno = doc.createElement("MobileNumber");
        mobileno.appendChild(doc.createTextNode(mobileNumber));
        patient.appendChild(mobileno);
         
                Set<Map.Entry<String , tablets>>map = p.prescriptionMap.entrySet();
                
                    for(Map.Entry<String , tablets>temp:map){
                        
                         org.w3c.dom.Element tablet = doc.createElement("tablet");
         //                tablet.appendChild(doc.createTextNode(temp.getKey()));
                         tablet.setAttribute("quantity", temp.getValue().getQuantity());
                         
                           org.w3c.dom.Element tname = doc.createElement("name");
                            tname.appendChild(doc.createTextNode(temp.getKey()));
                             tablet.appendChild(tname);
                         
                         tablets t = temp.getValue();
                         
                         for(int i=0 ; i< 3 ;i++){
                            Dosage d = t.dose[i];
                            
                            if(d!=null){
                                String time = d.getTime().toString();
                                org.w3c.dom.Element dosage = doc.createElement(time);
                                if(d.getFood())
                                    dosage.appendChild(doc.createTextNode("Beforefood"));
                                else
                                    dosage.appendChild(doc.createTextNode("Afterfood"));
                                tablet.appendChild(dosage);
                            }
                           
                    }
                    patient.appendChild(tablet);      
               }
                            
                      
                        
                    System.out.println("4");
                 
                    
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
                     System.out.println("ERROR");
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlFile1);
 
		// Output to console for testing
		//StreamResult result = new StreamResult(System.out);
                
                     System.out.println("5");
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch ( ParserConfigurationException | TransformerException pce) {
              pce.printStackTrace();
                return false;
	  }
              System.out.println("6");
        return true;

    }

     @SuppressWarnings("empty-statement")
    private boolean fetchFromJSP(presccription p,HttpServletRequest request, HttpServletResponse response) {
        
        
        String doctorname;
        String patientname;
        String sex;
        int age;
        int numberOfDays;
        
        String tablet;
        String quantity;
       
        
        doctorname = request.getParameter("doctorname");
        p.setDoctorName(doctorname);
        
       
        patientname = request.getParameter("patientname");
        p.setPatientName(patientname);
        
        sex = request.getParameter("sex");
        p.setSex(sex);
        
        age = Integer.parseInt(request.getParameter("age"));
        p.setAge(age);
        
        
        tablet = request.getParameter("tablet1");
        if(tablet!=null && !tablet.isEmpty())
        {
            tablets t = new tablets();
            t.setName(tablet);
            
            quantity = request.getParameter("quantity1");
            t.setQuantiy(quantity);
            
           String[] temp = request.getParameterValues("Dosage1m");
           
           boolean[] temp1 = new boolean[3];
           for(int i=0; i<3; i++){
               temp1[i] = false;
           }
            
           if(temp != null ){
                   
            //for(int j = 0 ; j<temp.length ; j++){
                
                if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
                

                    
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.MORNING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else if(temp1[2] == true)
                    d.setFood(2);
                t.dose[0] = d;
              
            }
               
               
           }
           
                
          temp = request.getParameterValues("Dosage1a");
            if(temp!=null){
                
                for(int i=0; i<3; i++){
                     temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.AFTERNOON);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[1] = d;
              
            }

                
            }
          // temp1 = new boolean[3];
                       
            temp = request.getParameterValues("Dosage1e");
            
           if(temp != null){
               
               for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.EVENING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[2] = d;
              
            }
        
           } 
       
            p.prescriptionMap.put(tablet, t);
        }
        
       tablet = request.getParameter("tablet3");
        if(tablet!=null && !tablet.isEmpty())
        {
            tablets t = new tablets();
            t.setName(tablet);
            
            quantity = request.getParameter("quantity3");
            t.setQuantiy(quantity);
            
          
            
           boolean[] temp1 = new boolean[3];
           for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
            String[] temp = request.getParameterValues("Dosage3m");
            
            if(temp!=null){
                
                 if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.MORNING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[0] = d;
              
            }
                
                
            }
            
                
          temp = request.getParameterValues("Dosage3a");
            
          if(temp!=null){
              
              for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.AFTERNOON);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[1] = d;
              
            }
          }
          // temp1 = new boolean[3];
           
            
            temp = request.getParameterValues("Dosage3e");
            
            if(temp!=null){
                
                for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
          if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.EVENING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[2] = d;
              
            }
            }
          // temp1 = new boolean[3];
           
            
            p.prescriptionMap.put(tablet, t);
        }
        
        
        tablet = request.getParameter("tablet2");
        if(tablet!=null && !tablet.isEmpty())
        {
            tablets t = new tablets();
            t.setName(tablet);
            
            quantity = request.getParameter("quantity2");
            t.setQuantiy(quantity);
            
          
            
           boolean[] temp1 = new boolean[3];
           for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
            String[] temp = request.getParameterValues("Dosage2m");
            if(temp!=null ){
              
                 if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.MORNING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[0] = d;
              
            }
                
            }
            
                
          temp = request.getParameterValues("Dosage2a");
           
          if(temp!=null){
              
              for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
           if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.AFTERNOON);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[1] = d;
              
            }
          }
          // temp1 = new boolean[3];
           
            
            temp = request.getParameterValues("Dosage2e");
            
            if(temp!=null){
                
                for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
            if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.EVENING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[2] = d;
              
            }
            }
          // temp1 = new boolean[3];
           
            
            p.prescriptionMap.put(tablet, t);
        }
        
        
       
     
        tablet = request.getParameter("tablet4");
        if(tablet!=null && !tablet.isEmpty())
        {
            tablets t = new tablets();
            t.setName(tablet);
            
            quantity = request.getParameter("quantity4");
            t.setQuantiy(quantity);
            
           
            
           boolean[] temp1 = new boolean[3];
           for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
           String[] temp = request.getParameterValues("Dosage4m");
           
           if(temp!=null){
               
               if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.MORNING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[0] = d;
              
            }
           }
            
                
          temp = request.getParameterValues("Dosage4a");
          
          if(temp!=null){
              
             for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.AFTERNOON);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[1] = d;
              
            } 
          }
          // temp1 = new boolean[3];
           
            
            temp = request.getParameterValues("Dosage4e");
            
            if(temp!=null){
                
                for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.EVENING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[2] = d;
              
            }
            }
          // temp1 = new boolean[3];
           
            
            p.prescriptionMap.put(tablet, t);
        }
        
          tablet = request.getParameter("tablet5");
        if(tablet!=null && !tablet.isEmpty())
        {
            tablets t = new tablets();
            t.setName(tablet);
            
            quantity = request.getParameter("quantity5");
            t.setQuantiy(quantity);
            
           
            
           boolean[] temp1 = new boolean[3];
           for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
           String[] temp = request.getParameterValues("Dosage5m");
           
           if(temp!=null){
               
              if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.MORNING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[0] = d;
              
            }
           }
            
                
          temp = request.getParameterValues("Dosage5a");
          
          if(temp!=null){
              
              for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
                if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
                          
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.AFTERNOON);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[1] = d;
              
            }
          }
          // temp1 = new boolean[3];
           
            
            temp = request.getParameterValues("Dosage5e");
            
            if(temp!=null){
                
                for(int i=0; i<3; i++){
               temp1[i] = false;
           }
           
             if(temp[0]!=null)
                    temp1[0] = true; 
                if(temp[1].equals("bf"))
                    temp1[1] = true;
                else if(temp[1].equals("af"))
                    temp1[2] = true;
              
            
            
            if(temp1[0] == true){
                Dosage d = new Dosage();
                
                d.setTime(Dosage.Time.EVENING);
                
                if(temp1[1] == true)
                     d.setFood(1);
                else
                    d.setFood(2);
                t.dose[2] = d;
              
            }
            }
          // temp1 = new boolean[3];
           
            p.prescriptionMap.put(tablet, t);
        }
        
        
        numberOfDays = Integer.parseInt(request.getParameter("numberofdays"));
        p.setDays(numberOfDays);
        
        String note1 = request.getParameter("note");
        p.SetNote(note1);
        
         Set<Map.Entry<String,tablets>>set = p.prescriptionMap.entrySet();
        for(Map.Entry<String,tablets>temp:set){
            System.out.println(temp.getKey());
            System.out.println(temp.getValue().getName());
            
        }
        
        return true;
        
    }
    
    
    
    
    boolean sendmail(File file , presccription p) {
                
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
 
                String mailid = getMail(p);
		try {
                        
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("virtualhospitalteam@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mailid));
			message.setSubject("Virtual Hospital Confirmation Email");
                       
                         // Create the message part 
                        BodyPart messageBodyPart = new MimeBodyPart();

                        
                        String text1 = "Hello,\n" ;
                        String text2 = " Here is your prescription!\n";
                        String text3 = "We will remind you on the registered phone"; 
                     //   String text4 = "\t Doctor ID : " +license+"\n";
		     //	String text5 = "\t Password  : " +password+"\n";
                        String text6 = "\n\n Thank You,\n";
			String text7 = "Virtual Hospital Team";
                        
                        String finaltext = text1+text2+text3+text6+text7;
                        
			//message.setText(finaltext);
                        messageBodyPart.setText(finaltext);
                        
                        
                        Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         String filename = p.getPatient()+".pdf";
         DataSource source = new FileDataSource(file);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart );

                        
                        Transport.send(message);
 
			System.out.println("Done");
                        return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
	    }
                //return true;
               
    }
    
    
    public String getMail(presccription p){
        
        
         ResultSet rs;
         String email = null;
        // String qualification = null;
    
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mynewdatabase?zeroDateTimeBehavior=convertToNull", "root", "virtualhospital@0");
       
        
        String CHECK_USER;//this will avoid sql injection
        CHECK_USER = "SELECT email FROM patients WHERE username = ?";
        java.sql.PreparedStatement st;
        st = con.prepareStatement(CHECK_USER);
        st.setString(1,p.getPatient() );
        
        rs = st.executeQuery();
        
       if( rs.first()){
            email = rs.getString(1);
           //qualification = rs.getString(2);
       }
       
     } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
    }
       return email; 
    }

    private boolean writeToServer(presccription p, File prescription ,HttpServletRequest request) throws FileUploadException, Exception {
        
             
        try {
            // get access to file that is uploaded from client
            
            InputStream is = new FileInputStream(prescription);
           // String UPLOAD_DIR = "prescription";
            // read filename which is sent as a part
          //  Part p2  = request.getPart("photoname");
          //  Scanner s = new Scanner(p2.getInputStream());
            String filename = p.getPatient();    // read filename from stream
            
            
       //     String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
         //   String uploadFilePath = applicationPath + File.separator + "prescription"+File.separator+p.getPatient();
          
        // creates the save directory if it does not exists
       // File fileSaveDir = new File(uploadFilePath);
       
       // System.out.println("Upload File Directory="+uploadFilePath);
        //    String filename = "";
          //   get filename to use on the server
            String outputfile = this.getServletContext().getRealPath(p.getPatient()+".pdf"); // get path on the server
           // System.out.println(outputfile);
            
           // File outputfile = new File(request.getServletContext().getAttribute("FILES_PRE")+File.separator+p.getPatient()+".pdf");
            FileOutputStream os = new FileOutputStream (outputfile);
            System.out.println(outputfile);
            // write bytes taken from uploaded file to target file
            int ch = is.read();
            while (ch != -1) {
                 os.write(ch);
                 ch = is.read();
            }
            os.close();
            System.out.println("prescription saved");
           // out.println("<h3>File uploaded successfully!</h3>"+outputfile);
        }
        catch(Exception ex) {
           //out.println("Exception -->" + ex.getMessage());
        }
        
        return true;
}
}
