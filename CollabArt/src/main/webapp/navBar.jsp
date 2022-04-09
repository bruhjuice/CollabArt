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
         boolean loggedIn = false;
         Cookie cookie = null;
         Cookie[] cookies = null;
         
         // Get an array of Cookies associated with the this domain
         cookies = request.getCookies();
         

      
        <ul class = "navBar">
  			<li><a href="index.jsp" class = "logo">Foodie!</a></li>
  			<% if (cookies != null){
  				for (int i = 0; i < cookies.length; i++){
  					cookie = cookies[i];
  					if((cookie.getName( )).equals("name")  )

  					{
  						
  						String realName = "";
 						String cookieName = cookie.getValue();
 						for (int j= 0; j < cookieName.length(); j++)
 						{
 							if (cookieName.charAt(j) == '=')
 							{
 								realName += ' ';
 							}
 							else
 							{
 								realName += cookieName.charAt(j);
 							}
 						}
  						loggedIn = true;
  						out.print("<li id = 'topHelloBar'><a id = 'helloBar'> Hi " + realName +"!" +  "</a></li>");
  					}
  					
  				}
  				
  			}%>
		
		
			<%
			boolean logIn = true;
				if (!logIn) {
					out.println(
							"<a href=\"login.jsp\" class = \"navText\">Log In</a>" +
							"<a href=\"register.jsp\" class = \"navText\">Register</a>"
							);
				} else {
					out.println(
							"<a href=\"logout\" class = \"navText\">Log Out</a>"
							);
				}
			%>
		</div>
	</nav>
	<div id = "navDivider"></div>
	


     

                

        
        
        
