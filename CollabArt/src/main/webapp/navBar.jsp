<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    
    
    



	<nav class = "navBar">
		<div class = "navLeft">
			<img src = "images/spongebob.jpg" id = navLogo>
			<a class="navText" id="create-room">Create Room</a>
			<form id = "navJoin" action = "JOINROOMDISPATCHER" method = "GET">
				<a href="#" onclick="$(this).closest('form').submit()" class="navText">Join Room</a>
				<input type = "text" id = "roomCode" placeholder = "[type code]">
			</form>
		</div>
		<div class = "navRight">
			<%
			boolean logIn = true;
				if (!logIn) {
					out.println(
							"<a href=\"login.jsp\" class = \"navText\">Log In</a>" +
							"<a href=\"register.jsp\" class = \"navText\">Register</a>"
							);
				} else {
					out.println(
							"<a href=\"login.jsp\" class = \"navText\">Log Out</a>"
							);
				}
			%>
		</div>
	</nav>
	<div id = "navDivider"></div>
	


     

                

        
        
        
