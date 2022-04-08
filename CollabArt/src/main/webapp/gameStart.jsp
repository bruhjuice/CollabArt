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
<title>CollabArt</title>
	<style>
	
	/* Remove background-image to make background blue */
	.overall {
		display: flex;
		flex-direction: column;
		flex: 1;
		width: 100%;
		align-items: center;
		justify-content: flex-start;
		margin-vertical: 100px;
		
		background-image: url("images/doodles2blueBlurred2.jpg");
	}
	
	.rowDiv {
		display: flex; 
		flex-direction: row; 
		flex: 1; 
		justify-content: center
	}
	
	.colDiv {
		display: flex; 
		flex-direction: column; 
		flex: 1; 
		align-items: center; 
		justify-content: flex-start;
	}
	
	.roomCodeTitle {
		width: 100%;
		display: flex;
		justify-content: center;
		
		margin: 5px;
	
		font-size: 30pt;
	}
	
	.roomCode {
		width: 100%;
		display: flex;
		justify-content: center;
		
		margin: 10px;
		
		font-size: 40pt;
	}
	
	.bigButton {
		text-align: center;
		width: fit-content;
		
		margin: 10px 0px 40px 0px;
		
		display: flex;
		flex-direction: row;
		justify-content: center;		
		align-items: center;		
				
		
		overflow: hidden;
		border-style: solid;
		border-width: 15px;
		border-color: #FFA353;
		
		box-shadow: 5px 10px 5px gray;
	}
	
	.startSize {
		font-size: 40pt;
		padding: 20px 70px;
		border-radius: 80px;
		
	}
	
	.waitPlayerSize {
		font-size: 30pt;
		padding: 20px 100px;
		border-radius: 100px;
	}
	
	.waitOwnerSize {
		font-size: 30pt;
		padding: 20px 100px;
		border-radius: 100px;
	}
	
	.playerCircle {
		width: 120px;
		height: 120px;
		margin: 0px 20px;
		
		overflow: hidden;
		border-radius: 100px;
		border-style: solid;
		border-width: 15px;
		border-color: #ACEBFF;
		
		justify-content: center;
		align-items: center;
		
		box-shadow: 5px 10px 5px gray;
	}
	
	.playerName {
		width: 140px;
		height: 25px;
		
		font-size: 12pt;
		text-align: center;
		padding-top: 6px;
		margin: 15px 0px 0px 0px;
		
		overflow: hidden;
		border-radius: 12px;
		
		box-shadow: 5px 10px 5px gray;
	}
	
	.waitPlayerText {
		font-size: 15pt;
		margin-top: -42px;
		text-align: center;
	}
	
	.questionmark {
		display: flex;
		flex-direction: column;
		flex: 1;
		
		font-size: 60px;
		justify-content: center;
		text-align: center;
		
		margin-top: 24px;
		
	}
	
	</style>
</head>


<!-- Remove overall background image to make light-blue show up -->
<body class="light-blue overall">

	<!-- vertical -->
	<div>
		<!-- put nav bar here???? -->
		<p>navbar here</p>
	
		<!-- Room code -->
		<p class="roomCodeTitle">Room Code (share with friends):</p>
		<p class="roomCode">QBEG</p>
		
		<!-- Various buttons depending on state -->
		<div class="rowDiv">
			<p class="bigButton startSize yellow">Game Start?</p>
		</div>
		<!-- <div style="display: flex; flex-direction: row; flex: 1; justify-content: center">
			<p class="bigButton waitPlayerSize yellow">Waiting for <br> players...</p>
		</div>
		<div style="display: flex; flex-direction: row; flex: 1; justify-content: center">
			<p class="bigButton waitOwnerSize yellow">Waiting for <br> owner...</p>
		</div> -->
		
		<!-- players -->
		<div class="rowDiv">	
		
			<div class="colDiv">
				<img class="playerCircle" src="images/Avatar1.png"/> 
				<p class="playerName blue">jspjspjsp</p>
				<p class="playerName yellow">Owner</p>
			</div>
			
			<div class="colDiv">
				<img class="playerCircle" src="images/Avatar2.png"/> 
				<p class="playerName blue">SAL</p>
			</div>
			
			<div class="colDiv">
				<img class="playerCircle" src="images/Avatar3.png"/> 
				<p class="playerName blue">help</p>
			</div>
			
			<div class="colDiv">
				<div class="playerCircle pink"> <p class="questionmark">?</p> </div>
				<p class="waitPlayerText">Waiting for player</p>
			</div>
			
		</div>
	
	
	
	</div>
	<!-- end of vertical div -->
	
</body>
</html>



















