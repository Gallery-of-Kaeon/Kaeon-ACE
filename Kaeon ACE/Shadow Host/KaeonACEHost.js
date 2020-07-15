var urlArgs = {};

window.location.href.replace(
	/[?&]+([^=&]+)=([^&]*)/gi,
	function(match, key, value) {
		urlArgs[key.toLowerCase()] = decodeURIComponent(value);
	}
);

var io = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/IO/ioBrowser.js")
var onePlus = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/ONEPlus.js");

require("https://raw.githubusercontent.com/Kaeon-ACE/GhostHost-XP/master/GhostHost%20XP/source/game/KaeonACE.js").run(
	onePlus.readONEPlus(io.open(urlArgs["kaeonace"])),
	require("https://raw.githubusercontent.com/Kaeon-ACE/GhostHost-XP/master/GhostHost%20XP/source/game/KaeonACEModules.js")
);