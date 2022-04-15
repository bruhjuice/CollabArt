import { wsURL } from './constants.js'

/* Variables */
var socket
var players = []
var roomCode
var username

/* Get URL search params */
const params = new Proxy(new URLSearchParams(window.location.search), {
  get: (searchParams, prop) => searchParams.get(prop),
});

(function() {
	/* Check if parameters are valid and init socket */
	if (!params['room-code']) {
		invalidRoomCode()
	} else if (!params['username']) {
		noUsername()
	} else {
		init()
	}
})()

/* Error functions */
function invalidRoomCode() {
	console.log('room code is invalid!')
	if (socket) socket.close()
}

function noUsername() {
	console.log('no username!!')
}

function usernameTaken() {
	console.log('username already taken!')
	if (socket) socket.close()
}

function init() {
	roomCode = params['room-code']
	username = params['username']
	
	document.getElementById('roomCodeDisplay').innerHTML = roomCode
	
	initSocket()
}

// Init sockets
function initSocket() {
	socket = new WebSocket(`${wsURL}/room/${roomCode}`)
	socket.addEventListener('open', e => {
		console.log('CONNECTED!!')
		socket.send(JSON.stringify(
			{ type: 'player-join', username: username }
		))
	})
	
	socket.addEventListener('message', e => {
		//console.log(e.data)
		let data 
		try {
			data = JSON.parse(e.data)
		} catch (e) {
			console.error('ERROR PARSING JSON')
			return 
		}
		
		if (data.error) {
			switch(data.error) {
				case "room-not-found":
					invalidRoomCode()
					break
				case "username-taken":
					usernameTaken()
					break
			}
			return
		} else {
			console.log(data)
			switch (data.type) {
				case "update-players":
					updatePlayers(data.players)
					break;
			}
		}
	})
}

function updatePlayers(p) {
	players = p
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
