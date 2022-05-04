import { invalidRoomCode, noUsername } from './utils.js'

/**
 * 
 */
/* Global variables */
var username 
var roomCode

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
	
	const body = new URLSearchParams({
		username,
		'room-code': roomCode,
	})
 	fetch('/FinalImage', { method: 'POST', body })
	 	.then(res => res.json())
	 	.then(data => {
			//console.log('DATA: ', data)
			//const dataURL = 'data:image/png;base64,' + data['image-string']
			const dataURL = data['image-string']
			document.getElementById('final-img').src = dataURL
			
			const players = data.players
			const statement = data.statement
			
			document.getElementById('statement').innerHTML = statement
			
			const playersContainer = document.getElementById('players')
			playersContainer.innerHTML = ''
			for (let i = 0; i < players.length; ++i) {
				let c = ''
				if (players[i].username === username) c = 'cur-player'
				
				playersContainer.innerHTML += `
				<span style="display: inline-flex; align-items: center">
					<img class="playerCircle" src="images/Avatar${i}.png"/> 
					<span class="${c}">${players[i].username}</span>
				</span>
				`
			}
			
			// Show main portion 
			document.getElementById('content').style.display = 'block'
			document.getElementById('loader').style.display = 'none'
		})
} 

