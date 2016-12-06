/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package backend;

/**
 *
 * @author vathsa
 */
import backend.Dosage;

public class tablets {
    
   
   
    String name;
    String quantity;
    
    public Dosage dose[];
   

    public tablets() {
       //To change body of generated methods, choose Tools | Templates.
      
        dose = new Dosage[3];
    }
   
    public String getName(){
        return name;
    }
    
   public void setName(String name){
       this.name =  name;
   }
    
   public void setQuantiy(String qnt){
       this.quantity = qnt;
   }
    
  public String getQuantity(){
      return this.quantity;
  }

}
