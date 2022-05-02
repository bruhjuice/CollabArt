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
	String regUsername = (String) request.getAttribute("regUsername");
	System.out.println(regUsername);
	String regPassword = (String) request.getAttribute("regPassword");
	String errorTxt = (String) request.getAttribute("error");
%>

</head>

<script>
	function errorAlert() {
		console.log("alerted");
		if (<%=errorTxt != null && errorTxt.equals("yes") %>) {
			alert("Please complete the erroneous fields");
		} else if (<%=errorTxt != null && errorTxt.equals("different") %>) {
			alert("Passwords are different from each other");
		} else if (<%=errorTxt != null && errorTxt.equals("taken") %>) {
			alert("Username is taken");
		} else if (<%=errorTxt != null && errorTxt.equals("invalidUser") %>) {
			alert("The username contains improper characters");
		}
	}
</script>


<body onload = "errorAlert()">
	<script id="replace_with_navbar" src="js/nav.js"></script>
	<form action = "register" method = "GET" class = "loginFormLeft">
	<div id = "loginBox">
	<div id = "loginBoxLogin">
	<h1> Register</h1>
	</div> 
	<div id = "loginBoxBottom">
	<div id = "loginBoxFields">
	<input class = "regForm" type = "text" id = "loginEmail" name = "registerName" placeholder = "Username" required
		oninvalid="this.setCustomValidity('Please complete your Username')"
  		oninput="this.setCustomValidity('')"/>
	<br>
	<input class = "regForm" type = "password" id = "loginPassword" name = "registerPassword" placeholder = "Password" required
		oninvalid="this.setCustomValidity('Please complete the required password sections')"
  		oninput="this.setCustomValidity('')"/>
	<br>
	<input class = "regForm" type = "password" id = "loginPassword" name = "confirmPassword" placeholder = "Confirm Password" required
		oninvalid="this.setCustomValidity('Please complete the required password sections')"
  		oninput="this.setCustomValidity('')"/>
	<br>
	<button type="submit" id = "signInButton">Create Account</button>
	
	
	</div>
	<p id = "bottomText3">Already a member? </p>
	<a href = "login.jsp" id = "bottomText2"> Log in Here!</a>
	
	</div>

	</div>
	</form>

<script>
	if (<%=regUsername != null && !regUsername.equals("")%>) {
		console.log("Weird");
		document.querySelector('#loginEmail').value = '<%=regUsername%>';
	}
</script>

</body>
</html>