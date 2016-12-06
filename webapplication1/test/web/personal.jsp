<!DOCTYPE html>
<html lang="en">
<head>
<title>Personalized Page</title>
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
								<li><a href="index.html">Logout</a></li>
                                                                <li name = "username">
                                                                    <a href="personal.jsp">
                                                                        <%=
                                                                           request.getSession().getAttribute("patientname")
                                                                        %>
                                                                    </a></li>
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
                                                            <li><a href="#">Previous Appointment</a></li>
                                                            <li><a href="#">Fix an appointment</a></li>
                                                        </ul>
                                                    
                                                    </li>
                                                        <li><a href="#">Prescriptions</a>
                                                            <ul>
                                                                <li><a href="#">View Prescription</a></li>
                                                                <li><a href="#">Complaint</a></li>
                                                            </ul>
                                                        </li>
							<li><a href="#">Doctor Details</a></li>
							<li><a href="#">Edit Profile</a></li>
							<li><a href="#">Change Dept/Doctor</a></li>
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