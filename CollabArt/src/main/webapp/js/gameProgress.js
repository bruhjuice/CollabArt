/**
 * 
 */

/* Global variables */
var mouseDown = false
var brushSize = 10
var selectedColor = ''
var lastX = 0
var lastY = 0

/* Colors */
const colorsContainer = document.getElementById('colors-container')
const brushSizeSlider = document.getElementById('brush-size-slider')
const colors = [
	{
		hex: '#F2CD4A',
		dark: false,
	},
	{
		hex: '#61C0DE',
		dark: false,
	},
	{
		hex: '#57CB44',
		dark: false,
	},
	{
		hex: '#C74646',
		dark: false,
	},
	{
		hex: '#000',
		dark: true,
	},
	{
		hex: '#fff',
		dark: false,
	},
]

function initColors() {
	for (let i = 0; i < colors.length; ++i) {
		const { hex, dark } = colors[i]
		let classString = 'color-circle'
		if (dark) classString += ' dark' // adds the 'dark' class if color is a dark color
		if (i === 0) classString += ' selected' // select first color
		colorsContainer.innerHTML += `
			<div class="${classString}" style="background-color: ${hex}" onclick="select(${i})"></div>
		`
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
const canvas = document.getElementById('drawing-canvas')
const ctx = canvas.getContext('2d')
ctx.fillStyle = 'white'
ctx.fillRect(0, 0, canvas.width, canvas.height)

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
