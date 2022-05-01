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

<title>GameEnd</title>
<style>
	.outer-container {
		width: 100%;
		height: 100%;
	}
	#title{
		font-size: 44px; 
		font-style: Irish Grover, cursive; 
		text-align: center; 
		line-height: 120px;
	}
	img{
		width: 800px;
		height: 600px;
	}
	.container{
		display: flex;
	}
	#artist-container{
		position: absolute;
		width: 200px;
		height: 380px;
		left: 80px;
		top: 100px;
		background-color: #DED3EB;
		border: 3px solid #A98FCB;
		border-radius: 25px;
		font-family: 'Irish Grover';
		font-size: 18px;
		padding: 0px 15px;
		box-shadow: 0px 6px 6px rgba(0, 0, 0, 0.5);
	}
	#drawing-container {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.tape.top-left {
		left: 0;
		transform: rotate(-40deg) scale(0.4, 0.5) translate(-70%, -200%);
	}
	.tape.top-right {
		right: 0;
		transform: rotate(40deg) scale(0.4, 0.5) translate(70%, -200%);
	}
	.tape.bottom-left {
		left: 0;
		bottom: 0;
		transform: rotate(220deg) scale(0.4, 0.5) translate(70%, -200%);
	}
	.tape.bottom-right {
		right: 0;
		bottom: 0;
		transform: rotate(-220deg) scale(0.4, 0.5) translate(-70%, -200%);
	}
	#button-container{
		display: flex;
		justify-content: center;
	}
	button.margin-right{
		margin-right:20px;
	}
	
	.playerCircle {
		width: 50px;
		height: 50px;
		margin: 0px 20px 10px 0px;
		
		overflow: hidden;
		border-radius: 100px;
		border-style: solid;
		border-width: 4px;
		border-color: #ACEBFF;
		
		justify-content: center;
		align-items: center;
		
		box-shadow: 5px 10px 5px gray;
	}
	.cur-player {
		color: #76BF7D;
	}
</style>
</head>
<body>
	<script id="replace_with_navbar" src="js/nav.js"></script>

	<div class="outer-container light-blue">
		<div id="title">
			"<span id="statement">Masterpiece</span>"
		</div>
		<div id="artist-container">
			<p style="font-size: 25px;">Artists:</p>
			<span id="players">
				<span style="display: inline-flex; align-items: center">
					<img class="playerCircle" src="images/Avatar0.png"/> 
					<span class="cur-player">Max</span>
				</span>
				<span style="display: inline-flex; align-items: center">
					<img class="playerCircle" src="images/Avatar1.png"/> 
					Karina
				</span>
				<span style="display: inline-flex; align-items: center">
					<img class="playerCircle" src="images/Avatar2.png"/> 
					Ed
				</span>
				<span style="display: inline-flex; align-items: center">
					<img class="playerCircle" src="images/Avatar3.png"/> 
					MiruBiru
				</span>
			</span>
		</div>
		<div id="drawing-container">
			<div style="position: relative;">
				<div class="tape top-left"></div>
				<div class="tape top-right"></div>
				<div class="tape bottom-left"></div>
				<div class="tape bottom-right"></div>
				<img id="final-img" src="images/spongebob.jpg">
				</img>
			</div>			
		</div>
		<br>
		<div id="button-container">
			<!-- <button type="submit" onclick="window.location='gameStart.jsp';" class="buttons-red margin-right">Play Again?</button>-->
			<button type="submit" onclick="window.location='index.jsp';" class="buttons-red">Home</button>
		</div>
	</div>
	<script type="module" src="js/gameEnd.js"></script>
</body>
</html>