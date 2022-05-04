<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Irish+Grover&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/navbar.css">
<title>playGame</title>
<style>
	.outer-container {
		display: flex;
		justify-content: center;
		flex-direction: column;
	}
	.input-container {
		background-color: #ACEBFF;
		font-size: 30px;
		height: 40%;
		width: 38%;
		text-align: center;
		margin: 0 auto;
		padding: 3%;
		border-radius: 70px;
		box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.5);
		max-width: fit-content;
	}
	
	.inputs{
		margin-bottom: 30px;
		max-width:420px;
		height:40px;
		border: 2px;
		padding: 5px;
		text-align: center;
		background-color: #E7FBFF;
		border-radius: 5px;
		font-family: 'Irish Grover', cursive;
		font-size: 30px;
	}
	#main-img{
		width: 20%;
		display: block;
  		margin-left: auto;
  		margin-right: auto;
	}
</style>
</head>
<body class="light-blue">
	
	
   		 <%
         Cookie cookie = null;
         Cookie[] cookies = null;
         boolean logIn = false;
         String displayedName = "";
         // Get an array of Cookies associated with the this domain
         cookies = request.getCookies();
      
  			 if (cookies != null){
  				for (int i = 0; i < cookies.length; i++){
  					cookie = cookies[i];
  					if((cookie.getName( )).equals("loggedIn")  )
  						
  					{
  						
 						String cookieName = cookie.getValue();
						if (cookieName.contentEquals("true"))
						{
	  						logIn = true;
						}
  					}
  					if((cookie.getName( )).equals("playerName")  )
  						
  					{
  						
 						 String fakeName = cookie.getValue();
 						 
						 for (int j = 0; j < fakeName.length(); j++) {
						 	if (fakeName.charAt(j)=='=') {
						    	displayedName+=' ';
						    	}
						 	else {
						        displayedName+=fakeName.charAt(j);
						    }
						  }

  					}
  					
  				}
  				
  			}%>

   
	
	
	<script id="replace_with_navbar" src="js/nav.js"></script>
	<form onsubmit="return joinRoom(event)">
		<div class="outer-container light-blue">
			<img id="main-img" src="images/CollabArtLogo_Ver2.png">
			<div class="input-container">
				<input autocomplete="off" class = "inputs" type = "text" id = "username" placeholder = "Name" value = "<%= displayedName %>" >
				<br>
				<input autocomplete="off" class = "inputs" type = "text" id = "roomCode" placeholder = "Game Code">
				<br>
				<button type="submit" class="buttons">Play</button>
			</div>
		</div>
	</form>
		
		
	<script>
	/* Get URL search params */
	const params = new Proxy(new URLSearchParams(window.location.search), {
	  get: (searchParams, prop) => searchParams.get(prop),
	});
	
	var create = Boolean(params['create'])
	if (create) {
		document.getElementById('roomCode').value = params['room-code']
		document.getElementById('roomCode').style.display = 'none'
	}
	
	function joinRoom(e) {
		e.preventDefault()
		const username = encodeURIComponent(document.getElementById('username').value)
		const roomCode = encodeURIComponent(document.getElementById('roomCode').value)
		const path = `gameStart.jsp?room-code=` + roomCode + `&username=` + username
		window.location.href = path
		return false
	}
	</script>
</body>
</html>