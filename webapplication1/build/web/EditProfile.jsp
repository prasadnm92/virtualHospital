<%-- 
    Document   : EditProfile
    Created on : Apr 30, 2014, 7:41:22 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Edit Profile</title>

<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<script type="text/javascript" src="js/newjavascript.js" ></script>
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
					<h2>Edit your Profile <span>here</span>!</h2>
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
							<form id="RegForm" method="post">
								<div>
									
                                                                    <div class="body17">
                                                                    <div id="fileinput">
                                                                            <label for="fileID"><img src="pix/johnmatthews.jpg"></label>
                                                                            <input type="file" id="fileID"></div>
                                                                    </div>
                                                                    
                                                                    
                                                                    <div  class="wrapper">
                                                                            <strong><b>Name:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="docname" ></div>
									</div>
                                                                    
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>URL of your homepage</b></strong>
                                                                            <br/><div class="bg"><input type="url" class="input" name="homepage"></div>
                                                                        </div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Mobile Number</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="mob"></div>
                                                                        </div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Email</b></strong>
                                                                            <br/><div class="bg"><input type="email" class="input" name="emaile"></div>
                                                                        </div>
                                                                    
                                                                        <div  class="textarea_box">
                                                                            <strong><b>Address:</b></strong>
                                                                            <br/><div class="bg"><textarea name="addr"></textarea></div>						
									</div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Years of Experience</b></strong>
                                                                            <br/><div class="bg"><input type="number" class="input" name="yoe" min="1" max="50"></div>
                                                                        </div>
                                                                    
                                                                   
                                                                            
                                                                        
                                                                          
									<a href="#" class="button" onClick="document.getElementById('RegForm').submit()"><span><span>Submit</span></span></a>
									<a href="#" class="button" onClick="document.getElementById('RegForm').reset()"><span><span>Clear</span></span></a>
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

