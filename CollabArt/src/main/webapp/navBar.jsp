<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    

		 <%
         Cookie cookie = null;
         Cookie[] cookies = null;
         boolean logIn = false;
         // Get an array of Cookies associated with the this domain
         cookies = request.getCookies();
      
  			 if (cookies != null){
  				for (int i = 0; i < cookies.length; i++){
  					cookie = cookies[i];
  					if((cookie.getName( )).equals("loggedIn")  )
  						
  					{
  						String name = "";
  						//convert name back to real name
 						String cookieName = cookie.getValue();
 						for (int j = 0; j < cookieName.length(); j++) {
 				            if (cookieName.charAt(j)=='=') {
 				            	name+=' ';
 				            }
 				            else {
 				            	name+=cookieName.charAt(j);
 				            }
 				        }
						if (name.contentEquals("true"))
						{
	  						logIn = true;
						}
  					}
  					
  				}
  				
  			}%>    
	<nav class = "navBar">
		<div class = "navLeft">
			<img src = "images/CollabArtLogo_Ver2.png" id = "navLogo">
			<a href = "HomeGallery.jsp" id = "galleryNav" > Gallery </a>
			<a href="#" class="navText" id="create-room">Create Room</a>
			<a href="#" class="navText" id="join-room">Join Room</a>
				
		</div>
		<div class = "navRight">
		

		
		
			<%
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
	


     

                

        
        
        
