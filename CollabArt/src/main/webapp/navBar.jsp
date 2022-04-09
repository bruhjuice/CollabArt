<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    
    
    



	<nav class = "navBar">
		<div class = "navLeft">
			<img src = "images/spongebob.jpg" id = navLogo>
			<a href = "gameStart.jsp" id = "navText">Create Room</a>
			<form id = "navJoin" action = "JOINROOMDISPATCHER" method = "GET">
				<a href="#" onclick="$(this).closest('form').submit()" id = "navText">Join Room</a>
				<input type = "text" id = "roomCode" placeholder = "[type code]">
			</form>
		</div>
		<div class = "navRight">
			<%
			boolean logIn = true;
				if (!logIn) {
					out.println(
							"<a href=\"login.jsp\" id = \"navText\">Log In</a>" +
							"<a href=\"register.jsp\" id = \"navText\">Register</a>"
							);
				} else {
					out.println(
							"<a href=\"login.jsp\" id = \"navText\">Log Out</a>"
							);
				}
			%>
		</div>
	</nav>
	<div id = "navDivider"></div>
	


     

                

        
        
        
