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
<title>CollabArt</title>
<style>
	.container {
		display: flex;
		align-items: center;
		justify-content: center;
		column-gap: 50px;
		flex: 1;
	}
	
	#drawing-container {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	.prompt-container {
		min-width: 80%;
		display: flex;
		justify-content: center;
		overflow: hidden;
		margin-bottom: 16px;
	}
	
	.prompt-container > div {
		padding: 10px 20px;
	}
	
	#drawing-canvas {
		
	}
	
	#canvas-background {
		background: radial-gradient(#fff 50%, #eee);
	}
	
	#colors-container {
		
	}
	
	.color-circle {
		border-radius: 50%;
		border-color: black;
		border-width: 2px;
		border-style: solid;
		width: 50px;
		height: 50px;
		cursor: pointer;
	}
	
	.color-circle.selected {
		background: radial-gradient(black 0, black 25%, transparent 25.1%);
	}
	
	.color-circle.selected.dark {
		background: radial-gradient(white 0, white 25%, transparent 25.1%);
	}
	
	.color-circle:nth-child(even) {
		margin-left: 100px;
	}
	
	#timer {
		background-color: black;
		color: white;
		width: 100px;
		height: 100px;
		text-align: center;
		line-height: 100px;
		border-radius: 50%;
		font-size: 40px;
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
	
	#completeddiv{
	  width:75%;
	  margin-left: auto;
	  margin-right: auto;
	}
	#completedimage{
	  width:100%;
	}
</style>
</head>
<body class="light-blue" style="display: flex; flex-direction: column;">
	<script id="replace_with_navbar" src="js/nav.js"></script>

	<div class="container">
		<div>
			<div id="colors-container">
			</div>
			<div style="margin-top: 36px;">
				Brush Size:<br/>
				<input id="brush-size-slider" type="range" min="5" max="50" value="5" step="5">
			</div>
		</div>
		<div id="drawing-container">
			<div class="prompt-container">
				<div class="left-rounded white">
					Draw...
				</div>
				<div id="prompt-text" class="right-rounded blue" style="flex: 1;">
					your mental health
				</div>
			</div>
			
			<div id="canvas-background" style="position: relative;">
				<div class="tape top-left"></div>
				<div class="tape top-right"></div>
				<div class="tape bottom-left"></div>
				<div class="tape bottom-right"></div>
				<canvas id="drawing-canvas" width="800" height="600">
				</canvas>
			</div>
			
			<button id="submit-data">Submit data!</button>
		</div>
		<div id="right-container">
			<div id="timer">
				60
			</div>
		</div>
	</div>
	<div id="completeddiv">
	  <img id="completedimage"/>
   </div>
	

	<script type="module" src="js/gameProgress.js"></script>
	<script>
		function startTimer(seconds, timeRemaining) {
		    var timer = seconds;
		    
		    // every 1 second = 1000 milliseconds
		    setInterval(function () {
		    	// change text
		        timeRemaining.textContent = timer;
		        timer--;
			    // when timer reaches end, redirect
			    if (timer == 0) {
			    	// "click" to submit image data
			    	document.getElementById('submit-data').click();
			    	
			    	// TODO:
			    		// add things to check that all players have submitted the data before redirecting
			    		// otherwise, freeze image here
			    		// and let other players know through minimap?
			    				// or are we scrapping minimap?
			    	
			    	
					// then redirect
			    	//window.location.replace('gameEnd.jsp');
			    }
		    }, 1000);
		    
		}
		
		// start timer when page loads
		window.onload = function () {
			// 1 min to draw
		    var seconds = 60;
			
			// change display of "timer" tag
		    var timeRemaining = document.querySelector('#timer');
			
		    startTimer(seconds, timeRemaining);
		};
	</script>
</body>
</html>