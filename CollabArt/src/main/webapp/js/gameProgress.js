import { wsURL } from './constants.js'
import { invalidRoomCode, noUsername, initSocket } from './utils.js'

/* Global variables */
var canvas
var ctx
var mouseDown = false
var brushSize = 10
var selectedColor = ''
var lastX = 0
var lastY = 0
var roomCode
var username

/* Get URL search params */
const params = new Proxy(new URLSearchParams(window.location.search), {
  get: (searchParams, prop) => searchParams.get(prop),
});

/* Check if parameters are valid and init socket */
if (!params['room-code']) {
	invalidRoomCode()
} else if (!params['username']) {
	noUsername()
} else {
	init()
}

function init() {
	roomCode = params['room-code']
	username = params['username']
	
	initSocket(wsURL, roomCode, username, {
		'prompt': setPrompt,
	})
}

function setPrompt(data) {
	const { prompt, bounds } = data
	const width = bounds.right - bounds.left
	const height = bounds.bottom - bounds.top
	
	document.getElementById('prompt-text').innerHTML = prompt
	if (width/height > 4/3) {
		canvas.width = width * 800/width
		canvas.height = height * 800/width		
	} else {
		canvas.width = width * 600/height
		canvas.height = height * 600/height		
	}
}

/* Colors */
const colorsContainer = document.getElementById('colors-container')
const brushSizeSlider = document.getElementById('brush-size-slider')
const colors = [
	{
		// black
		hex: '#000',
		dark: true,
	},
	{
		//white
		hex: '#fff',
		dark: false,
	},
	{
		// red
		hex: '#C74646',
		dark: false,
	},
	{
		// orange
		hex: '#FFA500',
		dark: false,
	},
	{
		// yellow
		hex: '#F2CD4A',
		dark: false,
	},
	{
		// green
		hex: '#57CB44',
		dark: false,
	},
	
	{
		// light blue
		hex: '#61C0DE',
		dark: false,
	},
	{
		// purple 
		hex: '#8a2be2',
		dark: false,
	},


	{
			// brown
		hex: '#964B00',
		dark: false,
	},
	{
			// grey
		hex: '#808080',
		dark: false,
	},
]

function initColors() {
	for (let i = 0; i < colors.length; ++i) {
		const { hex, dark } = colors[i]
		let classString = 'color-circle'
		if (dark) classString += ' dark' // adds the 'dark' class if color is a dark color
		if (i === 0) classString += ' selected' // select first color
		
		const colorSwatch = document.createElement('div')
		colorSwatch.className = classString
		colorSwatch.style = `background-color: ${hex}`
		colorSwatch.addEventListener('click', e => {
			select(i)
		})
		
		colorsContainer.appendChild(colorSwatch)
	}
	selectedColor = colors[0].hex
}

function select(index) {
	selectedColor = colors[index].hex
	for (let i = 0; i < colorsContainer.children.length; ++i) {
		if (index === i) {
			colorsContainer.children[i].classList.add('selected')
		} else {
			colorsContainer.children[i].classList.remove('selected')
		}
	}
}

brushSizeSlider.addEventListener('change', e => {
	brushSize = brushSizeSlider.value
})

initColors()


/* Canvas */ 
canvas = document.getElementById('drawing-canvas')
ctx = canvas.getContext('2d')

/* Canvas events */
canvas.addEventListener('mousedown', (e) => {
	const { offsetX: x, offsetY: y } = e
	mouseDown = true
	
	lastX = x
	lastY = y
	
	paint(x, y, selectedColor)
	
	// USE CAPTURE to be able to capture event when press on the tape things too
}, { capture: true })

canvas.addEventListener('mouseup', (e) => {
	mouseDown = false
})

canvas.addEventListener('mouseleave', e => {
	mouseDown = false
})

canvas.addEventListener('mousemove', (e) => {
	if (mouseDown) {
		const { offsetX: x, offsetY: y } = e
		paint(x, y, selectedColor)
		
		lastX = x
		lastY = y
	}
})

function paint(x, y, color) {
	/* Paints the specified coordinate on the canvas with the specified color */
	ctx.fillStyle = color
	ctx.strokeStyle = color
	ctx.beginPath()
	ctx.arc(x, y, brushSize, 0, 2*Math.PI)
	ctx.fill()
	ctx.closePath()
	
	if (Math.abs(x - lastX) > 2 || Math.abs(y - lastY) > 2) {
		ctx.beginPath()
		ctx.lineWidth = 2*brushSize
		ctx.moveTo(lastX, lastY)
		ctx.lineTo(x, y)
		ctx.stroke()
		ctx.closePath()
	}
}

/* Event handlers */
document.getElementById('submit-data').addEventListener('click', () => {
		const params = new URLSearchParams({
			'image-string': canvas.toDataURL(),
			username,
			'room-code': roomCode,
		})
		console.log(params);
		console.log(canvas.toDataURL())
		fetch('/CollabArt/Fragment', { method: 'POST', body: params })
			.then(res => res.text())//.then(data => console.log(data))
			//Show image below
			.then(data => document.querySelector("#completedimage").src="data:image/png;base64,"+data)
			//Note: image works! even adding the image! However, even tho image is right dimension, space to the right is all white now...
			
			//Later on, need to send do stuff to get to test.jsp.
			//Option a:
			//Add to post variable, send to test.jsp (and go there? How to go there instead of just geting data from there?)
			/*
			.then(var xhr = new XMLHttpRequest();
				xhr.open("POST", "test.jsp", true);
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.send(JSON.stringify({
					  completedString: res.text()
				}));)
		   */
				
			//Option b: Add attribute then send window to new page
			//.then(window.location.href="test.jsp");
	})