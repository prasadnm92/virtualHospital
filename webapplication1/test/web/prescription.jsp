<%-- 
    Document   : prescription
    Created on : Apr 28, 2014, 5:31:27 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Prescription</title>

<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">





    </head>
    
    <body>
        <div class="body1">
            <div class="body2">
		<div class="main">
        <header>
        <div class="wrapper">
					<h1><a href="index.html" id="logo">First</a></h1>
                                        <div class="right">
						<nav>
	
						</nav>
					</div>
					
	</div>
            <nav id="menu">
					<ul>
						
					</ul>
				</nav>
        </header>
                </div>
            </div>
        </div>
        <div class="body5">
		<div class="body6">
			<div class="main">
				<div class="wrapper">
					<figure class="left"><img src="images/letter5.png" alt=""></figure>
					<h2>Kindly<span>Fill in the prescription details  </span>!</h2>
					</div>
			</div>
		</div>
	</div>
        <div class="body7">
		<div class="main">
			<section id="content">
				
                            <div class="wrapper">
<article class="col2">
						<div class="pad1">
							<form id="RegForm" method="post" action="prescriptionstore">
								
                                                                    
                                                                    <div class="wrapper">                                                                        
                                                                            <strong><b>License Number</b></strong>                                                                        
                                                                            <br/><div class="bg"><input type="text" class="input" name="doctorname"></div>  
                                                                        
                                                                    </div>
                                                                    
                                                                    
                                                                    <div class="wrapper">
                                                                        <strong><b>Patient Name:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="patientname"></div>
                                                                    </div>
                                                            
                                                                  
                                                                        <div class="wrapper">
                                                                            <b>Sex:</b>
                                                                            <br/><select name="sex">
                                                                                <option>Male</option>
                                                                                <option>Female</option>
                                                                                
                                                                            </select>
                                                                        </div>
                                                            
                                                                        <div class="wrapper">
                                                                            <strong><b>Age:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name = "age"></div>
                                                                        </div>
                                                            
                                                                    
                                                                    	<div  class="wrapper">
                                                                            <strong><b>Tablet-1:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="tablet1"></div>
									</div>
                                                                    
                                                                                                                               
                                                                     <div class="wrapper">
                                                            
                                                                    <strong><b>Dosage-1:</b></strong>
                                                                    <br/><input type="checkbox" name="Dosage-1" value="Morning"/>Morning<br>
                                                                    <input type="checkbox" name="Dosage-1" value="Afternoon"/>Afternoon<br>
                                                                    <input type="checkbox" name="Dosage-1" value="Evening"/>Evening
                                                                    </div>
                                                            
									
                                                                                                                            
                                                            <div  class="wrapper">
                                                                        <strong><b>Tablet-2:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="tablet2"></div>
									</div>
                                                                        
                                                             
                                                            
                                                            <div class="wrapper">
                                                            
                                                                    <strong><b>Dosage-2:</b></strong>
                                                                    <br/><input type="checkbox" name="Dosage-2" value="Morning"/>Morning<br>
                                                                    <input type="checkbox" name="Dosage-2" value="Afternoon"/>Afternoon<br>
                                                                    <input type="checkbox" name="Dosage-2"value="Evening"/>Evening
                                                                    </div>
                                                                                                                                                                                            
                                                             <div class="wrapper">
                                                                        <strong><b>Tablet-3</b></strong>
                                                                       <br/><div class="bg"><input type="text" class="input" name="tablet3"></div>
                                                             </div>
                                                              
                                                            
                                                            <div class="wrapper">
                                                            
                                                                    <strong><b>Dosage-3:</b></strong>
                                                                    <br/><input type="checkbox" name="Dosage-3" value="Morning"/>Morning<br>
                                                                    <input type="checkbox" name="Dosage-3" value="Afternoon"/>Afternoon<br>
                                                                    <input type="checkbox" name="Dosage-3" value="Evening"/>Evening
                                                                    </div>
                                                            
                                                            
                                                                    
                                                            
                                                                    <div  class="wrapper">
                                                                        <strong><b>Tablet-4:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="tablet4"></div>
									</div>
                                                            
                                                            
                                                            <div class="wrapper">
                                                            
                                                                    <strong><b>Dosage-4:</b></strong>
                                                                    <br/><input type="checkbox" name="Dosage-4" value="Morning"/>Morning<br>
                                                                    <input type="checkbox" name="Dosage-4" value="Afternoon"/>Afternoon<br>
                                                                    <input type="checkbox" name="Dosage-4" value="Evening"/>Evening
                                                                    </div>
                                                            
                                                                    
                                                                    
                                                            
                                                                        <div  class="wrapper">
                                                                            <strong><b>Tablet-5:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="tablet5"></div>
									</div>
                                                            
                                                            
                                                                        <strong><b>Dosage-5:</b></strong>
                                                                    <br/><input type="checkbox" name="Dosage-5" value="Morning"/>Morning<br>
                                                                    <input type="checkbox" name="Dosage-5" value="Afternoon"/>Afternoon<br>
                                                                    <input type="checkbox" name="Dosage-5" value="Evening"/>Evening
                                                                    </div>
    
                                                                     <div  class="wrapper">
                                                                        <strong><b>No of Days:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="numberofdays"></div>
									</div>
    
                                                                        <div  class="textarea_box">
										<b>Note:</b>
										<div class="bg"><textarea name="textarea" name="note"></textarea></div>						
									</div>
                                                                    
                                                                     

                                                            
                                                                        
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
									 <br/><br/><input type="Submit" value=" Submit ">
                                                                        <input type="Reset" value="  Clear  ">
								</div>
                                                        </form>
						</div>	
						
					</article> 
                            </div>
                        </section>
                </div>
        </div>
    </body>
</html>


