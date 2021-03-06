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
<%
	String errorTxt = (String) request.getAttribute("error");
	String usernameTxt = (String) request.getAttribute("username");
%>

<script>
	function errorAlert() {
		console.log("alerted");
		if (<%=errorTxt != null && errorTxt.equals("yes") %>) {
			alert("Username and/or Password is invalid");
			
		}
	}
</script>

</head>

<body onload = "errorAlert()">
	<script id="replace_with_navbar" src="js/nav.js"></script>
	<form action = "login" method = "GET" class = "loginFormLeft">
	<div id = "loginBox">
	<div id = "loginBoxLogin">
	<h1> Log In</h1>
	</div> 
	<div id = "loginBoxBottom">
	<div id = "loginBoxFields">
	<input class = "regForm" type = "text" id = "loginEmail" name = "loginEmail" value = <%=usernameTxt%> placeholder = Username required
		oninvalid="this.setCustomValidity('Please fill out username')"
  		oninput="this.setCustomValidity('')"/>
	<br>

	<input class = "regForm" type = "password" id = "loginPassword" name = "loginPassword" value = "" placeholder = Password required
		oninvalid="this.setCustomValidity('Please fill out password')"
  		oninput="this.setCustomValidity('')"/>
		  
	<br>
	<button type="submit" id = "signInButton">Sign In</button>
	
	
	</div>
	
	<p id = "bottomText">Not a member? </p>
	<a href = "register.jsp" id = "bottomText2"> Sign up now!</a>
	
	</div>

	</div>
	</form>

</body>

<script>
	if (<%=usernameTxt == null || usernameTxt.equals("")%>) {
		document.querySelector('#loginEmail').value = "";
	}
</script>
</html>