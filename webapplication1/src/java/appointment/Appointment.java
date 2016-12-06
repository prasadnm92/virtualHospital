/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import appointment.Days.Day;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author user
 */
public class Appointment  implements Serializable{
  //  private static DateTimeFormatter HH;
    private static final long serialVersionUID = 6091734954340005586L;
    int numberOfDays;
    public int numberOfSlots;
    //LocalTime start;
    //LocalTime end;
    Day day;
    public HashMap<Day,HashMap<LocalTime,String>>appointments;
    HashMap<LocalTime,String>temp;

    public Appointment() {
        this.appointments = new  HashMap<Day,HashMap<LocalTime,String>>();
    }
   
    public void storeInMAp(Day d1 , LocalTime start ,LocalTime end){
       
        DateTimeFormatter formatter;
        formatter = DateTimeFormat.forPattern("HH:mm");
        
        Days d = new Days();
        
        
        this.day = d1;
        
        d.setStartTime(start);
        d.setEndTime(end);
        d.generateSlots();
        
        this.numberOfSlots = d.returnSlots()+1;
        temp = d.map2;
       
        int count = numberOfSlots;
        LocalTime time = start;
        while(count!=0){
        //DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
      
            temp.put(time, null);
            time = time.plusMinutes(60);
            count--;
        }
        appointments.put(day, temp);
   
    }
    
    public void setNumberOfDays(int n){
        
        this.numberOfDays = n;
    }
    
   public void updateMap(String time1 , Day d, String s){
       
   
        DateTimeFormatter formatter;
        formatter = DateTimeFormat.forPattern("HH:mm");
    //    ap.storeInMAp(Day.SUNDAY, LocalTime.parse("10:30", formatter), LocalTime.parse("18:00",formatter));
        
        int trim = time1.lastIndexOf(":");
       
       String time2 = time1.substring(0, trim);
        
        System.out.println(time1);
          LocalTime time = LocalTime.parse(time2,formatter);
      //   LocalTime time = LocalTime.parse(time1);
          System.out.println(time);
        HashMap<LocalTime,String>innermap = appointments.get(d);
        innermap.put(time,s);
        appointments.put(d,innermap);
   }

    boolean checkExists(String time1, Day d) {
        
        
        HashMap<LocalTime,String>innermap = appointments.get(d);
        
        Set<Map.Entry<LocalTime ,String>>set = innermap.entrySet();
        
        for(Map.Entry<LocalTime,String>me : set){
            if(me.getKey().toString().equals(time1) && me.getValue() == null)
                return true;
        }
        return false;
      
     
    }
  
   
    
}
