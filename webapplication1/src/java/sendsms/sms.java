/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sendsms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author games
 */
public class sms {

    /**
     * @param args the command line arguments
     */
    
    public void delegate(String number , String patientname , String food , String medicine) throws MalformedURLException, IOException{
        
        
         String msg = "VIRTUAL%20HOSPITAL%0A";
        String msg1 = "%0AHello"+"%20"+patientname;
        String msg2 = "%0AThis%20is%20a%20reminder%20to%20take%20the%20following%20medicines%20"+food+":%0A";
        //String msg3 = medicine;
        String message = msg+msg1+msg2+medicine;
            // 1. URL
        String Url = "https://site2sms.p.mashape.com/index.php?";
        String text1 = "uid="+"9886071109";
        String text2 = "&pwd="+"srivathsa";
        String text3 = "&phone="+number;
        String text4 = "&msg="+message;
        
        String finalUrl = Url+text1+text2+text3+text4;
        URL url;
        url = new URL(finalUrl);
 
        // 2. Open connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 
        // 3. Specify POST method
        conn.setRequestMethod("GET");
 
        // 4. Set the headers
        //conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-Mashape-Authorization", "zxIeiJEdrJ4sjNb6MgRZTyCC9V1c7rOa");
 
        conn.setDoOutput(true);
 
 
     
 
            // 6. Get the response
            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
 
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
 
            // 7. Print result
            System.out.println(response.toString());
 
          
        }
        
        
    }
    
    
        

    

