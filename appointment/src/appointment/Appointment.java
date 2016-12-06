/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import appointment.Days.Day;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.*;
import static org.joda.time.PeriodType.time;
import org.joda.time.format.*;
import static org.joda.time.format.ISODateTimeFormat.time;

/**
 *
 * @author user
 */
public class Appointment {
  //  private static DateTimeFormatter HH;

    int numberOfDays;
    public int numberOfSlots;
    //LocalTime start;
    //LocalTime end;
    Day day;
    HashMap<Day,HashMap<LocalTime,String>>appointments;
    HashMap<LocalTime,String>temp;

    public Appointment() {
        this.appointments = new  HashMap<Day,HashMap<LocalTime,String>>();
    }
   
    public void storeInMAp(Day d1 , LocalTime start ,LocalTime end){
      
       
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
            time = time.plusMinutes(30);
            count--;
        }
        appointments.put(day, temp);
   
    }
    
    public void setNumberOfDays(int n){
        
        this.numberOfDays = n;
    }
    
   public void updateMap(LocalTime time , Day d, String s){
       
    HashMap<LocalTime,String>innermap = appointments.get(d);
    innermap.put(time,s);
    appointments.put(d,innermap);
   }
    /**
     * @param args the command line arguments
     */
   
    
}
