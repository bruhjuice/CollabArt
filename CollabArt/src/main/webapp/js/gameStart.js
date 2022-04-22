import { wsURL } from './constants.js'
import { invalidRoomCode, noUsername, initSocket } from './utils.js'


/* Variables */
var players = []
var roomCode
var username

/* HTML references */
const startBtn = document.getElementById('start-btn')
const waitPlayers = document.getElementById('wait-players')
const waitOwner = document.getElementById('wait-owner')

/* Get URL search params */
const params = new Proxy(new URLSearchParams(window.location.search), {
  get: (searchParams, prop) => searchParams.get(prop),
});

/* Check if parameters are valid and init socket */
if (!params['room-code']) {
	invalidRoomCode(socket)
} else if (!params['username']) {
	noUsername(socket)
} else {
	init()
}

function init() {
	roomCode = params['room-code']
	username = params['username']
	
	document.getElementById('roomCodeDisplay').innerHTML = roomCode
	
	initSocket(wsURL, roomCode, username, {
		'update-players': updatePlayers,
		started: startGame,
	})
}

/* Event Listeners */
startBtn.addEventListener('click', e => {
	console.log('START BTN CLICKED')
	window.socket.send(JSON.stringify(
		{ type: 'start' }
	))
})

/* Utility Functions */
function updatePlayers(data) {
	players = data.players
	const playersDiv = document.getElementById('players')
	playersDiv.innerHTML = ''
	
	// Update the html to reflect the new players
	for (let i = 0; i < 4; ++i) {
		const col = document.createElement('div')
		col.classList.add('colDiv')
		
		if (players.length >= i+1) {
			// Player exists
			col.innerHTML += playerHTML(players[i].username, i, players[i].username === username)
			if (i === 0) {
				col.innerHTML += ownerHTML
			}
		} else {
			// No player exists
			col.innerHTML += noPlayerHTML
		}
		playersDiv.appendChild(col)
	}
	
	//Change start button
	if (players.length < 4) {
		startBtn.style.display = 'none'
		waitPlayers.style.display = 'block'
		waitOwner.style.display = 'none'
	} else {
		if (players[0].username === username) {
			// is owner
			startBtn.style.display = 'block'
			waitPlayers.style.display = 'none'
			waitOwner.style.display = 'none'
		} else {
			startBtn.style.display = 'none'
			waitPlayers.style.display = 'none'
			waitOwner.style.display = 'block'
		}
	}
}

function startGame() {
	window.location.replace(`gameProgress.jsp?room-code=${roomCode}&username=${username}`)
}

/* HTML strings */
const noPlayerHTML = `
	<div class="playerCircle pink"> <p class="questionmark">?</p> </div>
	<p class="waitPlayerText">Waiting for player</p>
`

const playerHTML = (username, i, isCurrentUser) => (`
	<img class="playerCircle" src="images/Avatar${i}.png"/> 
	<p class="playerName ${isCurrentUser ? 'light-green' : 'blue'}">${username}</p>
`)

const ownerHTML = `<p class="playerName yellow">Owner</p>`
