<%-- 
    Document   : complaints
    Created on : Apr 30, 2014, 7:43:48 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Doctor Complaints</title>

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
					<h2>Your Complaints are taken<span> Seriously</span>!</h2>
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
							
							<form id="ContactForm" method="post" action="send_form_email.php">
								<div>
									<div  class="wrapper">
                                                                            <strong>Name:</strong>
										<div class="bg"><input type="text" class="input" name="dname" ></div>
									</div>
									<div  class="wrapper">
                                                                            <strong>Email:</strong>
                                                                            <div class="bg"><input type="text" class="input" name="demail"></div>							
									</div>
									<div  class="textarea_box">
                                                                            <strong>Complaint:</strong>
                                                                            <div class="bg"><textarea name="dcompaint" cols="1" rows="1"></textarea></div>						
									</div>
									<br/><input type="Submit" value="Submit">
                                                                    <input type="Reset" value="  Clear  ">
                                                                    
								</div>
							</form>
						</div>	
						
					</article>
					
				</div>
				
			</section>
		</div>
	</div>
	
