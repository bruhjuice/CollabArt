/* Error functions */
export const invalidRoomCode = (socket) => {
	console.log('room code is invalid!')
	if (socket) socket.close()
}

export const noUsername = (socket) => {
	console.log('no username!!')
	if (socket) socket.close()
}

export const usernameTaken = (socket) => {
	console.log('username already taken!')
	if (socket) socket.close()
}

export const initSocket = (wsURL, roomCode, username, functions) => {
	/** initializes the websocket and sets up the functions to run based on message type
	 * 
	 * functions is of the form { 'message-type': function(data) } 
	 **/
	window.socket = new WebSocket(`${wsURL}/room/${roomCode}`)
	window.socket.addEventListener('open', e => {
		console.log('CONNECTED!!')
		socket.send(JSON.stringify(
			{ type: 'player-join', username: username }
		))
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
			}
			return
		} else {
			console.log(data)
			functions[data.type](data)
		}
	})
}