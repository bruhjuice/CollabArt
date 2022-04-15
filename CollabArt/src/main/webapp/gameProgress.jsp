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
	.outer-container {
		width: 100%;
		height: 100%;
	}
	
	.container {
		display: flex;
		align-items: center;
		height: 100%;
		box-sizing:border-box;
		padding: 50px;
	}
	
	.container > div {
		padding: 50px;
	}
	
	#drawing-container {
		flex: 1;
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
</style>
</head>
<body>
	<div class="outer-container light-blue">
		<div class="container">
			<div>
				<div id="colors-container">
				</div>
				<div style="margin-top: 36px;">
					Brush Size:
					<input id="brush-size-slider" type="range" min="10" max="50" value="10" step="10">
				</div>
			</div>
			<div id="drawing-container">
				<div class="prompt-container">
					<div class="left-rounded white">
						Draw...
					</div>
					<div class="right-rounded blue" style="flex: 1;">
						your mental health
					</div>
				</div>
				
				<div style="position: relative;">
					<div class="tape top-left"></div>
					<div class="tape top-right"></div>
					<div class="tape bottom-left"></div>
					<div class="tape bottom-right"></div>
					<canvas id="drawing-canvas" width="800" height="600">
					</canvas>
					
					<button id="submit-data">Submit data!</button>
				</div>
			</div>
			<div id="right-container">
				<div id="timer">
					0:53
				</div>
			</div>
		</div>
	</div>
	<img id="completedImage"/>

	<script src="js/gameProgress.js"></script>
	<script>
		document.getElementById('submit-data').addEventListener('click', () => {
			const params = new URLSearchParams({
				'image-string': canvas.toDataURL()
			})
			console.log(canvas.toDataURL())
			fetch('/CollabArt/Fragment', { method: 'POST', body: params })
				.then(res => res.text())//.then(data => console.log(data))
				.then(data => document.querySelector("#completedImage").src="data:image/png;base64,"+data)
				
				//Add to post variable, send to test.jsp (and go there? How to go there instead of just geting data from there?)
				/*
				.then(var xhr = new XMLHttpRequest();
					xhr.open("POST", "test.jsp", true);
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.send(JSON.stringify({
						  completedString: res.text()
					}));)
					*/
					
					
					//Add attribute then send window to new page
				//.then(window.location.href="test.jsp");
		})
	</script>
</body>
</html>