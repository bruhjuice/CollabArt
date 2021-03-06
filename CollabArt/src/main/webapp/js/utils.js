/* Error functions */
export const invalidRoomCode = (socket) => {
	console.log('room code is invalid!')
	alert('Room code is invalid!')
	history.back()
	if (socket) socket.close()
}

export const noUsername = (socket) => {
	console.log('no username!!')
	alert('Please enter a username!')
	history.back()
	if (socket) socket.close()
}

export const usernameTaken = (socket) => {
	console.log('username already taken!')
	alert('That username has already been taken!')
	history.back()
	if (socket) socket.close()
}

export const playerLimitReached = (socket) => {
	console.log('player limit reached!')
	alert('The player limit has been reached!!')
	history.back()
	if (socket) socket.close()
}

export const initSocket = (wsURL, roomCode, username, functions) => {
	/** initializes the websocket and sets up the functions to run based on message type
	 * 
	 * functions is of the form { 'message-type': function(data) } 
	 **/
	 
	var pingInterval
	
	window.socket = new WebSocket(`${wsURL}/room/${roomCode}`)
	window.socket.addEventListener('open', e => {
		console.log('CONNECTED!!')
		socket.send(JSON.stringify(
			{ type: 'player-join', username: username }
		))
		
		pingInterval = setInterval(() => {
			socket.send(JSON.stringify(
				{ type: 'PING' }
			))
		}, 30000)
	})
	
	window.socket.addEventListener('message', e => {
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
					invalidRoomCode(window.socket)
					break
				case "username-taken":
					usernameTaken(window.socket)
					break
				case "player-limit-reached":
					playerLimitReached(window.socket)
					break
			}
			return
		} else {
			console.log(data)
			functions[data.type](data)
		}
	})
	
	window.socket.addEventListener('close', e => {
		console.log('CLOSED!!')
		clearInterval(pingInterval)
	})
}