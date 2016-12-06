<%-- 
    Document   : doctor Registration
    Created on : Mar 9, 2014, 7:09:11 PM
    Author     : vinay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

        <script type="text/javascript">
        
    function validatename(x){
        if(x.value.length === 0){
           alert("Name field is empty");
           x.focus();
           return false;
        } 
        return false;
    }
    
     function validatedob(x){
        if(x.value.length === ''){
           alert("dob field is empty");
           x.focus();
        } 
        var pattern = /^[0-9][1-9][-][0-9][1-9][-][0-9]{4}$/;
        if(!x.value.match(pattern)){
            alert("Enter proper DOB");
            x.focus();
        }
    }
    
    function validatephone(x){
         if(x.value.length === 0){
           alert("Phone number field is empty");
           x.focus();
           return false;
        } 
        var pattern = /^[7-9][0-9]{9}$/;
         if(!x.value.match(pattern)){
            alert("Enter valid mobile number ");
            x.focus();
        }
        return false;
    }
    
    function validateemail(x){
         if(x.value.length === 0){
           alert("Mail field is empty");
           x.focus();
        } 
        var atpos = x.value.indexOf("@");
        var dotpos = x.value.lastIndexOf(".");
        
        if( atpos<1 || dotpos<atpos+2 || dotpos+2 >= x.value.length || x.value.length>dotpos+4){
            alert("Not a valid email address");
            return false;
        }
    }
  
    function validatelicense(x){
        if(x.value.length === 0){
           alert("license field is empty");
           x.focus();
        }          
    }
    
    function validatequalification(x){
        if(x.value.length === 0){
           alert("qualification field is empty");
           x.focus();
        }          
    }
    
    function validatespecialization(x){
        if(x.value.length === 0){
           alert("Specialization field is empty");
           x.focus();
        }          
    }
    
    function validateyof(x){
        if(x.value.length === 0){
           alert(" Year when practice was started field is empty");
           x.focus();
           return false;
        }
    }
    
    
    
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
						<li class="nav2" id="active"><a href="dregister.jsp">Register</a></li>							
						<li class="nav5"><a href="dlogin.jsp">Login</a></li>
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
					<h2>Kindly<span> enter the details to register </span>!</h2>
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
							<form id="RegForm" method="post" action="doctorregister">
								
                                                                    
                                                                    <div class="wrapper">
                                                                        <strong><b>Name:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="name" id="name" value="${param.name}" onblur = "validatename(this);"></div>  
                                                                        
                                                                    </div>
                                                                    
                                                                    
                                                                    <div class="wrapper">
                                                                        <strong><b>License No:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="license" id="license" value="${param.license}" onblur = "validatelicense(this);"></div>
                                                                    </div>                                                                                                                                
                                                                  
                                                                        <div class="wrapper">
                                                                            <strong><b>Sex:</b></strong>
                                                                            <br/><select name="sex" id="sex">
                                                                                <option>Male</option>
                                                                                <option>Female</option>
                                                                                
                                                                            </select>
                                                                        </div>
                                                            
                                                                    
                                                                    	<div  class="wrapper">
                                                                            <strong><b>Qualification:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="qualification" id="qualification" value="${param.qualification}" onblur = "validatequalification(this);"></div>
									</div>
                                                                    
                                                                    <div  class="wrapper">
                                                                        <strong><b>Specialization:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="specialization" id="specialization" value="${param.specialization}" onblur = "validatespecialization(this);"></div>
									</div>
                                                                        
                                                                         <div class="wrapper">
                                                                             <strong><b>Department:</b></strong>
                                                                             <br/><select name="department" id="dept">
                                                                                <option>Cardiology</option>
                                                                                <option>ENT</option>
                                                                                <option>General Surgery</option>
                                                                                <option>Gynaecology</option>
                                                                                <option>Urology</option>
                                                                            </select>
                                                                         </div>
                                                                                
                                                                            
                                                            <div  class="wrapper">
                                                                        <strong><b>DOB:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="dob" id="dob" value="${param.dob}" onblur = "validatedob(this);"></div>
									</div>
                                                                    
                                                                    <div class="wrapper">
                                                                        <strong><b>Year when practice was started</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="yof" id="yof" value="${param.yof}" onblur = "validateyof(this);"></div>
                                                                    </div>
                                                            
                                                                    <div class="wrapper">
                                                                        <strong><b>URL of your blog</b></strong>
                                                                        <br/><div class="bg"><input type="url" class="input" name="url" id="url" value="${param.url}" ></div>
                                                                    </div>
                                                                    
                                                                    <div  class="wrapper">
                                                                        <strong><b>Phone:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="phone" id="phone" value="${param.phone}" onblur = "validatephone(this);"></div>
									</div>
                                                            
                                                                    <div  class="wrapper">
                                                                        <strong><b>Email:</b></strong>
                                                                        <br/><div class="bg"><input type="text" class="input" name="email" id="email" value="${param.email}" onblur = "validateemail(this);"></div>
									</div>
                                                            
                                                                    
                                                                    <div  class="textarea_box">
                                                                        <strong><b>Address:</b></strong>
                                                                        <br/><div class="bg"><textarea name="textarea" class="input" name="address" id="address" value="${address.email}" onblur = "validateaddress(this);"></textarea></div>
									</div>
                                                                    
                                                           
                                                                        <input type="Submit" value="Submit">	
                                                                        <input type="Reset" value="Clear">
                                                        </form>
						</div>	
						
					</article> 
                            </div>
                        </section>
                </div>
        </div>
    </body>
</html>

