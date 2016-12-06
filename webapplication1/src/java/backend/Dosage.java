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
public class Dosage {
    
    public enum Time {

     MORNING,AFTERNOON,EVENING;
//     int food;

 
 }

 boolean Food;

 Time time;

 void setTime(Time t){

     time = t;
 }

 Time getTime(){

     return time;
 }

 void setFood(int n){
     if(n == 1)
        Food = true;
     else
         Food = false;

 }

 boolean getFood(){
     return Food;
 }
}
