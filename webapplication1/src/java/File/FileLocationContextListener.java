/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package File;

/**
 *
 * @author user
 */
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
        String rootPath = System.getProperty("catalina.home");
    	ServletContext ctx = servletContextEvent.getServletContext();
    	
        String relativePath1 = "prescription";
    	File file1 = new File(rootPath + File.separator + relativePath1);
        
        String relativePath2 = "images";
    	File file2 = new File(rootPath + File.separator + relativePath2);
        
        
    	if(!file1.exists()) 
            file1.mkdirs();
        
        if(!file2.exists()) 
            file2.mkdirs();
        
    	
        System.out.println("File Directory created to be used for storing files");
    	
        ctx.setAttribute("FILES_PRE_FILE", file1);
    	ctx.setAttribute("FILES_PRE", rootPath + File.separator + relativePath1);
        
        ctx.setAttribute("FILE_IMG_FILE", file2);
    	ctx.setAttribute("FILES_IMG", rootPath + File.separator + relativePath2);
        
        
    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		//do cleanup if needed
	}
	
}
