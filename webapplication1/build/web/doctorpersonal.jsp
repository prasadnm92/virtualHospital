<%-- 
    Document   : doctorpersonal
    Created on : Apr 25, 2014, 2:08:56 PM
    Author     : user
--%>

<<!DOCTYPE html>
<html lang="en">
<head>
<title>Personalized Doctor Page</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout1.css" type="text/css" media="all">
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
<!--[if lt IE 9]>
	<script type="text/javascript" src="http://info.template-help.com/files/ie6_warning/ie6_script_other.js"></script>
	<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
</head>
<body id="page1">
<div class="body1">
	<div class="body2">
		<div class="main">
<!-- header -->
			<header>
				<div class="wrapper">
					<h1><a href="index.html" id="logo">First</a></h1>
					<div class="right">
						<nav>
							<ul id="top_nav">                                                            
                                                  
                                                                
								<li name="username"><div class="body15">
                                                                        <%=
                                                                           (String)request.getAttribute("Msg")
                                                                        %>
                                                                        </div></li><br/><br/>
                                                                        <li><a href="index.html" onclick=""><div class="body16">Logout</div></a></li>
                                                                        <li><a href="changePassword.jsp"><div class="body16">Change Password</div></a></li>
							</ul>
                                                            
                                                               
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
	
<!-- / header -->
<!-- content -->
	<div class="body5">
		<div class="body6">
			<div class="main">
				<div class="wrapper">
					<figure class="left"><img src="images/letter1.png" alt=""></figure>
					<h2>Welcome to <span>Virtual Hospital</span>!</h2>
					This is your personalized page, Please feel free to explore the various options provided.
				</div>
			</div>
		</div>
	</div>
	<div class="body7">
		<div class="main">
			<section id="content">
				<div class="wrapper">
				
				
				
				</div>
				<div class="wrapper">
                                <article class="col1 pad_left1">
						<h2>Tasks</h2>
						<ul class="list2">
                                                    <li><a href="#">Appointments</a>
                                                        <ul>
                                                            <%
                                                            String did=(String) request.getSession().getAttribute("doctorname");
                                                            %>
                                                            <li><a href="ProvideSlots.jsp?doctorid=$<%= did %>">Provide Slots</a></li>
                                                            <li><a href="#">New Appointments</a></li>
                                                        </ul>
                                                    
                                                    </li>
                                                    <li><a href="#">Registered Patients</a></li>                                                                                                                   
                                                    <li><a href="#">Start a Chat</a></li>
						    <li><a href="EditProfile.jsp">Edit Profile</a></li>
						    <li><a href="prescription.jsp">Provide Prescription</a></li>
                                                    <li><a href="complaints.jsp">Complaints</a></li>
						</ul>
						
					</article>
				</div>
			</section>
		</div>
	</div>
			<!-- / content -->
			<!-- footer -->
	<div class="body8">
	<div class="body9">
		<div class="main">
			<footer>
				
			</footer>
		</div>
<!-- / footer -->
	</div>
</div>

</body>
</html>
