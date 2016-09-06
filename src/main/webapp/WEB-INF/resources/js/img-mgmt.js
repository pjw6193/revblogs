/**
 * 
 */

function imageAdded(url) {
	console.log('added: ' + url);
}

function sleepFor( sleepDuration ){
    var now = new Date().getTime();
    while(new Date().getTime() < now + sleepDuration){ /* do nothing */ } 
}

$(document).ready(function(){
	console.log('starting loop.....');
	
	
});

function keepPageSynchronized() {
	var msBetweenSyncs = 500;
	setInterval(synchronizePage, msBetweenSync);
}

function synchronizePage() {
	// Do a cheap check of whether the page is out of sync
	var isOutOfSync = false;
	
	if ( isOutOfSync ) { // if out of sync...
		// Refresh the image list
		
	}
	console.log('synchronizing page');
}
