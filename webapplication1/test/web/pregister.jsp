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
    function validator()
    {
        var name = document.getElementById('Name');
        var age = document.getElementById('age');
        var no = document.getElementById('number');
        var em = document.getElementById('email');
        var user = document.getElementById('username');
        var pass1 = document.getElementById('password1');
        var pass2 = document.getElementById('password2');
        var sym = document.getElementById('symptoms');
        
        if(name.value.length === 0){
            alert("Name field is empty");
            name.focus();
            return false;
        }
            
        else if(age.value.length === 0){
            alert("Age field is empty");
            age.focus();
            return false;
        }
            
        else if(no.value.length === 0)
            alert("Mobile Number field is empty");
        
        else if(em.value.length === 0)
            alert("Email field is empty");
        else if(user.value.length === 0)
            alert("Username field is empty");
        else if(pass1.value.length === 0)
            alert("Password field is empty");
        else if(pass2.value.length === 0)
            alert("Confirm Password field is empty");
        else if(sym.value.length === 0)
            alert("Please enter your symptoms");
        else if(pass1.value !== pass2.value){
             alert("Passwords do not match");
             document.formName.elements['hiddenValue'].value = false;
            // document.getElementById("RegForm").submit();
         }
        else{
            document.formName.elements['hiddenValue'].value = true;
            //document.location.href = "/patientregister";        
        }
            
    }
    
    function validatename(x){
        if(x.value.length === 0){
           alert("Name field is empty");
           x.focus();
           return false;
        } 
        return false;
    }
    
     function validateage(x){
        if(x.value.length === ''){
           alert("age field is empty");
           x.focus();
        } 
        var pattern = /^[0-9][0-9]?$/;
        if(!x.value.match(pattern)){
            alert("Enter proper age");
            x.focus();
        }
    }
    
    function validatemobile(x){
         if(x.value.length === 0){
           alert("Mobile field is empty");
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
  
    function validateaddress(x){
        if(x.value.length === 0){
           alert("Address field is empty");
           x.focus();
        }          
    }
    
    function validatesymptoms(x){
        if(x.value.length === 0){
           alert("Symptoms field is empty");
           x.focus();
        }          
    }
    
    function validateusername(x){
        if(x.value.length === 0){
           alert("Username field is empty");
           x.focus();
        }          
    }
    
    function validatepassword1(x){
        if(x.value.length === 0){
           alert("password1 field is empty");
           x.focus();
           return false;
        }
    }
    
    function validatepassword2(x){
       //var pass1=document.forms["RegForm"]["password1"].value;
        var pass1 = document.getElementById('password1');
        if(x.value.length === 0){
           alert("password2 field is empty");
           x.focus();
           return false;
        }
        if(x.value !== pass1.value){
            alert("Passwords doesn't match");
            pass1.focus();
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
						<li class="nav2" id="active"><a href="pregister.jsp">Register</a></li>							
						<li class="nav5"><a href="plogin.jsp">Login</a></li>
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
					<h2>Patients please <span>Register/Login here</span>!</h2>
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
							<form id="RegForm" method="post" action  = "patientregister">
                                                                <input type=hidden name="hiddenValue" value=""/>  
								<div>
									<div  class="wrapper">
                                                                            <strong><b>Name:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="Name" id="Name" value="${param.Name}" onblur = "validatename(this);"></div>
									</div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Age:</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="age" id="age" value="${param.age}"  onblur = "validateage(this);"></div>
                                                                        </div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Sex:</b></strong>
                                                                            <br/><select name="sex">
                                                                                <option>Male</option>
                                                                                <option>Female</option>
                                                                                <option>Other</option>
                                                                            </select>
                                                                            
                                                                            
                                                                        </div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Mobile</b></strong>
                                                                            <br/><div class="bg"><input type="text" class="input" name="number" id="number" value="${param.number}" onblur = "validatemobile(this);"></div>
                                                                        </div>                                                                                                                                       
                                                                                                                                                 
									<div  class="wrapper">
                                                                            <strong><b>Email:</b></strong>
                                                                            <br/><div class="bg"><input type="email" class="input" name="email" id="email" value="${param.email}" onblur = "validateemail(this);">	</div>							
									</div>
                                                                        
                                                                      
									<div  class="textarea_box">
                                                                            <strong><b>Address:</b></strong>
                                                                            <br/><div class="bg"><textarea name="address" class="input" value="${param.address}" onblur = "validateaddress(this);" ></textarea></div>						
									</div>
                                                                    
                                                                    <div  class="textarea_box">
                                                                        <strong><b>Symptoms:</b></strong>
                                                                        <br/><div class="bg"><textarea name="symptoms" class="input" id="symptoms" value="${param.address}" onblur = "validatesymptoms(this);" ></textarea></div>						
									</div>
                                                                    
                                                                        <div class="wrapper">
                                                                            <strong><b>Department:</b></strong>
                                                                            <br/><select name="Department" id="dept">
                                                                                <option>cardiology</option>
                                                                                <option>ENT</option>
                                                                                <option>General Surgery</option>
                                                                                <option>Gynaecology</option>
                                                                                <option>Urology</option>
                                                                            </select>
                                                                         </div>
                                                                    <div class="wrapper">
                                                                        <strong><b>Username:</b></strong>
                                                                        <br/><pre>(Follow the format: First three letters of your name followed by your
age[number], your sir name[first three letters] or Initial. )</pre>
                                                                        <div class="bg"><input type="text" class="input" name="username" id="username" value="${param.username}" onblur = "validateusername(this);" ></div>
                                                                        </div>        
                                                                    
                                                                    <div class="wrapper">
                                                                        <strong><b>Password:</b></strong>
                                                                        <br/><div class="bg"><input type="password" class="input" name="password1" id="password1" onblur = "validatepassword1(this);" ></div>
                                                                    </div>
                                                                    
                                                                     <div class="wrapper">
                                                                         <br/><strong><b>Confirm Password:</b></strong>
                                                                         <br/><div class="bg"><input type="password" class="input" name="password2" id="password2" onblur = "validatepassword2(this);"></div>
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
