<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    

		 <%
         	Cookie cookie = null;
         	Cookie[] cookies = null;
         	boolean logIn = false;
    		String username = "default";

        	// Get an array of Cookies associated with the this domain
         	cookies = request.getCookies();
  			if (cookies != null){
  				for (int i = 0; i < cookies.length; i++){
  					cookie = cookies[i];
  					if((cookie.getName( )).equals("loggedIn") )	
  					{
 						String cookieName = cookie.getValue();
						if (cookieName.contentEquals("true"))
						{
	  						logIn = true;
						}
  					}  else if (cookie.getName().equals("playerName")) {
  			   			String fakeName = cookie.getValue();
  			   			String name = "";
  			   			for (int j = 0; j < fakeName.length(); j++) {
  				            if (fakeName.charAt(j)=='=') {
  				            	name+=' ';
  				            }
  				            else {
  				            	name+=fakeName.charAt(j);
  				            }
  				        }
  			   			username = name;
  			   		}
  				}
  			}%>    
	<nav class = "navBar">
		<div class = "navLeft">
			<a href = "HomeGallery.jsp" style = "width:60px;margin-right: 100px">
				<img src = "images/CollabArtLogo_Ver2.png" id = "navLogo">
			</a>

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
							"<span class = \"navText\">" + username + "</span>" +
							"<a href=\"logout\" class = \"navText\">Log Out</a>"
							);
				}
			%>
		</div>
	</nav>
	<div id = "navDivider"></div>
	


     

                

        
        
        
