/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import appointment.Days.Day;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 *
 * @author user
 */
public class Samole {
    
   public static void main(String args[]){
     /*  
       Samole s = new Samole();
       Appointment a = null;
       try {
            a = s.fetch("1234");
       } catch (IOException ex) {
           Logger.getLogger(Samole.class.getName()).log(Level.SEVERE, null, ex);
       }
       Day d = Day.MONDAY;
       HashMap<LocalTime,String>innermap = a.appointments.get(d);
       // innermap.put(time,s);
       // appointments.put(d,innermap);
       
       Set<Map.Entry<LocalTime , String>> set = innermap.entrySet();
       
       for(Map.Entry<LocalTime , String>set1:set){
           
           if(set1.getValue() ==null){
               
           System.out.print(set1.getKey()+"   ");
           System.out.println(set1.getValue()+ "  | ");
           }
          
       }
       
       System.out.println(innermap.size());
             */
       
       
       LocalDate now = new LocalDate();
        System.out.println(now.withDayOfWeek(DateTimeConstants.MONDAY)); //prints 2011-01-17
        System.out.println(now.withDayOfWeek(DateTimeConstants.SUNDAY)); //prints 2011-01-23
        
        
        Calendar cal=Calendar.getInstance();
        System.out.println(new SimpleDateFormat("EEE").format(cal.getTime()));
        
        Date d = new Date();
        System.out.println(d.getDate());
       
   }

    private Appointment fetch(String did) throws IOException {
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
    
}
