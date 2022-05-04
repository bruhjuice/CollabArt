<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import = "util.Likes" %>
<%@ page import = "util.Utility" %>

<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.DriverManager" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "java.sql.SQLException" %>
<%@ page import = "java.io.IOException" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script src="https://kit.fontawesome.com/1a5a91d3bb.js"
	crossorigin="anonymous"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Irish+Grover&display=swap"
	rel="stylesheet">

<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/home.css">
<link rel="stylesheet" href="css/navbar.css">

<script>
const LIKED = 1;
const DISLIKED = -1;
const UNLIKED = 0;
likeMap = new Map();

function Convert(e) {
	let idStr = e.parentNode.parentNode.id.substring(6);
	return parseInt(idStr);
}

</script>

<title> Collabart | Home</title>
</head>
<body>
	<script id="replace_with_navbar" src="js/nav.js"></script>

	<!-- Thoughts: doing likes/dislikes will be hard: whenever they click on it, need to send update to database
   In addition, need to keep track of each user's likes, dislikes, to make sure they can't like multiple times? 
   Have to do AJAX to see likes go up/down? increment/display locally then update to true value on reload? Or get update when like?-->
	<%
	Cookie cookie = null;
	Cookie[] cookies = null;
	boolean logIn = false;
	// Get an array of Cookies associated with the this domain
	cookies = request.getCookies();
	String username = "default";
	if (cookies != null)
	{
		for (int i = 0; i < cookies.length; i++)
		{
	 		cookie = cookies[i];
			if ((cookie.getName()).equals("loggedIn"))
	 		{
	   			String cookieName = cookie.getValue();
	   			String name = "";
	   			//change back to real name
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
	   		} else if (cookie.getName().equals("playerName")) {
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
	}
	%>
	<%
	if (!logIn)
	{

	   out.println("<br> <br> <h1> Please login or create an account to view the gallery</h1> <div id = 'blur'> ");
	}
	%>

	<br>
   <h1>Collabart Gallery</h1>


	<br>
	<!-- Text, image, and like count will all be grabbed from database. Can also add unique id to each galart element -->

	<%
	String sql = "SELECT * FROM drawings ORDER BY dateCreated DESC, likes DESC";

	try
	{
	   Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	} catch (Exception e)
	{
	   e.printStackTrace();
	}
	try (Connection conn = Utility.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);)
	{
	   ResultSet rs = ps.executeQuery();
	   if (rs != null)
	   {
	      while (rs.next())
	      {
	         int likes = rs.getInt("likes");
	         String image = "data:image/png;base64,";
	         image+=rs.getString("image");

	         out.println("<div class='galart' id='galart" + rs.getInt("id") + "'>");
	             out.println("<div class='galart-top blue top-rounded'>");
	                 out.println("<p class='prompttext'>"+rs.getString("prompt")+"</p>");
	                 out.println("<p class='smallertext'>Created "+rs.getDate("dateCreated")+" by: "+rs.getString("createdUsers")+"</p>");
	             out.println("</div>");
	             out.println("<div class='galart-mid'>");
	                 out.println("<img class=galart-img src='" + image + "'>");
	             out.println("</div>");
	             out.println("<div class='galart-bottom pink bottom-rounded'>");
	                 out.println("<i class='fa-solid fa-thumbs-up'></i> <span>&emsp;" + likes
	                 + " Likes&emsp;</span> <i class='fa-solid fa-thumbs-down'></i>");
	             out.println("</div>");
	         out.println("</div>");
	      }
	   }
	} catch (SQLException ex)
	{
	   System.out.println("SQLException: " + ex.getMessage());
	}
	%>

	<%-- <div class="galart" id="galart1">
		<div class="galart-top blue top-rounded">
			<p>Draw... <span>a cat playing basketball</span></p>
		</div>
		<!-- Images should be a bit wider, maybe something like 3:2? -->
		<div class="galart-mid">
			<img class=galart-img src="images/cat_basketball.png">
		</div>
		<div class="galart-bottom pink bottom-rounded">
			<!-- Also put id on each art's thumbs up and thumbs down? -->
			<!-- If logged in, show like and dislike button. If not, don't add. -->
			<!-- Make sure when you loop this, update id galart below -->
			<i class="fa-solid fa-thumbs-up"></i> <span>&emsp;<%=Likes.GetLike(GetId("galart1"))%> Likes&emsp;</span> <i class="fa-solid fa-thumbs-down"></i>
		</div>
	</div>
	<div class="galart" id="galart2">
		<div class="galart-top blue top-rounded">
			<p>Draw... <span>a cat playing basketball</span></p>
		</div>
		<div class="galart-mid">
			<img class=galart-img src="images/cat_basketball.png">
		</div>
		<div class="galart-bottom pink bottom-rounded">
			<!-- Also put id on each art's thumbs up and thumbs down? -->
			<!-- If logged in, show like and dislike button. If not, don't add. -->
			<!-- Make sure when you loop this, update id galart below -->
			<i class="fa-solid fa-thumbs-up"></i> <span>&emsp;<%=Likes.GetLike(GetId("galart2"))%> Likes&emsp;</span> <i class="fa-solid fa-thumbs-down"></i>
		</div>
	</div>
 --%>
	
	<%if (!logIn)
  	{
  		out.println("</div");
  	}
  	%>
	

	<!-- Script to read thumbs up and thumbs down: -->
	<script>
	let thumbsup = Array.from(document.getElementsByClassName("fa-thumbs-up"));
	let thumbsdown = Array.from(document.getElementsByClassName("fa-thumbs-down"));
	console.log(thumbsup);
	console.log(thumbsdown);
	
	for (let i = 0; i < thumbsup.length; i++) {
		likeMap.set(Convert(thumbsup[i]), UNLIKED);
	}
	
	console.log(likeMap);
	
	for (let i = 0; i < thumbsup.length; i++) { 
		let id = Convert(thumbsup[i]);
		likeMap.set(id, UNLIKED);
		
		let para = new URLSearchParams({
			"command": "exist",
			"picId": Convert(thumbsup[i]),
			"username": "<%=username%>"
		});
		
		fetch('/LikeDispatcher', {method: 'POST', body: para})
			.then(response => response.text())
			.then(data => {likeMap.set(id, parseInt(data));
				switch (likeMap.get(id)) {
				case LIKED:
					blueLike(thumbsup[i]);
					break;
				case DISLIKED:
					blueDislike(thumbsdown[i]);
					break;
				}
			})
	}
	
	function handletup(event) {
		id = Convert(event.target);
		switch(likeMap.get(id)) {
		case LIKED:
			params = new URLSearchParams({
				"command": "remove",
				"picId": id,
				"username": "<%=username%>"
			})
			//Update database based on parent div id
			fetch('/LikeDispatcher', {method: 'POST', body: params})
				.then(response => response.text())
				.then(data => event.target.nextElementSibling.innerHTML="\&emsp;"+data+" Likes\&emsp;");
			//Update
			removeLike(event.target);
			likeMap.set(id, UNLIKED);
			
			break;
		default:
			params = new URLSearchParams({
				"command": "like",
				"picId": id,
				"username": "<%=username%>"
			})
			//Update database based on parent div id.
			fetch('/LikeDispatcher', {method: 'POST', body: params})
				.then(response => response.text())
				.then(data => event.target.nextElementSibling.innerHTML="\&emsp;"+data+" Likes\&emsp;");
	     	//Update
	     	blueLike(event.target);
	     	likeMap.set(id, LIKED);
		}
    };
   
	function handletdown(event) {
		id = Convert(event.target);
		switch(likeMap.get(id)) {
		case DISLIKED:
			params = new URLSearchParams({
				"command": "remove",
				"picId": id,
				"username": "<%=username%>"
			})
			//Update database based on parent div id
			fetch('/LikeDispatcher', {method: 'POST', body: params})
				.then(response => response.text())
				.then(data => event.target.previousElementSibling.innerHTML="\&emsp;"+data+" Likes\&emsp;");
			//Update
			removeDislike(event.target);
			likeMap.set(id, UNLIKED);
			break;
		default:
			params = new URLSearchParams({
				"command": "dislike",
				"picId": id,
				"username": "<%=username%>"
			})
			//Update database based on parent div id.
			fetch('/LikeDispatcher', {method: 'POST', body: params})
				.then(response => response.text())
				.then(data => event.target.previousElementSibling.innerHTML="\&emsp;"+data+" Likes\&emsp;");
	     	//Update
	     	blueDislike(event.target);
			likeMap.set(id, DISLIKED);
		}
    };
    
   thumbsup.map( tup => {
	   if (<%=logIn%>) {
		   tup.addEventListener('click', handletup);
	   }
   });
   
   thumbsdown.map( tdown => {
	   if (<%=logIn%>) {
		   tdown.addEventListener('click', handletdown);
	   }
	});
   
   function blueLike(element) {
		element.style.color = "blue";
		element.style.opacity = 1.0;
		element.nextElementSibling.nextElementSibling.style.opacity = 0.5;
		element.nextElementSibling.nextElementSibling.style.color = "black";
		//event.target.removeEventListener('click', handletup);
		//event.target.nextElementSibling.nextElementSibling.removeEventListener('click', handletdown);
   }
      
   function blueDislike(element) {
	   element.style.color = "blue";
	   element.style.opacity = 1.0;
       element.previousElementSibling.previousElementSibling.style.opacity = 0.5;
       element.previousElementSibling.previousElementSibling.style.color = "black";
       //event.target.removeEventListener('click', handletdown);
       //event.target.previousElementSibling.previousElementSibling.removeEventListener();
   }
   
   function removeLike(element) {
	   element.style.color = "black";
	   element.nextElementSibling.nextElementSibling.style.opacity = 1.0;
   }
   
   function removeDislike(element) {
	   element.style.color = "black";
	   element.previousElementSibling.previousElementSibling.style.opacity = 1.0;
   }
   </script>
   
   <%!
   //Get pic id from galart1
   public int GetId(String text) {
	   if (text == null) {
		   return 0;
	   }
	   if (text.length() > 6) {
		   String t = text.substring(6);
		   try {
			   return Integer.parseInt(t);
		   } catch (NumberFormatException e) {
			   System.out.println ("ParseIntException: " + e.getMessage());
			   return 0;
		   }
	   } else {
		   return 0;
	   }
   }
   %>
</body>
</html>