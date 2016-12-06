/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.socket;

/**
 *
 * @author user
 */
import com.ui.ChatFrame;
import java.io.*;
import java.net.*;

public class Download implements Runnable{
    
    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";
    
    public InputStream In;
    public FileOutputStream Out;
    public ChatFrame ui;
    
    /**
     *
     * @param saveTo
     * @param ui
     */
    public Download(String saveTo, ChatFrame ui){
        try {
            server = new ServerSocket(0x0);//creates a server 								   socket bound to port 0.
            port = server.getLocalPort();
            this.saveTo = saveTo;
            this.ui = ui;
        } 
        catch (IOException ex) {
            System.out.println("Exception [Download : Download(...)]");
        }
    }

    @Override
    public void run() {
        try {
            socket = server.accept();//listens for a connection 								to be made and accepts it. 
            System.out.println("Download : "+socket.getRemoteSocketAddress());
            
            In = socket.getInputStream(); 
            Out = new FileOutputStream(saveTo);
            
            byte[] buffer = new byte[1024];
            int count;
            
            while((count = In.read(buffer)) >= 0){
                Out.write(buffer, 0, count);
            }
            
            Out.flush();
            
            ui.jTextArea1.append("[Application > Me] : Download complete\n");
            
            if(Out != null){ Out.close(); }
            if(In != null){ In.close(); }
            if(socket != null){ socket.close(); }
        } 
        catch (IOException ex) {
            System.out.println("Exception [Download : run(...)]");
        }
    }
}
