const serverURL = '/CollabArt'

fetch('navBar.jsp')
.then(res => res.text())
.then(text => {
    let oldelem = document.querySelector("script#replace_with_navbar");
    let newelem = document.createElement("div");
    newelem.setAttribute("class","sticky");
    newelem.innerHTML = text;
    oldelem.parentNode.replaceChild(newelem,oldelem);
    
    init()
})

function init() {	
	document.getElementById('create-room').addEventListener('click', createRoom)
	document.getElementById('join-room').addEventListener('click', e => window.location.replace('playGame.jsp'))
}

function createRoom() {
	fetch(`${serverURL}/create-room`, { method: 'POST' }).then(res => res.json()).then(({ roomCode }) => {
		const username = makeid(5)
		window.location.replace(`gameStart.jsp?room-code=${roomCode}&username=${username}`)
	})
}

function makeid(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength))
   }
   return result;
}