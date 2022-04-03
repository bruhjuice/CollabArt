/**
 * 
 */

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
	const colorsContainer = document.getElementById('colors-container')
	for (const { hex } of colors) {
		colorsContainer.innerHTML += `
			<div class="color-circle" style="background-color: ${hex}"></div>
		`
	}
}

initColors()


/* Canvas */ 
const canvas = document.getElementById('drawing-canvas')
const ctx = canvas.getContext('2d')

ctx.fillStyle = 'white'
ctx.fillRect(0, 0, canvas.width, canvas.height)
ctx.fillStyle = 'green'
ctx.fillRect(10, 10, 100, 150)
