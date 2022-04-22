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
		width: 100%;
		height: 100%;
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
		margin: auto;
		padding: 3%;
		border-radius: 70px;
		box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.5);
	}
	.buttons{
		width: 300px;
		height: 90px;
		background-color: #FCEFC1;
		border: 4px solid #FFA353;
		box-sizing: border-box;
		box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.5);
		border-radius: 50px;
		cursor: pointer;
		font-size: 40px;
		font-family: 'Irish Grover', cursive;
	}
	.inputs{
		margin-bottom: 30px;
		width:420px;
		height:40px;
		border: 2px;
		padding: 5px;
		text-align: center;
		background-color: #E7FBFF;
		border-radius: 5px;
		font-family: 'Irish Grover', cursive;
		font-size: 30px;
	}
	img{
		width: 20%;
		display: block;
  		margin-left: auto;
  		margin-right: auto;
	}
	form{
    	margin:0px;
    	padding:0px;
    	width: 100%;
		height: 100%;
	}
</style>
</head>
<body>
	
	<script id="replace_with_navbar" src="js/nav.js"></script>
	<form onsubmit="return joinRoom(event)">
		<div class="outer-container light-blue">
			<img src="images/CollabArtLogo_Ver2.png">
			<div class="input-container">
				<input class = "inputs" type = "text" id = "name" name = "name" placeholder = "Name">
				<br>
				<input class = "inputs" type = "text" id = "roomCode" name = "roomCode" placeholder = "Game Code">
				<br>
				<button type="submit" class="buttons">Play</button>
			</div>
		</div>
	</form>
		
		
	<script>
	function joinRoom(e) {
		e.preventDefault()
		const name = document.getElementById('name').value
		const roomCode = document.getElementById('roomCode').value
		console.log('WHAT')
		console.log(name, roomCode)
		window.location.href = `gameStart.jsp?room-code=${roomCode}&username=${name}`
		return false
	}
	</script>
</body>
</html>