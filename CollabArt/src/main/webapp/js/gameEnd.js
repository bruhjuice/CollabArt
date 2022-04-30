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
 	fetch('/CollabArt/FinalImage', { method: 'POST', body })
	 	.then(res => res.json())
	 	.then(data => {
			//console.log('DATA: ', data)
			const dataURL = 'data:image/png;base64,' + data['image-string']
			console.log(dataURL)
			document.getElementById('final-img').src = dataURL
		})
} 

