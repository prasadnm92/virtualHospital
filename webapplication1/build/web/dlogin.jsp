<%-- 
    Document   : dlogin
    Created on : Mar 23, 2014, 11:58:09 AM
    Author     : user
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Doctor Registration</title>
        <!DOCTYPE html>
<html lang="en">
    <head>
        <title>Form</title>

<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<script type="text/javascript" src="js/jquery-1.4.2.js" ></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>  
<script type="text/javascript" src="js/Copse_400.font.js"></script>
<script type="text/javascript" src="js/jquery.nivo.slider.pack.js"></script>
<script type="text/javascript" src="js/imagepreloader.js"></script>
<script type="text/javascript">
	preloadImages([
	'images/menu1_active.gif',
	'images/menu2_active.gif',
	'images/menu3_active.gif',
	'images/menu4_active.gif',
	'images/marker_right_active.jpg',
	'images/marker_left_active.jpg',
	'images/menu5_active.gif']);
</script>





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
						<li class="nav2"><a href="dregister.jsp">Register</a></li>							
						<li class="nav5" id="active"><a href="dlogin.jsp">Login</a></li>
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
					<h2>Doctors please <span>Login here</span>!</h2>
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
							<form id="RegForm" method="post" action="drLogin">
							 <div id="error" style="display:block;"><font color="red">
                                                            <%if(request.getAttribute("Msg")!=null) { out.println(request.getAttribute("Msg"));} %>
                                                          </font></div>

                                                            <div>
									<div  class="wrapper">
                                                                            <strong><b>Doctor ID:</b></strong>
										<div class="bg"><input type="text" class="input" name="username" ></div>
									</div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Password:</b></strong>
                                                                            <div class="bg"><input type="password" class="input" name="password"></div>
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
    </body>
</html>

