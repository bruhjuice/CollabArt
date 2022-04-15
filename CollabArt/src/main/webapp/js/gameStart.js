import { wsURL } from './constants.js'

// Variables
var socket

// Get URL search params
const params = new Proxy(new URLSearchParams(window.location.search), {
  get: (searchParams, prop) => searchParams.get(prop),
});

// Check if parameters are valid
if (!params['room-code']) {
	invalidRoomCode()
} else if (!params['test-user'] /* TODO: add check for username cookie! */) {
	notLoggedIn()
} else {
	initSocket()
}

function invalidRoomCode() {
	console.log('room code is invalid!')
	if (socket) socket.close()
}

function notLoggedIn() {
	console.log('You are not logged in!!')
}

function initSocket() {
	socket = new WebSocket(`${wsURL}/room/${params['room-code']}`)
	socket.addEventListener('open', e => {
		console.log('CONNECTED!!')
		socket.send(JSON.stringify(
			{ type: 'player-join', id: params['test-user'] }
		))
	})
	
	socket.addEventListener('message', e => {
		console.log(e.data)
		let data 
		try {
			data = JSON.parse(e.data)
		} catch (e) {
			console.error('ERROR PARSING JSON')
			return 
		}
		
		if (data.error) {
			if (data.error === 'room-not-found') invalidRoomCode()
			return
		} else {
			console.log(data)
		}
	})
}
