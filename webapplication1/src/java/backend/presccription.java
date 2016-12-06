/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

/**
 *
 * @author user
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;

/**
 *
 * @author vathsa
 */
public class presccription {
    
    
    String doctorname;
    String patientname;
    
    int numberOfDays;
    int numberOfTablets;
    
    
    int age;
    String sex;
    String note;
    
    public HashMap<String ,tablets>prescriptionMap;
    
    
   public presccription() {
         this.prescriptionMap = new HashMap<>();
    }

    public void setAge(int n){
        this.age = n;
    }
    
    public void setSex(String sex){
        this.sex = sex;
    }
    
    public int getAge(){
        
        return this.age;
    }
    
    public String getSex(){
        
        return this.sex;
    }
    
    public void setDoctorName(String name){
        
        this.doctorname = name;
    }
    
    public void setPatientName(String name){
        
        this.patientname = name;
    }
    
    public void setDays(int n){
        this.numberOfDays = n;
    }
    
    public void setTablets(int n){
        this.numberOfTablets = n;
    }
    
  
    
     public String getDoctor(){
         
         return doctorname;
     }
    
     public String getPatient(){
         
         return patientname;
     }
     
     public int getNumberOfDays(){
         return numberOfDays;
     } 
     
     public int getNumberOfTablets(){
         return numberOfTablets;
     } 
     
     public void enterTablet(String tabletName , tablets t){
         
         this.prescriptionMap.put(tabletName, t);
         
     }
     
       public String getNote(){
         
         return note;
     }
     
     public void SetNote(String note){
         this.note = note;
     } 
     
}

