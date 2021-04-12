var moduleDependencies = {
	io: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/Data/io.js",
	kaeonACE: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Core/KaeonACE.js",
	onePlus: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/ONEPlus.js",
	standardKaeonACE: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/Source/API/Babylon/Modules/Router/standardKaeonACE.js",
	universalPreprocessor: "https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-FUSION/master/Kaeon%20FUSION/Source/Engine/UniversalPreprocessor.js",
	widgets: "https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/Utilities/UI/Visual/Widgets/widgets.js"
};

var io = require(moduleDependencies.io);
var kaeonACE = require(moduleDependencies.kaeonACE);
var onePlus = require(moduleDependencies.onePlus);
var standardKaeonACE = require(moduleDependencies.standardKaeonACE);
var universalPreprocessor = require(moduleDependencies.universalPreprocessor);
var widgets = require(moduleDependencies.widgets);

var urlArgs = {};

window.location.href.replace(
	/[?&]+([^=&]+)=([^&]*)/gi,
	function(match, key, value) {
		urlArgs[key.toLowerCase()] = decodeURIComponent(value);
	}
);

function startGame(element) {

	var core = { };

	standardKaeonACE(core);
	
	kaeonACE.run(
		core,
		onePlus.readONEPlus(universalPreprocessor.preprocess(io.open(urlArgs["kaeonace"]))),
		element
	);
}

widgets.createStartScreen(document.documentElement, "Start", startGame);