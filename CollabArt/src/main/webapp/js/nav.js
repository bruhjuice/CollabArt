fetch('navBar.jsp')
.then(res => res.text())
.then(text => {
    let oldelem = document.querySelector("script#replace_with_navbar");
    let newelem = document.createElement("div");
    newelem.innerHTML = text;
    oldelem.parentNode.replaceChild(newelem,oldelem);
    
    init()
})

function init() {	
	document.getElementById('create-room').addEventListener('click', createRoom)
}

function createRoom() {
	fetch('/CollabArt/create-room', { method: 'POST' }).then(res => res.json()).then(console.log)
}