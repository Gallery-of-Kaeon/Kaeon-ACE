var urlArgs = {};

window.location.href.replace(
	/[?&]+([^=&]+)=([^&]*)/gi,
	function(match, key, value) {
		urlArgs[key.toLowerCase()] = decodeURIComponent(value);
	}
);

var io = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/IO/ioBrowser.js")
var onePlus = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/ONEPlus.js");
var universalPreprocessor = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/UniversalPreprocessor.js");

require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/API/Kaeon%20ACE/Babylon/KaeonACEModules.js")(core);

require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/API/Kaeon%20ACE/Babylon/KaeonACE.js").run(
	core,
	onePlus.readONEPlus(universalPreprocessor.preprocess(io.open(urlArgs["kaeonace"]))),
	document.documentElement
);