var fusion = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/United%20Bootstrap/FUSION.js");
var kaeonACE = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Kaeon-ACE/master/Kaeon%20ACE/API/Kaeon%20ACE/Babylon/KaeonACE.js");
var philosophersStone = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/Philosophers-Stone/master/Philosopher's%20Stone/API/JavaScript/PhilosophersStone.js");
var widgets = require("https://raw.githubusercontent.com/Gallery-of-Kaeon/JavaScript-Utilities/master/JavaScript%20Utilities/UI/widgets.js");

function getACECallback(fusion, ace) {

	return function(element) {
		kaeonACE.run(fusion, ace, element);
	};
}

let ACEModule = function() {

	philosophersStone.abide(this, new fusion.FUSIONUnit());

	this.verify = function(element) {
		return element.parent == null;
	}

	this.process = function(element, processed) {

		widgets.createStartScreen(
			document.documentElement,
			"Start",
			getACECallback(this.fusion, element)
		);
	}
}

module.exports = function(fusion) {

	let aceModule = new ACEModule();
	aceModule.fusion = fusion;

	philosophersStone.connect(fusion, aceModule, [], true);
};