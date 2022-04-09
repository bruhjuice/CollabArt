<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    
    
    
    
    <!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Irish+Grover&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="css/navbar.css">
<title>CollabArt</title>


</head>

<body>

	<script id="replace_with_navbar" src="js/nav.js"></script>
	<form action = "login" method = "GET" class = "loginFormLeft">
	<div id = "loginBox">
	<div id = "loginBoxLogin">
	<h1> Log In</h1>
	</div> 
	<div id = "loginBoxBottom">
	<div id = "loginBoxFields">
	<input class = "regForm" type = "text" id = "loginEmail" name = "loginEmail" value = "" placeholder = "Username">
	<br>
	<input class = "regForm" type = "text" id = "loginPassword" name = "loginPassword" value = "" placeholder = "Password">
	<br>
	<button type="submit" id = "signInButton">Sign In</button>
	
	
	</div>
	
	<p id = "bottomText">Not a member? </p>
	<a href = "register.jsp" id = "bottomText2"> Sign up now!</a>
	
	</div>

	</div>
	</form>
<!-- TODO -->

     

                

        
        
        
</body>
</html>