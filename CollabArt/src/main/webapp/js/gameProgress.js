import { wsURL } from './constants.js'
import { invalidRoomCode, noUsername, initSocket } from './utils.js'

/* Global variables */
var canvas
var ctx
var mouseDown = false
var brushSize = 5
var selectedColor = ''
var lastX = 0
var lastY = 0
var roomCode
var username
var pushed = false
var submitted = false;

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
		completed: gameEnd,
		timer: handleTimer,
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
	
	// Show main portion 
	document.getElementById('content').style.display = 'block'
	document.getElementById('loader').style.display = 'none'
}

function handleTimer(data) {
	document.querySelector('#timer').textContent = data['time-left']
}

function gameEnd() {
	if (!submitted) {
		submitData()
		setInterval(() => {
			if (submitted) window.location.replace(`gameEnd.jsp?room-code=${roomCode}&username=${username}`)
		}, 100)
	} else {
		window.location.replace(`gameEnd.jsp?room-code=${roomCode}&username=${username}`)
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
		// pink
		hex: '#ffc0cb',
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
	if (!pushed) {
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

}

/* Event handlers */
document.getElementById('submit-data').addEventListener('click', submitData)
	
document.getElementById('clear-btn').addEventListener('click', () => {
	ctx.clearRect(0, 0, canvas.width, canvas.height)
})

function submitData() {
	if (!submitted) {
		const params = new URLSearchParams({
			'image-string': canvas.toDataURL(),
			username,
			'room-code': roomCode,
		})
		pushed = true
		console.log(params);
		console.log(canvas.toDataURL())
		fetch('/Fragment', { method: 'POST', body: params })
			.then(res => res.json())//.then(data => console.log(data))
			//Show image below
			.then(data => {
				console.log(data)
				if (data.success) {
					console.log('YAY')
					window.socket.send(JSON.stringify({
						type: 'submitted'
					}))
					
					document.getElementById('submit-btn-container').innerHTML = 'Waiting for others...'
					submitted = true
				}
			})
	}
}

export function isSubmitted() {
	return submitted
}

/*
var onBeforeUnLoadEvent = false;
window.onunload = window.onbeforeunload= function(){
	if(!onBeforeUnLoadEvent){
  		return false
  	}
};*/