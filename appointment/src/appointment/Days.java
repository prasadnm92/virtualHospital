/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package appointment;

import java.sql.Time;
import java.util.HashMap;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

/**
 *
 * @author user
 */
public class Days {
    
    HashMap<LocalTime,String>map2;
    
    public Days(){
    
        map2 = new HashMap<>();
}
    
    public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}
     int numberOfSlots;
     LocalTime startTime;
     LocalTime endTime;
     Day day;
     
     
     
     public void setDay(Day d){
         this.day = d;   
     }
     
     public void setStartTime(LocalTime t){
        this.startTime = t;
    }
    
    public void setEndTime(LocalTime t){
        this.endTime = t;
    }
    
    public void generateSlots(){
        
        int totalTime;
        int minutes = Minutes.minutesBetween(startTime, endTime).getMinutes(); 
        totalTime = minutes/60;
        this.numberOfSlots = totalTime * 2;
   
    }
    
    public int returnSlots(){
        return  this.numberOfSlots;
    }
    
    public Day fetchDay(){
        return this.day;
    }
     
}
